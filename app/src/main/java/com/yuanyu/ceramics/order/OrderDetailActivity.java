package com.yuanyu.ceramics.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.cart.AliPayResultInfo;
import com.yuanyu.ceramics.cart.GenerateOrdersBean;
import com.yuanyu.ceramics.chat.ChatActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.PayResult;
import com.yuanyu.ceramics.common.SelectPayTypeActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.large_payment.LargePaymentActivity;
import com.yuanyu.ceramics.logistics.LogisticsActivity;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.order.refund.ApplyRefundActivity;
import com.yuanyu.ceramics.order.refund.RefundDetailWujiaActivity;
import com.yuanyu.ceramics.seller.evaluationmanage.EvaluationManageActivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.DAIFAHUO;
import static com.yuanyu.ceramics.AppConstant.DAIFUKUAN;
import static com.yuanyu.ceramics.AppConstant.WECHAT_APP_ID;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailConstract.IOrderDetailView {

    private static final int SDK_PAY_FLAG = 1;
    private final int REFUND = 123;
    private final int EVULATE = 124;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.order_introduce)
    TextView orderIntroduce;
    @BindView(R.id.logistics_status)
    TextView logisticsStatus;
    @BindView(R.id.logistics_new)
    TextView logisticsNew;
    @BindView(R.id.logistics_time)
    TextView logisticsTime;
    @BindView(R.id.logistics)
    LinearLayout logisticsLinear;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.receiver_name)
    TextView receiverName;
    @BindView(R.id.receiver_phone)
    TextView receiverPhone;
    @BindView(R.id.receiver_address)
    TextView receiverAddress;
    @BindView(R.id.shop_portrait)
    CircleImageView shopPortrait;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.contact_shop)
    TextView contactShop;
    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.item_num)
    TextView itemNum;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.delivery_time)
    TextView deliveryTime;
    @BindView(R.id.receive_time)
    TextView receiveTime;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.btn_white)
    TextView btnWhite;
    @BindView(R.id.btn_red)
    TextView btnRed;
    @BindView(R.id.item)
    RelativeLayout item;
    @BindView(R.id.coupons)
    TextView coupons;
    @BindView(R.id.coupons_linear)
    LinearLayout couponsLinear;
    private String orderId;
    private String commodityId;
    private String shopId;
    private int type, qiugou_id;
    private String comment, telnum, address, name_str;
    private String area;//县、区
    private String city;//城市
    private String province;//省份
    private LoadingDialog dialog;
    private String logistics;
    private String image;
    private String logistics_id;
    private String price_pay;
    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected OrderDetailPresenter initPresent() {
        return new OrderDetailPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dialog = new LoadingDialog(this);
        dialog.show();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("ordernum");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> presenter.getOrderDetail(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderId));
        presenter.getOrderDetail(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderId);
    }

    @Override
    public void getOrderDetailSuccess(OrderDetailBean bean) {
        swipe.setRefreshing(false);
        int status = bean.getStatus();
        logistics = bean.getLogistiscnum();
        image = bean.getItem_list().get(0).getImage();
        logistics_id = bean.getLogisticscompany();
        commodityId = bean.getItem_list().get(0).getId();
        shopId = bean.getShop_id();
        type = bean.getType();
        qiugou_id = bean.getQiugou_id();
        comment = bean.getComment();
        telnum = bean.getTel();
        address = bean.getAddress();
        province = bean.getProvince();
        city = bean.getCity();
        area = bean.getArea();
        name_str = bean.getName();
        price_pay=bean.getPrice_pay();
        if(null!=bean.getCoupons()&&bean.getCoupons().trim().length()>0){
            couponsLinear.setVisibility(View.VISIBLE);
            coupons.setText(bean.getCoupons());
        }else{
            couponsLinear.setVisibility(View.GONE);
        }
        switch (status) {
            case AppConstant.DAIFUKUAN:
                orderStatus.setText("等待您的付款");
                orderIntroduce.setText(TimeUtils.OrderTimeRemain(bean.getCreat_time(), bean.getSystem_time()));
                logisticsLinear.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                btnRed.setVisibility(View.VISIBLE);
                btnWhite.setText("取消订单");
                btnRed.setText("付款");
                payTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                receiveTime.setVisibility(View.GONE);

                //取消订单
                btnWhite.setOnClickListener(v -> presenter.cancelOrder(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderId));
                //付款
                btnRed.setOnClickListener(v -> {
                    Intent intent=new Intent(this, SelectPayTypeActivity.class);
                    intent.putExtra("price",price_pay);
                    intent.putExtra("title","选择支付方式");
                    startActivityForResult(intent,1000);
                });
                break;
            case AppConstant.DAIFAHUO:
                orderStatus.setText("商家备货中");
                try {
                    String finish_time = Long.parseLong(bean.getPay_time()) + (3 * 24 * 60 * 60) + "";
                    orderIntroduce.setText("等待商家发货 " + TimeUtils.TimeRemain(finish_time, bean.getSystem_time()));
                } catch (Exception e) {
                    orderIntroduce.setText("等待商家发货");
                }
                logisticsLinear.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                payTime.setVisibility(View.VISIBLE);
                deliveryTime.setVisibility(View.GONE);
                receiveTime.setVisibility(View.GONE);
                payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                btnRed.setVisibility(View.GONE);
                btnWhite.setText("申请退款");
                btnWhite.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ApplyRefundActivity.class);
                    intent.putExtra("order_num", orderId);
                    intent.putExtra("id", bean.getItem_list().get(0).getId());
                    intent.putExtra("image", bean.getItem_list().get(0).getImage());
                    intent.putExtra("name", bean.getItem_list().get(0).getName());
                    intent.putExtra("original_price", bean.getItem_list().get(0).getOriginal_price());
                    intent.putExtra("price", bean.getItem_list().get(0).getPrice());
                    startActivityForResult(intent, REFUND);
                });
                break;
            case AppConstant.DAISHOUHUO:
                orderStatus.setText("宝贝离你越来越近");
                try {
                    orderIntroduce.setText(TimeUtils.TimeRemain((Long.parseLong(bean.getDelivery_time()) + 10 * 24 * 60 * 60) + "", bean.getSystem_time()) + "自动确认收货");
                } catch (Exception e) {
                    L.e(e.getMessage());
                    orderIntroduce.setText("运输中");
                }
                logisticsLinear.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                payTime.setVisibility(View.VISIBLE);
                deliveryTime.setVisibility(View.VISIBLE);
                receiveTime.setVisibility(View.GONE);
                presenter.getLogisticsTracing(bean.getLogistiscnum(), bean.getLogisticscompany());
                payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                deliveryTime.setText("发货时间: " + TimeUtils.CountTime(bean.getDelivery_time()));
                btnRed.setVisibility(View.VISIBLE);
                btnWhite.setText("申请退款");
                btnWhite.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ApplyRefundActivity.class);
                    intent.putExtra("order_num", orderId);
                    intent.putExtra("id", bean.getItem_list().get(0).getId());
                    intent.putExtra("image", bean.getItem_list().get(0).getImage());
                    intent.putExtra("name", bean.getItem_list().get(0).getName());
                    intent.putExtra("original_price", bean.getItem_list().get(0).getOriginal_price());
                    intent.putExtra("price", bean.getItem_list().get(0).getPrice());
                    startActivityForResult(intent, REFUND);
                });
                btnRed.setText("确认收货");
                btnRed.setOnClickListener(v -> presenter.comfirmReceived(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderId));
                break;
            case AppConstant.DAIPINGJIA:
                orderStatus.setText("你的宝贝已收到，赶快去评价吧");
                orderIntroduce.setText("订单尚未评价");
                logisticsLinear.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                payTime.setVisibility(View.VISIBLE);
                deliveryTime.setVisibility(View.VISIBLE);
                receiveTime.setVisibility(View.VISIBLE);
                presenter.getLogisticsTracing(bean.getLogistiscnum(), bean.getLogisticscompany());
                payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                deliveryTime.setText("发货时间: " + TimeUtils.CountTime(bean.getDelivery_time()));
                receiveTime.setText("收货时间: " + TimeUtils.CountTime(bean.getFinish_time()));
                if (bean.isCan_refund()) {
                    btnRed.setVisibility(View.VISIBLE);
                    btnWhite.setText("申请售后");
                    btnWhite.setOnClickListener(v -> {
                        Intent intent = new Intent(this, ApplyRefundActivity.class);
                        intent.putExtra("order_num", orderId);
                        intent.putExtra("id", bean.getItem_list().get(0).getId());
                        intent.putExtra("image", bean.getItem_list().get(0).getImage());
                        intent.putExtra("name", bean.getItem_list().get(0).getName());
                        intent.putExtra("original_price", bean.getItem_list().get(0).getOriginal_price());
                        intent.putExtra("price", bean.getItem_list().get(0).getPrice());
                        startActivityForResult(intent, REFUND);
                    });
                } else {
                    btnWhite.setVisibility(View.GONE);
                }
                btnRed.setText("立即评价");
                btnRed.setOnClickListener(view -> {
                    Intent intent = new Intent(this, EvaluationActivity.class);
                    intent.putExtra("order_num", orderId);
                    intent.putExtra("commodity_id", commodityId);
                    L.e("shop id is:" + shopId);
                    intent.putExtra("shop_id", shopId);
                    startActivityForResult(intent, EVULATE);
                });
                break;
            case AppConstant.TUIKUAN:
                orderStatus.setText("退款售后订单");
                payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                if(null!=bean.getRefund_time()&&bean.getRefund_time().length()>0){
                    deliveryTime.setText("退款时间: " + TimeUtils.CountTime(bean.getRefund_time()));
                }
                logisticsLinear.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                btnRed.setVisibility(View.VISIBLE);
                btnRed.setText("联系客服");
                btnRed.setOnClickListener(v -> ChatActivity.navToChat(this, bean.getCustomer_service()));
                break;
            case AppConstant.YIPINGJIA:
                orderStatus.setText("订单已签收");
                orderIntroduce.setText("订单完成");
                logisticsLinear.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                deliveryTime.setText("发货时间: " + TimeUtils.CountTime(bean.getDelivery_time()));
                receiveTime.setText("收货时间: " + TimeUtils.CountTime(bean.getFinish_time()));
                if (bean.isCan_refund()) {
                    btnRed.setVisibility(View.VISIBLE);
                    btnWhite.setText("申请售后");
                    btnWhite.setOnClickListener(v -> {
                        Intent intent = new Intent(this, ApplyRefundActivity.class);
                        intent.putExtra("order_num", orderId);
                        intent.putExtra("id", bean.getItem_list().get(0).getId());
                        intent.putExtra("image", bean.getItem_list().get(0).getImage());
                        intent.putExtra("name", bean.getItem_list().get(0).getName());
                        intent.putExtra("original_price", bean.getItem_list().get(0).getOriginal_price());
                        intent.putExtra("price", bean.getItem_list().get(0).getPrice());
                        startActivityForResult(intent, REFUND);
                    });
                    btnRed.setText("联系客服");
                } else {
                    btnWhite.setVisibility(View.GONE);
                    btnRed.setText("联系客服");
                }
                btnRed.setOnClickListener(v -> ChatActivity.navToChat(this, bean.getCustomer_service()));
                break;
            case AppConstant.YIQUXIAO:
                if (bean.getCancel_type() == 0) {
                    orderIntroduce.setText("您自动取消");
                } else if (bean.getCancel_type() == 1) {
                    orderIntroduce.setText("订单超时未支付自动取消");
                } else if (bean.getCancel_type() == 2) {
                    orderIntroduce.setText("商家3天未发货,订单自动取消");
                    payTime.setText("付款时间: " + TimeUtils.CountTime(bean.getPay_time()));
                    if(null!=bean.getRefund_time()&&bean.getRefund_time().length()>0){
                        deliveryTime.setText("退款时间: " + TimeUtils.CountTime(bean.getRefund_time()));
                    }
                } else {
                    orderIntroduce.setText("订单取消");
                }
                orderStatus.setText("订单已取消");
                logisticsLinear.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                btnRed.setVisibility(View.GONE);
                btnWhite.setText("联系客服");
                btnWhite.setOnClickListener(v -> ChatActivity.navToChat(this, bean.getCustomer_service()));
                break;
                default:
        }
        if(bean.getRefund_status()!=4&&bean.getRefund_status()!=2) {
            btnWhite.setVisibility(View.VISIBLE);
            btnWhite.setText("查看售后");
            btnWhite.setOnClickListener(v -> {
                Intent intent=new Intent(this, RefundDetailWujiaActivity.class);
                intent.putExtra("refund_num",bean.getOrdernum());
                startActivity(intent);
            });
        }
        receiverName.setText("收货人: " + bean.getName());
        receiverPhone.setText(bean.getTel());
        receiverAddress.setText("收货地址: " + bean.getProvince() + bean.getCity() + bean.getArea() + bean.getAddress());
        shopName.setText(bean.getShop_name());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getShop_portrait())
                .override(50, 50)
                .placeholder(R.drawable.img_default)
                .into(shopPortrait);
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getItem_list().get(0).getImage())
                .override(50, 50)
                .placeholder(R.drawable.img_default)
                .into(itemImage);
        itemName.setText(bean.getItem_list().get(0).getName());
        itemPrice.setText("¥" + bean.getItem_list().get(0).getPrice());
        itemNum.setText("x" + bean.getItem_list().get(0).getNum());
        num.setText("共" + bean.getItem_list().size() + "件  合计: ");
        price.setText("¥" + bean.getPrice_pay());
        orderNum.setText("订单编号: " + bean.getOrdernum());
        orderTime.setText("下单时间: " + TimeUtils.CountTime(bean.getCreat_time()));
        //查看物流
        logisticsLinear.setOnClickListener(view -> {
            Intent intent = new Intent(this, LogisticsActivity.class);
            intent.putExtra("image", image);
            intent.putExtra("logistics", logistics);
            intent.putExtra("logistics_id", logistics_id);
            startActivity(intent);
        });
        contactShop.setOnClickListener(v -> ChatActivity.navToChat(this, bean.getShop_userid()));
        shopName.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopIndexActivity.class);
            intent.putExtra("shopid", bean.getShop_id());
            startActivity(intent);
        });
        shopPortrait.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopIndexActivity.class);
            intent.putExtra("shopid", bean.getShop_id());
            startActivity(intent);
        });
        if(type==3||type==4){
            item.setOnClickListener(v -> ItemDetailAcitivity.actionStart(this, bean.getItem_list().get(0).getId()));
        }
        dialog.dismiss();
    }

    @Override
    public void getOrderDetailFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        Toast.makeText(OrderDetailActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
        swipe.setRefreshing(false);
        dialog.dismiss();
    }

    @Override
    public void getLogisticsTracingSuccess(LogisticsBean bean) {
        switch (bean.getState()) {
            case "2":
                logisticsStatus.setText("运输中");
                break;
            case "3":
                logisticsStatus.setText("已签收");
                break;
            case "4":
                logisticsStatus.setText("问题件");
                break;
            default:
                logisticsStatus.setText("处理中");
                break;
        }
        if (bean.getTraces().size() > 0) {
            logisticsNew.setText(bean.getTraces().get(bean.getTraces().size() - 1).getAcceptStation());
            logisticsTime.setText(bean.getTraces().get(bean.getTraces().size() - 1).getAcceptTime());
        } else {
            logisticsNew.setText("暂无物流信息");
        }

    }

    @Override
    public void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e) {
        logisticsLinear.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
        Toast.makeText(this, "物流信息出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelOrderSuccess() {
        Toast.makeText(OrderDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
        presenter.getOrderDetail(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderId);
    }

    @Override
    public void cancelOrderFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + " " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void comfirmReceivedSuccess() {
        Toast.makeText(OrderDetailActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void comfirmReceivedFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + " " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void generateOrdersSuccess(GenerateOrdersBean bean, int paytype) {
        switch (paytype) {
            case 0:
                alipay(bean.getOrder_data());//Alipay
                break;
            case 1:
                dialog.dismiss();
                wechatpay(bean.getOrder_data());//Wechatpay
                break;
            case 3://Yuanyupay
                dialog.dismiss();
                List<String> strings=new ArrayList<>();
                strings.add(orderId);
                Intent intent=new Intent(this, LargePaymentActivity.class);
                intent.putExtra("price",price_pay);
                intent.putExtra("type",0);
                intent.putStringArrayListExtra("list",(ArrayList<String>) strings);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void generateOrdersFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message + e.status);
        Toast.makeText(this, "支付异常", Toast.LENGTH_SHORT).show();
    }

    private void wechatpay(String orderInfo) {
        try {
            IWXAPI api;
            api = WXAPIFactory.createWXAPI(this, WECHAT_APP_ID);//这里填入自己的微信APPID
            api.registerApp(WECHAT_APP_ID);
            JSONObject json = new JSONObject(orderInfo);
            PayReq req = new PayReq();
            req.appId = json.getString("appid");
            req.partnerId = json.getString("mch_id");
            req.prepayId = json.getString("prepay_id");
            req.nonceStr = json.getString("nonce_str");
            req.timeStamp = json.getString("time");
            req.packageValue = "Sign=WXPay";
            req.sign = json.getString("sign");
            Toast.makeText(this, "正常调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
            L.e("支付失败 " + e.toString());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setWechatPay(WechatEvent wechatEvent) {
        L.e("wechatevent is " + wechatEvent.getCode() + "  tr " + wechatEvent.getTransaction() + "  openid " + wechatEvent.getOpenId());
        switch (wechatEvent.getCode()) {
            case 0:
                L.e("支付成功");
//                setAliPay(wxOutTradeNo,wechatEvent.getTransaction());
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                MyOrderActivity.actionStart(this, DAIFAHUO);
                finish();
                break;
            case -1:
                L.e("支付失败");
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                L.e("支付取消");
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                MyOrderActivity.actionStart(this, DAIFUKUAN);
                finish();
                break;
        }
    }

    private void alipay(final String orderInfo) {
        Runnable payRunnable = () -> {
            //新建任务
            PayTask alipay = new PayTask(OrderDetailActivity.this);
            //获取支付结果
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Gson gson = new Gson();
                    AliPayResultInfo payResultInfo = gson.fromJson(resultInfo, AliPayResultInfo.class);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        List<String> order_list = new ArrayList<>();
                        order_list.add(orderId);
                        dialog.dismiss();
                        Toast.makeText(OrderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(OrderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFUND || requestCode == EVULATE) {
            if (resultCode == RESULT_OK) finish();
        }
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    if (data.getIntExtra("paytype", -1) == 0) {
                        dialog.show();
                        //支付宝支付
                        presenter.generateOrders(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), 0, type, 1, qiugou_id, name_str, address, province, city, area, telnum, presenter.initOrderList(commodityId, orderId, comment, shopId));
                    } else if (data.getIntExtra("paytype", -1) == 1) {
                        dialog.show();
                        //微信支付
                        presenter.generateOrders(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), 1, type, 1, qiugou_id, name_str, address, province, city, area, telnum, presenter.initOrderList(commodityId, orderId, comment, shopId));
                    } else if (data.getIntExtra("paytype", -1) == 3) {
                        Toast.makeText(this, "大额支付", Toast.LENGTH_SHORT).show();
                        dialog.show();
                        //大额支付
                        presenter.generateOrders(Sp.getString(this, "useraccountid"), 3, type, 1, qiugou_id, name_str, address, province, city, area, telnum, presenter.initOrderList(commodityId, orderId, comment, shopId));
                    }
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
