package com.yuanyu.ceramics.order.refund;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.ScanActivity;
import com.yuanyu.ceramics.common.SquareImageViewAdapter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefundDetailWujiaActivity extends BaseActivity<RefundDetailWujiaPresenter> implements RefundDetailWujiaConstract.IRefundDetailView {

    private static final int COURIER_CODE = 201;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refund_status)
    TextView refundStatus;
    @BindView(R.id.refund_introduce)
    TextView refundIntroduce;
    @BindView(R.id.top_status)
    LinearLayout topStatus;
    @BindView(R.id.logistics_status)
    TextView logisticsStatus;
    @BindView(R.id.logistics_new)
    TextView logisticsNew;
    @BindView(R.id.logistics_time)
    TextView logisticsTime;
    @BindView(R.id.logistics)
    LinearLayout logistics;
    @BindView(R.id.reject_reason)
    TextView rejectReason;
    @BindView(R.id.recyclerview_reject)
    RecyclerView recyclerviewReject;
    @BindView(R.id.reject_linear)
    LinearLayout rejectLinear;
    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.item)
    RelativeLayout item;
    @BindView(R.id.refund_reason)
    TextView refundReason;
    @BindView(R.id.refund_price)
    TextView refundPrice;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.refund_time)
    TextView refundTime;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.apply_note)
    TextView applyNote;
    @BindView(R.id.recyclerview_apply)
    RecyclerView recyclerviewApply;
    @BindView(R.id.contact_shop)
    TextView contactShop;
    @BindView(R.id.contact_kefu)
    TextView contactKefu;
    @BindView(R.id.cancel_apply)
    TextView cancelApply;
    @BindView(R.id.modify_apply)
    TextView modifyApply;
    @BindView(R.id.select_logistics)
    TextView selectLogistics;
    @BindView(R.id.logistics_num)
    EditText logisticsNum;
    @BindView(R.id.scanner)
    ImageView scanner;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.input_logistics)
    LinearLayout inputLogistics;
    @BindView(R.id.btn_linear)
    LinearLayout btnLinear;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private SquareImageViewAdapter adapterApply;
    private SquareImageViewAdapter adapterReject;
    private List<String> list_pic=new ArrayList<>();
    private List<String> fail_pic =new ArrayList<>();
    private String image;
    private String logisticsnum;
    private String logistics_id;
    private String customer_service;
    private String shop_userid;
    private boolean isInit=false;
    private String orderid;
    @Override
    protected int getLayout() {return R.layout.activity_refund_detail_wujia;}

    @Override
    protected RefundDetailWujiaPresenter initPresent() {return new RefundDetailWujiaPresenter();}

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
        orderid = getIntent().getStringExtra("refund_num");
        swipe.setVisibility(View.GONE);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(() -> {
            presenter.getRefundDetailData(Sp.getInt(this, AppConstant.USER_ACCOUNT_ID), orderid);
        });
        adapterApply=new SquareImageViewAdapter(this,list_pic);
        recyclerviewApply.setLayoutManager(new CantScrollGirdLayoutManager(this,3));
        recyclerviewApply.setAdapter(adapterApply);
        adapterReject=new SquareImageViewAdapter(this, fail_pic);
        recyclerviewReject.setLayoutManager(new CantScrollGirdLayoutManager(this,3));
        recyclerviewReject.setAdapter(adapterReject);
        presenter.getRefundDetailData(Sp.getInt(this, AppConstant.USER_ACCOUNT_ID), orderid);
    }

    @Override
    public void getRefundDetailSuccess(RefundDetailBean bean) {
        isInit=true;
        swipe.setRefreshing(false);
        image=bean.getPortrait();
        logisticsnum=bean.getLogisticsnum();
        logistics_id=bean.getLogisticscompany();
        customer_service=bean.getCustomer_service();
        shop_userid=bean.getShop_userid();
        swipe.setVisibility(View.VISIBLE);
        //退款状态  1退款中 2退款失败 3退款成功 5 商家同意用户发快递
        GlideApp.with(this)
                .load(AppConstant.BASE_URL+bean.getPortrait())
                .override(100,100)
                .placeholder(R.drawable.img_default)
                .into(itemImage);
        itemName.setText(bean.getName());
        itemPrice.setText("¥"+bean.getRefund_money());
        refundReason.setText("退款原因: "+bean.getRefund_reason());
        refundPrice.setText("退款金额: ¥"+bean.getRefund_money());
        num.setText("申请件数: "+bean.getNum());
        refundTime.setText("申请时间: "+TimeUtils.CountTime(bean.getApply_time()));
        orderNum.setText("退款编号: "+bean.getRefund_num());
        applyNote.setText("退款说明: "+bean.getIllustrate());
        list_pic.clear();
        if(null!=bean.getPic_list()) {
            list_pic.addAll(bean.getPic_list());
        }
        adapterApply.notifyDataSetChanged();
        if(bean.getType()==1){//1退款中
            topStatus.setVisibility(View.VISIBLE);
            refundStatus.setText("退款申请已提交，等待商家处理");
            refundIntroduce.setText(TimeUtils.TimeRemain(bean.getFinish_time(),bean.getSystem_time()));
            logistics.setVisibility(View.GONE);
            rejectLinear.setVisibility(View.GONE);
            btnLinear.setVisibility(View.VISIBLE);
            contactShop.setVisibility(View.VISIBLE);
            contactKefu.setVisibility(View.VISIBLE);
            cancelApply.setVisibility(View.VISIBLE);
            modifyApply.setVisibility(View.VISIBLE);
            inputLogistics.setVisibility(View.GONE);
        }else if(bean.getType()==2){//2退款失败
            topStatus.setVisibility(View.VISIBLE);
            refundStatus.setText("您的退款申请失败，可重新申请");
            refundIntroduce.setText("退款失败");
            logistics.setVisibility(View.GONE);
            rejectLinear.setVisibility(View.VISIBLE);
            if(bean.getFailed_msg().trim().length()>0) {
                rejectReason.setText(bean.getFailed_msg());
            }else{
                rejectReason.setText("商家没有填写原因");
            }
            fail_pic.clear();
            if(null!=bean.getPic_fail()) {
                fail_pic.addAll(bean.getPic_fail());
            }
            adapterReject.notifyDataSetChanged();
            btnLinear.setVisibility(View.VISIBLE);
            contactShop.setVisibility(View.GONE);
            contactKefu.setVisibility(View.VISIBLE);
            cancelApply.setVisibility(View.GONE);
            modifyApply.setVisibility(View.VISIBLE);
            modifyApply.setText("重新申请");
            inputLogistics.setVisibility(View.GONE);
        }else if(bean.getType()==3){//3退款成功
            topStatus.setVisibility(View.VISIBLE);
            refundStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
            refundStatus.setText("退款成功");
            refundIntroduce.setTextColor(getResources().getColor(R.color.colorPrimary));
            refundIntroduce.setText(TimeUtils.CountTime(bean.getFinish_time()));
            logistics.setVisibility(View.GONE);
            rejectLinear.setVisibility(View.GONE);
            btnLinear.setVisibility(View.VISIBLE);
            contactShop.setVisibility(View.VISIBLE);
            contactKefu.setVisibility(View.VISIBLE);
            cancelApply.setVisibility(View.GONE);
            modifyApply.setVisibility(View.GONE);
            inputLogistics.setVisibility(View.GONE);
        }else if(bean.getType()==4){//4退款取消
            topStatus.setVisibility(View.VISIBLE);
            refundStatus.setText("取消退款");
            refundIntroduce.setText(TimeUtils.CountTime(bean.getFinish_time()));
            logistics.setVisibility(View.GONE);
            rejectLinear.setVisibility(View.GONE);
            btnLinear.setVisibility(View.VISIBLE);
            contactShop.setVisibility(View.VISIBLE);
            contactKefu.setVisibility(View.VISIBLE);
            cancelApply.setVisibility(View.GONE);
            modifyApply.setVisibility(View.GONE);
            inputLogistics.setVisibility(View.GONE);
        }
        else if(bean.getType()==5){//退货退款
            if(bean.getLogisticsnum()!=null&&bean.getLogisticsnum().length()>0){
                topStatus.setVisibility(View.GONE);
                logistics.setVisibility(View.VISIBLE);
//                presenter.getLogisticsTracing(bean.getLogisticsnum(), bean.getLogisticscompany());
                rejectLinear.setVisibility(View.GONE);
                btnLinear.setVisibility(View.VISIBLE);
                contactShop.setVisibility(View.VISIBLE);
                contactKefu.setVisibility(View.VISIBLE);
                cancelApply.setVisibility(View.GONE);
                modifyApply.setVisibility(View.GONE);
                inputLogistics.setVisibility(View.GONE);
            }else{
                topStatus.setVisibility(View.VISIBLE);
                refundStatus.setText("商家已同意您的申请，请填写物流信息");
                refundIntroduce.setText(TimeUtils.TimeRemain(bean.getFinish_time(),bean.getSystem_time()));
                logistics.setVisibility(View.GONE);
                rejectLinear.setVisibility(View.GONE);
                btnLinear.setVisibility(View.GONE);
                inputLogistics.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getRefundDetailFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.message);
        swipe.setRefreshing(false);
        Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void getLogisticsTracingSuccess(LogisticsBean bean) {
//        switch (bean.getState()) {
//            case "2": logisticsStatus.setText("运输中");
//                break;
//            case "3": logisticsStatus.setText("已签收");
//                break;
//            case "4": logisticsStatus.setText("问题件");
//                break;
//            default: logisticsStatus.setText("处理中");
//                break;
//        }
//        if (bean.getTraces().size() > 0) {
//            logisticsNew.setText(bean.getTraces().get(bean.getTraces().size() - 1).getAcceptStation());
//            logisticsTime.setText(bean.getTraces().get(bean.getTraces().size() - 1).getAcceptTime());
//        } else {
//            logisticsNew.setText("暂无物流信息");
//        }
//
//    }
//
//    @Override
//    public void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e) {
//        logistics.setVisibility(View.GONE);
//        Toast.makeText(this, "物流信息出错", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void CancelRefundSuccess() {
        Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void CancelRefundFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void InputLogisticsSuccess() {
        Toast.makeText(this, "填写成功，请耐心等待商家收货", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void InputLogisticsFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COURIER_CODE:
                if (resultCode == RESULT_OK && data.getIntExtra("type",-1)>-1) {
                    if(data.getIntExtra("type",-1)==1){
                        selectLogistics.setText(data.getStringExtra("courier_name") + "\b\b");
                        logistics_id=data.getStringExtra("courier_id");
                    }
                }
                break;
        }
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (scanResult.getContents() != null && scanResult.getContents().length() > 0) {
                String result = scanResult.getContents();
                DeleteDialog dialog =new DeleteDialog(this) ;
                dialog.setTitle("是否将"+result+"填入快递单号");
                dialog.setNoOnclickListener(dialog::dismiss);
                dialog.setYesOnclickListener(() -> {
                    logisticsNum.setText(result);
                    dialog.dismiss();
                });
                dialog.show();
            }
        }
    }
    @OnClick({R.id.logistics, R.id.contact_shop, R.id.contact_kefu, R.id.cancel_apply, R.id.modify_apply, R.id.select_logistics, R.id.scanner, R.id.submit})
    public void onViewClicked(View view) {
        if (isInit) {
            Intent intent;
            switch (view.getId()) {
                case R.id.logistics:
//                    intent = new Intent(this, LogisticsTracingActivity.class);
//                    intent.putExtra("image", image);
//                    intent.putExtra("logistics", logisticsnum);
//                    intent.putExtra("logistics_id", logistics_id);
//                    startActivity(intent);
                    break;
                case R.id.contact_shop:
//                    ChatActivity.navToChat(this, shop_userid, TIMConversationType.C2C);
                    break;
                case R.id.contact_kefu:
//                    ChatActivity.navToChat(this, customer_service, TIMConversationType.C2C);
                    break;
                case R.id.cancel_apply:
                    presenter.CancelRefund(Sp.getInt(this, AppConstant.USER_ACCOUNT_ID),orderid);
                    break;
                case R.id.modify_apply:
                    intent = new Intent(this,ApplyRefundActivity.class);
                    intent.putExtra("order_num",orderid);
                    startActivity(intent);
                    break;
                case R.id.select_logistics:
//                    intent = new Intent(this, CourierListActivity.class);
//                    intent.putExtra("type",1);//自己发货
//                    startActivityForResult(intent, COURIER_CODE);
                    break;
                case R.id.scanner:
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                    integrator.setCaptureActivity(ScanActivity.class); //设置打开摄像头的Activity
                    integrator.setPrompt("请扫描条形码"); //底部的提示文字，设为""可以置空
                    integrator.setCameraId(0); //前置或者后置摄像头
                    integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();
                    break;
                case R.id.submit:
                    if(logisticsNum.getText().toString().trim().length()>0&&logistics_id.length()>0) {
                        presenter.InputLogistics(orderid, logisticsNum.getText().toString().trim(), logistics_id);
                    }else{
                        Toast.makeText(this, "请选择快递公司，并填写快递单号", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
