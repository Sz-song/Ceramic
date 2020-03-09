package com.yuanyu.ceramics.seller.order.detail;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopOrderDetailActivity extends BaseActivity<ShopOrderDetailPresenter> implements ShopOrderDetailConstract.IShopOrderDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logistics_status)
    TextView logisticsStatus;
    @BindView(R.id.logistics_new)
    TextView logisticsNew;
    @BindView(R.id.logistics_time)
    TextView logisticsTime;
    @BindView(R.id.logistics)
    LinearLayout logistics;
    @BindView(R.id.receiver_name)
    TextView receiverName;
    @BindView(R.id.receiver_phone)
    TextView receiverPhone;
    @BindView(R.id.receiver_address)
    TextView receiverAddress;
    @BindView(R.id.image)
    SquareImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.shop_activity)
    TextView shopActivity;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.bottom_txt1)
    TextView bottomTxt1;
    @BindView(R.id.bottom_txt2)
    TextView bottomTxt2;
    @BindView(R.id.bottom_txt3)
    TextView bottomTxt3;
    @BindView(R.id.bottom_relat)
    RelativeLayout bottomRelat;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.receive_time)
    TextView receiveTime;
    @BindView(R.id.settle_time)
    TextView settleTime;
    @BindView(R.id.buyer_name)
    TextView buyerName;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.delivery)
    TextView delivery;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.delivery_time)
    TextView deliveryTime;
    @BindView(R.id.modify_price)
    TextView modifyPrice;
    private String ordernum;
    private ShopOrderDetailBean detailBean;
    private LoadingDialog dialog;
    private ModifyPricePopupwindow modifyPricePopupwindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_shop_order_detail;
    }

    @Override
    protected ShopOrderDetailPresenter initPresent() {
        return new ShopOrderDetailPresenter();
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
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        dialog = new LoadingDialog(this);
        dialog.show();
        swipe.setRefreshing(true);
        presenter.shopGetOrderDetail(Sp.getString(this, AppConstant.SHOP_ID), ordernum);
        swipe.setOnRefreshListener(() -> {
            dialog.show();
            presenter.shopGetOrderDetail(Sp.getString(this, AppConstant.SHOP_ID), ordernum);
        });
    }

    @Override
    public void shopGetOrderDetailSuccess(ShopOrderDetailBean bean) {
        detailBean = bean;
        dialog.dismiss();
        swipe.setRefreshing(false);
        receiverName.setText("收货人:" + bean.getReceiver_name());
        receiverPhone.setText(bean.getReceiver_tel());
        receiverAddress.setText("收货地址:" + bean.getProvince() + " " + bean.getCity() + " " + bean.getArea() + " " + bean.getAddress());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getImage())
                .placeholder(R.drawable.img_default)
                .override(100, 100)
                .into(image);
        name.setText(bean.getName());
        shopActivity.setText(bean.getActive());
        price.setText("¥" + bean.getPrice_pay());
        num.setText("x" + bean.getNum());
        orderNum.setText("订单编号:" + ordernum);
        orderTime.setText("下单时间:" + TimeUtils.CountTime(bean.getOrder_time()));
        buyerName.setText("买家昵称:" + bean.getUser_name());
        note.setText("买家留言:" + bean.getUser_content());
        switch (bean.getStatus()) {
            case "1"://待付款订单
                logistics.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("待付款");
                payTime.setVisibility(View.GONE);
                receiveTime.setVisibility(View.GONE);
                settleTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                modifyPrice.setVisibility(View.VISIBLE);
                modifyPricePopupwindow=new ModifyPricePopupwindow(this,bean.getPrice_pay());
                modifyPricePopupwindow.setOnModityListener((str)->{
                    modifyPricePopupwindow.dismiss();
                    dialog.show();
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager)this
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    presenter.modityOrderPrice(Sp.getString(this, AppConstant.SHOP_ID), ordernum,str);
                });
                break;
            case "2"://表示待发货订单
                logistics.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                bottomRelat.setVisibility(View.GONE);
                payTime.setVisibility(View.VISIBLE);
                payTime.setText("付款时间:" + TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.GONE);
                status.setText("待发货");
                settleTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                if (bean.getRefund_status() != 4 && bean.getRefund_status() != 2) {
                    delivery.setVisibility(View.GONE);
                } else {
                    delivery.setVisibility(View.VISIBLE);
                }
                modifyPrice.setVisibility(View.GONE);
                break;
            case "3"://表示待收货订单
                logistics.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("待收货");
                payTime.setVisibility(View.VISIBLE);
                payTime.setText("付款时间:" + TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.GONE);
                settleTime.setVisibility(View.GONE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticsid());
                deliveryTime.setVisibility(View.VISIBLE);
                deliveryTime.setText("发货时间" + TimeUtils.CountTime(bean.getDelivery_time()));
                modifyPrice.setVisibility(View.GONE);
                break;
            case "4"://表示代评价(买家已收货
                logistics.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("待评价");
                payTime.setVisibility(View.VISIBLE);
                payTime.setText("付款时间:" + TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.VISIBLE);
                receiveTime.setText("签收时间:" + TimeUtils.CountTime(bean.getReceive_time()));
                settleTime.setVisibility(View.GONE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticsid());
                deliveryTime.setVisibility(View.VISIBLE);
                deliveryTime.setText("发货时间" + TimeUtils.CountTime(bean.getDelivery_time()));
                modifyPrice.setVisibility(View.GONE);
                break;
            case "5"://退款订单
                logistics.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("退款订单");
                payTime.setVisibility(View.GONE);
                receiveTime.setVisibility(View.GONE);
                settleTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                modifyPrice.setVisibility(View.GONE);
                break;
            case "6"://被取消订单
                logistics.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("订单取消");
                payTime.setVisibility(View.GONE);
                receiveTime.setVisibility(View.GONE);
                settleTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                modifyPrice.setVisibility(View.GONE);
                break;
            case "7"://异常订单
                logistics.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("异常订单");
                payTime.setVisibility(View.VISIBLE);
                payTime.setText(TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.GONE);
                settleTime.setVisibility(View.GONE);
                deliveryTime.setVisibility(View.GONE);
                modifyPrice.setVisibility(View.GONE);
                break;
            case "8":// 已评价
                logistics.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                bottomRelat.setVisibility(View.GONE);
                delivery.setVisibility(View.GONE);
                status.setText("已评价");
                payTime.setVisibility(View.VISIBLE);
                payTime.setText("付款时间:" + TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.VISIBLE);
                receiveTime.setText("签收时间:" + TimeUtils.CountTime(bean.getReceive_time()));
                settleTime.setVisibility(View.GONE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticsid());
                deliveryTime.setVisibility(View.VISIBLE);
                deliveryTime.setText("发货时间" + TimeUtils.CountTime(bean.getDelivery_time()));
                modifyPrice.setVisibility(View.GONE);
                break;
            case "9"://已结算
                logistics.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                bottomRelat.setVisibility(View.VISIBLE);
                delivery.setVisibility(View.GONE);
                status.setText("已结算");
                payTime.setVisibility(View.VISIBLE);
                payTime.setText("付款时间:" + TimeUtils.CountTime(bean.getPay_time()));
                receiveTime.setVisibility(View.VISIBLE);
                receiveTime.setText("签收时间:" + TimeUtils.CountTime(bean.getReceive_time()));
                settleTime.setVisibility(View.VISIBLE);
                settleTime.setText("结算时间" + TimeUtils.CountTime(bean.getSettle_time()));
                bottomTxt1.setText("结算金额");
                bottomTxt2.setText(bean.getSettlement_money() + "元");
                bottomTxt3.setText(bean.getPrice_pay() + "*(100%-" + bean.getCommission() + "%)");
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticsid());
                deliveryTime.setVisibility(View.VISIBLE);
                deliveryTime.setText("发货时间" + TimeUtils.CountTime(bean.getDelivery_time()));
                modifyPrice.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void shopGetOrderDetailFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        swipe.setRefreshing(false);
        L.e(e.message + "  " + e.status);
        Toast.makeText(this, "获取数据失败请重试", Toast.LENGTH_SHORT).show();
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
        logistics.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
        Toast.makeText(this, "物流信息出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void modityOrderPriceSuccess(String new_price) {
        dialog.dismiss();
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        price.setText("¥" + new_price);
    }

    @Override
    public void modityOrderPriceFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(this, "修改失败"+e.message, Toast.LENGTH_SHORT).show();
        L.e(e.message+"  "+e.status);
    }

    @OnClick({R.id.logistics, R.id.contact, R.id.delivery,R.id.modify_price})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.logistics:
//                intent = new Intent(this, LogisticsTracingActivity.class);
//                intent.putExtra("image", detailBean.getImage());
//                intent.putExtra("logistics", detailBean.getLogisticsnum());
//                intent.putExtra("logistics_id", detailBean.getLogisticsid());
//                startActivity(intent);
                break;
            case R.id.contact:
//                if (detailBean != null && detailBean.getUseraccountid() != null && detailBean.getUseraccountid().length() > 0) {
//                    ChatActivity.navToChat(this, detailBean.getUseraccountid(), TIMConversationType.C2C);
//                }
                break;
            case R.id.delivery:
//                intent = new Intent(this, DeliveryActivity.class);
//                intent.putExtra("ordernum", ordernum);
//                startActivityForResult(intent, 1000);
                break;
            case R.id.modify_price:
                if(modifyPricePopupwindow!=null){
                    modifyPricePopupwindow.show(findViewById(R.id.root), Gravity.CENTER,0,0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            dialog.show();
            presenter.shopGetOrderDetail(Sp.getString(this, AppConstant.SHOP_ID), ordernum);
        }
    }

}
