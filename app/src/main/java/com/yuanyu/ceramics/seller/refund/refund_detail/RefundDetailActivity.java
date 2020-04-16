package com.yuanyu.ceramics.seller.refund.refund_detail;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.chat.ChatActivity;
import com.yuanyu.ceramics.common.CommonDialog2;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.logistics.LogisticsActivity;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefundDetailActivity extends BaseActivity<RefundDetailPresenter> implements RefundDetailConstract.IRefundDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refund_type)
    TextView refundType;
    @BindView(R.id.refunding)
    LinearLayout refunding;
    @BindView(R.id.refund_success_time)
    TextView refundSuccessTime;
    @BindView(R.id.refund_success)
    LinearLayout refundSuccess;
    @BindView(R.id.refund_fail_time)
    TextView refundFailTime;
    @BindView(R.id.refund_fail)
    LinearLayout refundFail;
    @BindView(R.id.refund_deliver)
    TextView refundDeliver;
    @BindView(R.id.image)
    SquareImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price_pay_txt)
    TextView pricePayTxt;
    @BindView(R.id.price_pay)
    TextView pricePay;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.refund_evidence)
    TextView refundEvidence;
    @BindView(R.id.image0)
    SquareImageView image0;
    @BindView(R.id.image1)
    SquareImageView image1;
    @BindView(R.id.image2)
    SquareImageView image2;
    @BindView(R.id.refund_explain)
    TextView refundExplain;
    @BindView(R.id.refund_reason)
    TextView refundReason;
    @BindView(R.id.refund_price)
    TextView refundPrice;
    @BindView(R.id.refund_num)
    TextView refundNum;
    @BindView(R.id.refund_time)
    TextView refundTime;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.contact_user)
    TextView contactUser;
    @BindView(R.id.contact_kefu)
    TextView contactKefu;
    @BindView(R.id.rejected1)
    TextView rejected1;
    @BindView(R.id.agree1)
    TextView agree1;
    @BindView(R.id.refunding_bottom1)
    RelativeLayout refundingBottom1;
    @BindView(R.id.sixin0)
    LinearLayout sixin0;
    @BindView(R.id.agree0)
    TextView agree0;
    @BindView(R.id.rejected0)
    TextView rejected0;
    @BindView(R.id.refunding_bottom0)
    RelativeLayout refundingBottom0;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.finish_time)
    TextView finishTime;
    @BindView(R.id.minus)
    TextView minus;
    @BindView(R.id.logistics_status)
    TextView logisticsStatus;
    @BindView(R.id.logistics_new)
    TextView logisticsNew;
    @BindView(R.id.logistics)
    LinearLayout logistics;
    private LoadingDialog loadingDialog;
    private String ordernum;
    private String userid;
    private String customer_service;
    private String logisticsnum;
    private String logistics_id;
    private String item_image;

    @Override
    protected int getLayout() {
        return R.layout.activity_detail_refund;
    }

    @Override
    protected RefundDetailPresenter initPresent() {
        return new RefundDetailPresenter();
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
        ordernum = getIntent().getStringExtra("ordernum");
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        presenter.getRefundDetailData(Sp.getString(this, AppConstant.SHOP_ID), ordernum);
    }

    @Override
    public void getRefundDetailDataSuccess(RefundDetailBean bean) {
        loadingDialog.dismiss();
        logisticsnum=bean.getLogisticsnum();
        logistics_id=bean.getLogisticscompany();
        if (bean.getRefund_status().equals("1")) {//申请中
            refunding.setVisibility(View.VISIBLE);
            refundSuccess.setVisibility(View.GONE);
            refundFail.setVisibility(View.GONE);
            refundDeliver.setVisibility(View.GONE);
            refundingBottom0.setVisibility(View.VISIBLE);
            refundingBottom1.setVisibility(View.GONE);
            refundType.setText(bean.getRefund_type());
            finishTime.setVisibility(View.VISIBLE);
            finishTime.setText(Md5Utils.TimeRemain(bean.getFinish_time(), bean.getSystem_time()));
        } else if (bean.getRefund_status().equals("2")) {//退款失败
            refunding.setVisibility(View.GONE);
            refundSuccess.setVisibility(View.GONE);
            refundFail.setVisibility(View.VISIBLE);
            refundDeliver.setVisibility(View.GONE);
            refundingBottom0.setVisibility(View.GONE);
            refundingBottom1.setVisibility(View.GONE);
            finishTime.setVisibility(View.GONE);
            if (bean.getLogisticsnum() != null && bean.getLogisticsnum().length() > 0) {
                item_image=bean.getSale_portrait();
                refundDeliver.setVisibility(View.GONE);
                logistics.setVisibility(View.VISIBLE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticscompany());
            }else{
                refundDeliver.setVisibility(View.GONE);
                logistics.setVisibility(View.GONE);
            }
        } else if (bean.getRefund_status().equals("3")) {//3退款成功
            refunding.setVisibility(View.GONE);
            refundSuccess.setVisibility(View.VISIBLE);
            refundFail.setVisibility(View.GONE);
            refundDeliver.setVisibility(View.GONE);
            refundingBottom0.setVisibility(View.GONE);
            refundingBottom1.setVisibility(View.GONE);
            if (bean.getLogisticsnum() != null && bean.getLogisticsnum().length() > 0) {
                item_image=bean.getSale_portrait();
                refundDeliver.setVisibility(View.GONE);
                logistics.setVisibility(View.VISIBLE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticscompany());
            }else{
                refundDeliver.setVisibility(View.GONE);
                logistics.setVisibility(View.GONE);
            }
        } else if (bean.getRefund_status().equals("5")) {//物流中
            if (bean.getLogisticsnum() != null && bean.getLogisticsnum().length() > 0) {
                item_image=bean.getSale_portrait();
                refundDeliver.setVisibility(View.GONE);
                logistics.setVisibility(View.VISIBLE);
                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticscompany());
            } else {
                refundDeliver.setVisibility(View.VISIBLE);
                logistics.setVisibility(View.GONE);
            }
            refunding.setVisibility(View.GONE);
            refundSuccess.setVisibility(View.GONE);
            refundFail.setVisibility(View.GONE);
            refundingBottom0.setVisibility(View.GONE);
            refundingBottom1.setVisibility(View.VISIBLE);
        }
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getSale_portrait())
                .placeholder(R.drawable.img_default)
                .override(200, 200)
                .into(image);
        name.setText(bean.getSale_name());
        pricePay.setText("¥" + bean.getRefund_money());
        num.setText("x" + bean.getRefund_num());
        if (bean.getMinus().trim().length() > 0) {
            minus.setText("优惠:省" + bean.getMinus() + "元");
        } else {
            minus.setText("");
        }
        if (bean.getPic_list().size() == 0) {
            refundEvidence.setText("用户未上传退款凭证");
            image0.setVisibility(View.GONE);
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.GONE);
        }
        if (bean.getPic_list().size() == 1) {
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(0))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image0);
        }
        if (bean.getPic_list().size() == 2) {
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(0))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image0);
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(1))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image1);
        }
        if (bean.getPic_list().size() == 3) {
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(0))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image0);
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(1))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image1);
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL + bean.getPic_list().get(2))
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(image2);
        }
        if (bean.getIllustrate().trim().length() > 0) {
            refundExplain.setText("退款说明: " + bean.getIllustrate());
        } else {
            refundExplain.setText("退款说明: 用户没有填写退款说明");
        }
        refundReason.setText("退款原因: " + bean.getRefund_reason());
        refundPrice.setText("退款金额: ¥" + bean.getRefund_money());
        refundNum.setText("申请件数: " + bean.getRefund_num());
        refundTime.setText("申请时间: " + Md5Utils.CountTime(bean.getApply_time()));
        orderNum.setText("退款编号: " + ordernum);
        userid = bean.getUserid();
        customer_service = bean.getCustomer_service();
    }

    @Override
    public void getRefundDetailDataFail(ExceptionHandler.ResponeThrowable e) {
        loadingDialog.dismiss();
        L.e(e.status + "  " + e.message);
        Toast.makeText(this, "数据加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RefundReviewSuccess(Boolean b) {
        loadingDialog.dismiss();
        if (b) {
            refunding.setVisibility(View.GONE);
            refundSuccess.setVisibility(View.GONE);
            refundFail.setVisibility(View.GONE);
            refundDeliver.setVisibility(View.VISIBLE);
            refundingBottom0.setVisibility(View.GONE);
            agree1.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    @Override
    public void RefundReviewFail(ExceptionHandler.ResponeThrowable e) {
        loadingDialog.dismiss();
        L.e(e.status + "  " + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLogisticsTracingSuccess(LogisticsBean bean) {
        switch (bean.getState()) {
            case "2": logisticsStatus.setText("运输中");
                break;
            case "3": logisticsStatus.setText("已签收");
                break;
            case "4": logisticsStatus.setText("问题件");
                break;
            default: logisticsStatus.setText("处理中");
                break;
        }
        if (bean.getTraces().size() > 0) {
            logisticsNew.setText(bean.getTraces().get(bean.getTraces().size() - 1).getAcceptStation());
        } else {
            logisticsNew.setText("暂无物流信息");
        }
    }

    @Override
    public void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e) {
        refundDeliver.setVisibility(View.VISIBLE);
        logistics.setVisibility(View.GONE);
        Toast.makeText(this, "物流信息出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.contact_user, R.id.contact_kefu, R.id.rejected1, R.id.agree1, R.id.sixin0, R.id.agree0, R.id.rejected0,R.id.logistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_user:
                ChatActivity.navToChat(this, userid);
                break;
            case R.id.contact_kefu:
                ChatActivity.navToChat(this, customer_service);
                break;
            case R.id.rejected1:
                RefundRejectPopupWindow popupWindow1 = new RefundRejectPopupWindow(this);
                popupWindow1.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                popupWindow1.setOnSummitClickListenner(str -> {
                    loadingDialog.show();
                    presenter.RefundReview(Sp.getString(RefundDetailActivity.this, AppConstant.SHOP_ID), ordernum, 1, str);
                    popupWindow1.dismiss();
                });
                break;
            case R.id.agree1:
                CommonDialog2 dialog1 = new CommonDialog2(this, "确认同意退款", "确定", "取消");
                dialog1.show();
                dialog1.setNoOnclick(() -> dialog1.dismiss());
                dialog1.setYesOnclick(() -> {
                    dialog1.dismiss();
                    loadingDialog.show();
                    presenter.RefundReview(Sp.getString(RefundDetailActivity.this, AppConstant.SHOP_ID), ordernum, 0, "");
                });
                break;
            case R.id.sixin0:
                ChatActivity.navToChat(this, userid);
                break;
            case R.id.agree0:
                CommonDialog2 dialog2 = new CommonDialog2(this, "确认同意退款", "确定", "取消");
                dialog2.show();
                dialog2.setNoOnclick(() -> dialog2.dismiss());
                dialog2.setYesOnclick(() -> {
                    dialog2.dismiss();
                    loadingDialog.show();
                    presenter.RefundReview(Sp.getString(RefundDetailActivity.this, AppConstant.SHOP_ID), ordernum, 0, "");
                });
                break;
            case R.id.rejected0:
                RefundRejectPopupWindow popupWindow = new RefundRejectPopupWindow(this);
                popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnSummitClickListenner(str -> {
                    loadingDialog.show();
                    presenter.RefundReview(Sp.getString(RefundDetailActivity.this, AppConstant.SHOP_ID), ordernum, 1, str);
                    popupWindow.dismiss();
                });
                break;
            case R.id.logistics:
                Intent intent = new Intent(this, LogisticsActivity.class);
                intent.putExtra("image", item_image);
                intent.putExtra("logistics", logisticsnum);
                intent.putExtra("logistics_id", logistics_id);
                startActivity(intent);
                break;
        }
    }

}
