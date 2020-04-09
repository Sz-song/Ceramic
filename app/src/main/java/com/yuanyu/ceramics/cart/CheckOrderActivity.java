package com.yuanyu.ceramics.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddressManageActivity;
import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.SelectPayTypeActivity;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.item.CheckOrderDecoration;
import com.yuanyu.ceramics.order.MyOrderActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.wxapi.WechatEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.DAIFAHUO;
import static com.yuanyu.ceramics.AppConstant.WECHAT_APP_ID;

public class CheckOrderActivity extends BaseActivity<CheckOrderPresenter> implements CheckOrderConstract.ICheckOrderView{
    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.check_pay)
    TextView checkPay;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private double totalPrice;
    private int page = 0;
    private CheckOrderAdapter adapter;
    private List<AddressManageBean> addressBeanlist = new ArrayList<>();
    private List<GoodsBean> payList = new ArrayList<>();
    private List<AdsCellBean> adsCellBeanList = new ArrayList<>();
    private String name;
    private String address;//收货地址
    private String province;
    private String city;
    private String area;
    private String tel;//收货人tel；
    private int type;
    private int tag;
    private List<String> order_list = new ArrayList<>();
    private List<String> itemid_list=new ArrayList<>();
    private List<SumOrderBean> list = new ArrayList<>();
    private LoadingDialog dialog;
    String order_num="";

    @Override
    protected int getLayout() {
        return R.layout.activity_check_order;
    }

    @Override
    protected CheckOrderPresenter initPresent() {
        return new CheckOrderPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("确认订单");
        EventBus.getDefault().register(this);
        dialog=new LoadingDialog(this);
        Intent intent = getIntent();
        int payListSize = intent.getIntExtra("listSize", 0);//商品数量
        totalPrice = intent.getDoubleExtra("totalPrice", 0);//总价
        for (int i = 0; i < payListSize; i++) {
            payList.add((GoodsBean) intent.getSerializableExtra("payList" + i));
            payList.get(i).setCoupons_id("");
            payList.get(i).setCoupons_txt("");
            payList.get(i).setMinus(0);
        }
        L.e("paylist.size is:"+payList.size());
        for(int i=0;i<payList.size();i++){
            itemid_list.add(payList.get(i).getId());
        }
        dialog.show();
        type = intent.getIntExtra("type", -1);//1 拍卖订单 2直播订单 3 商品订单 4 求购订单。
        tag = intent.getIntExtra("tag", -1);//0 首次支付，1再次支付。
        if (null != intent.getStringExtra("order_num")) {
            order_num = intent.getStringExtra("order_num");
        }
        price.setText("¥"+String.format("%.2f",totalPrice));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position > payList.size() + 1) {
                    return 1;
                } else return 2;
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter = new CheckOrderAdapter(CheckOrderActivity.this, payList, adsCellBeanList, addressBeanlist);
         recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new CheckOrderDecoration( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8,this.getResources().getDisplayMetrics()),payList.size()+2));
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    }
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                        presenter.loadMoreAds(page);
                    }
                }
            }
        });
        adapter.setOnSetAddressLinster(() -> {
            Intent intent1 = new Intent(CheckOrderActivity.this, AddressManageActivity.class);
            intent1.putExtra("finish","1");
            startActivityForResult(intent1, 1001);
        });
        presenter.getAddressData(Sp.getString(this, "useraccountid"));
    }
    @OnClick(R.id.check_pay)
    public void onViewClicked() {
        AddressManageBean addressBean = adapter.getAddressBean();
        if (addressBean != null) {
            name = addressBean.getName();
            address = addressBean.getAddress();
            province=addressBean.getProvince();
            city=addressBean.getCity();
            area=addressBean.getExparea();
            tel = addressBean.getPhone();
            Intent intent=new Intent(this, SelectPayTypeActivity.class);
            intent.putExtra("price",price.getText().toString().substring(1));
            startActivityForResult(intent,1000);
        } else {
            Toast.makeText(this, "添加收货地址", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(resultCode==RESULT_OK){
                    if(data.getIntExtra("paytype",-1)==0){
                        dialog.show();
                        //支付宝支付
                        presenter.generateOrders(Sp.getString(this, "useraccountid"), 0, type, tag, name, address,province,city,area,tel, list);
                    }else if(data.getIntExtra("paytype",-1)==1){
                        dialog.show();
                        //微信支付
                        presenter.generateOrders(Sp.getString(this, "useraccountid"), 1, type, tag, name, address,province,city,area,tel, list);
                    }else if(data.getIntExtra("paytype",-1)==3){
                        dialog.show();
                        //大额支付
                        presenter.generateOrders(Sp.getString(this, "useraccountid"), 3, type, tag, name, address,province,city,area,tel, list);
                    }
                }
                break;
            case 1001:
                if (resultCode == RESULT_OK) {
                    if (null != data.getSerializableExtra("addressbean")) {
                        AddressManageBean address = (AddressManageBean) data.getSerializableExtra("addressbean");
                        address.setIsdefault(1);
                        for (int i = 0; i < addressBeanlist.size(); i++) {
                            addressBeanlist.get(i).setIsdefault(0);
                        }
                        addressBeanlist.add(address);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
    //唤起微信支付
    private void wechatpay(String orderInfo){
        try {
            L.e("orderinfo is "+orderInfo);
            IWXAPI api;
            api = WXAPIFactory.createWXAPI(this, WECHAT_APP_ID);//这里填入自己的微信APPID
            api.registerApp(WECHAT_APP_ID);
            JSONObject json = new JSONObject(orderInfo);
            PayReq req = new PayReq();
            req.appId			= json.getString("appid");
            req.partnerId		= json.getString("mch_id");
            req.prepayId		= json.getString("prepay_id");
            req.nonceStr		= json.getString("nonce_str");
            req.timeStamp		= json.getString("time");
            req.packageValue	= "Sign=WXPay";
            req.sign			= json.getString("sign");
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
            L.e("支付失败 "+e.toString());
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setWechatPay(WechatEvent wechatEvent){
        L.e("wechatevent is "+wechatEvent.getCode()+"  tr "+wechatEvent.getTransaction()+"  openid "+wechatEvent.getOpenId());
        switch (wechatEvent.getCode()){
            case 0:
                L.e("支付成功");
//                setAliPay(wxOutTradeNo,wechatEvent.getTransaction());
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                MyOrderActivity.actionStart(CheckOrderActivity.this, DAIFAHUO);
                finish();
                break;
            case -1:
                L.e("支付失败");
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                L.e("支付取消");
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                MyOrderActivity.actionStart(CheckOrderActivity.this, DAIFAHUO);
                finish();
                break;
        }
    }


    //唤起支付宝
    private void alipay(final String orderInfo) {
//        Runnable payRunnable = () -> {
//            //新建任务
//            PayTask alipay = new PayTask(CheckOrderActivity.this);
//            //获取支付结果
//            Map<String, String> result = alipay.payV2(orderInfo, true);
//            Message msg = new Message();
//            msg.what = SDK_PAY_FLAG;
//            msg.obj = result;
//            mHandler.sendMessage(msg);
//        };
//        Thread payThread = new Thread(payRunnable);// 必须异步调用
//        payThread.start();
    }
    //支付宝支付结果
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (msg.what == SDK_PAY_FLAG) {
//                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                //同步获取结果
//                String resultInfo = payResult.getResult();
//                Gson gson = new Gson();
//                AliPayResultInfo payResultInfo = gson.fromJson(resultInfo, AliPayResultInfo.class);
//                String resultStatus = payResult.getResultStatus();
//                // 判断resultStatus 为9000则代表支付成功
//                if (TextUtils.equals(resultStatus, "9000")) {
//                    presenter.sendAliPay(SpUtils.getInt(CheckOrderActivity.this, "useraccountid"), order_list, payResultInfo.getAlipay_trade_app_pay_response().getOut_trade_no(), payResultInfo.getAlipay_trade_app_pay_response().getTrade_no());
//                } else {
//                    finish();
//                    dialog.dismiss();
//                    MyOrderActivity.actionStart(CheckOrderActivity.this, DAIFUKUAN);
//                }
//            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    public void getAddressDataSuccess(List<AddressManageBean> addressBeans) {
        addressBeanlist.addAll(addressBeans);
        adapter.notifyDataSetChanged();
        presenter.loadMoreAds(page);
    }

    @Override
    public void getAddressDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + " " + e.message);
        Toast.makeText(this, "地址获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreAdsSuccess(List<AdsCellBean> adsCellBeans) {
        L.e("获取成功");
        adsCellBeanList.addAll(adsCellBeans);
        page++;
        adapter.notifyItemRangeInserted(payList.size() + adsCellBeanList.size() + 2 - list.size(), list.size());
    }

    @Override
    public void loadMoreAdsFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status+"  "+e.message);
    }

    @Override
    public void generateOrdersSuccess(GenerateOrdersBean generateOrdersBean, int paytype) {
        order_list.addAll(generateOrdersBean.getOrder_list());
        switch (paytype) {
            case 0://Alipay
                alipay(generateOrdersBean.getOrder_data());
                break;
            case 1://Wechatpay
                dialog.dismiss();
                L.e("wechat data is "+generateOrdersBean.getOrder_data());
                wechatpay(generateOrdersBean.getOrder_data());
                break;
            case 3://Yuanyupay
                dialog.dismiss();
                presenter.initOrdernum(generateOrdersBean.getOrder_list(),list);
                tag=1;
//                Intent intent=new Intent(CheckOrderActivity.this,LargePaymentActivity.class);
//                intent.putExtra("price",totalPrice+"");
//                intent.putExtra("type",0);
//                intent.putStringArrayListExtra("list",(ArrayList<String>) presenter.getOrderList(list));
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void generateOrdersFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(CheckOrderActivity.this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendAliPaySuccess(Boolean b) {
        if(b) {
            dialog.dismiss();
            Toast.makeText(CheckOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            dialog.dismiss();
            Toast.makeText(CheckOrderActivity.this, "订单异常", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendAliPayFail(ExceptionHandler.ResponeThrowable e, String out_trade_no, String trade_no) {
        dialog.dismiss();
        Toast.makeText(CheckOrderActivity.this, "订单异常", Toast.LENGTH_SHORT).show();
        presenter.notify_order_exception(order_list,Sp.getString(this,AppConstant.USER_ACCOUNT_ID)+"",out_trade_no,trade_no);
    }

    @Override
    public void notify_order_exceptionSuccess() {
        Toast.makeText(this, "订单异常，请联系客服", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notify_order_exceptionFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status+" "+e.message);
        Toast.makeText(CheckOrderActivity.this, "异常订单通知失败，请联系客服", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
