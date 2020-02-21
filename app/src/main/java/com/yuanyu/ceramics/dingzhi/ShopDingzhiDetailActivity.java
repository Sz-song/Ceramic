package com.yuanyu.ceramics.dingzhi;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.ScanActivity;
import com.yuanyu.ceramics.common.view.CustomDatePicker;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShopDingzhiDetailActivity extends BaseActivity<ShopDingzhiDetailPresenter> implements ShopDingzhiDetailConstract.IShopDingzhiDetailView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.portrait)
    CircleImageView portrait;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.deposit)
    TextView deposit;
    @BindView(R.id.contact_user)
    TextView contactUser;
    @BindView(R.id.receiver_name)
    TextView receiverName;
    @BindView(R.id.receiver_phone)
    TextView receiverPhone;
    @BindView(R.id.receiver_address)
    TextView receiverAddress;
    @BindView(R.id.address_flame)
    FrameLayout addressFlame;
    @BindView(R.id.dingzhi_detail)
    TextView dingzhiDetail;
    @BindView(R.id.usage_scenarios)
    TextView usageScenarios;
    @BindView(R.id.about_price)
    TextView aboutPrice;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.chanzhuang)
    TextView chanzhuang;
    @BindView(R.id.fenlei)
    TextView fenlei;
    @BindView(R.id.ticai)
    TextView ticai;
    @BindView(R.id.cancel_order)
    TextView cancelOrder;
    @BindView(R.id.confirm_order)
    TextView confirmOrder;
    @BindView(R.id.set_price)
    EditText setPrice;
    @BindView(R.id.set_end_time)
    TextView setEndTime;
    @BindView(R.id.set_linear)
    LinearLayout setLinear;
    @BindView(R.id.set_price_txt)
    TextView setPriceTxt;
    @BindView(R.id.set_end_time_txt)
    TextView setEndTimeTxt;
    @BindView(R.id.set_linear_txt)
    LinearLayout setLinearTxt;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.choose_linear)
    LinearLayout chooseLinear;
    @BindView(R.id.input_linear)
    LinearLayout inputLinear;
    @BindView(R.id.select_courier)
    TextView selectCourier;
    @BindView(R.id.courier_num)
    EditText courierNum;
    @BindView(R.id.scanner)
    ImageView scanner;
    @BindView(R.id.courier_linear)
    LinearLayout courierLinear;
    private LoadingDialog dialog;
    private String id;
    private boolean isInit = false;
    private int statusCode;
    private String user_id;
    private CustomDatePicker endTimePicker;
    private SimpleDateFormat sdf;
    private String endtime="";
    private String courier_id="";
    private String view_courier_id;
    private String courier_num;

    @Override
    protected int getLayout() {
        return R.layout.activity_dingzhi_detail_shop;
    }

    @Override
    protected ShopDingzhiDetailPresenter initPresent() {
        return new ShopDingzhiDetailPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        title.setText("定制详情");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        swipe.setColorSchemeResources(R.color.colorPrimary);
        id = getIntent().getStringExtra("id");
        dialog = new LoadingDialog(this);
        dialog.show();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        endTimePicker = new CustomDatePicker(this, time -> { // 回调接口，获得选中的时间
            setEndTime.setText(time);
            try {
                Date date = sdf.parse(time);
                endtime = ((date.getTime() / 1000) + "");
            } catch (Exception e) {
                L.e(e.getMessage());
            }
        }, "2019-01-01 00:00", "2030-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        endTimePicker.showSpecificTime(true); // 显示时和分
        endTimePicker.setIsLoop(true); // 允许循环滚动
        presenter.dingzhiDetail(id, Sp.getInt(this, AppConstant.USER_ACCOUNT_ID));
        swipe.setOnRefreshListener(() -> presenter.dingzhiDetail(id, Sp.getInt(this, AppConstant.USER_ACCOUNT_ID)));
    }

    @Override
    public void dingzhiDetailSuccess(DingzhiDetailBean detailBean) {
        isInit = true;
        statusCode = detailBean.getStatus();
        user_id = detailBean.getUseraccountid();
        view_courier_id=detailBean.getCourier_id();
        courier_num=detailBean.getCourier_num();
        dialog.dismiss();
        swipe.setRefreshing(false);
        dingzhiDetail.setText(detailBean.getDetail());
        usageScenarios.setText(detailBean.getUseage());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + detailBean.getPortrait())
                .placeholder(R.drawable.img_default)
                .override(50, 50)
                .into(portrait);
        name.setText(detailBean.getName());
        if (detailBean.getPrice().equals("0")) {
            aboutPrice.setText("1000-5000");
            chanzhuang.setText("山料");
        } else if (detailBean.getPrice().equals("1")) {
            aboutPrice.setText("5001-50000");
            chanzhuang.setText("籽料");
        } else if (detailBean.getPrice().equals("2")) {
            aboutPrice.setText("50000以上");
            chanzhuang.setText("籽料");
        } else {
            aboutPrice.setText("1000-5000");
            chanzhuang.setText("山料");
        }
        if (detailBean.getBirthday() != null && detailBean.getBirthday().length() > 0) {
            birthday.setText(detailBean.getBirthday());
        } else {
            birthday.setText("用户未填写");
        }
        fenlei.setText(detailBean.getFenlei());
        ticai.setText(detailBean.getTicai());
        L.e("status is: " + detailBean.getStatus());
        switch (detailBean.getStatus()) {
            //0 正在审核，1 平台审核成功 2，平台审核失败，3，大师拒绝接单，4，大师接单未缴纳保证金，5商家接单缴纳了保证金，6,支付尾款未发货，7商家发货，8买家收货
            case 1://1 平台审核成功
                addressFlame.setVisibility(View.GONE);
                setLinear.setVisibility(View.VISIBLE);
                setLinearTxt.setVisibility(View.GONE);
                chooseLinear.setVisibility(View.VISIBLE);
                inputLinear.setVisibility(View.GONE);
                courierLinear.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                break;
            case 4://大师接单未缴纳保证金
                addressFlame.setVisibility(View.GONE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.VISIBLE);
                courierLinear.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                setPriceTxt.setText("¥ " + detailBean.getMaster_price());
                setEndTimeTxt.setText(TimeUtils.CountTime(detailBean.getEnd_time()));
                break;
            case 5://大师接单缴纳了保证金
                addressFlame.setVisibility(View.GONE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                courierLinear.setVisibility(View.GONE);
                try {
                    deposit.setText("已缴纳保证金: " + (Float.parseFloat(detailBean.getMaster_price()) / 2));
                } catch (Exception e) {
                    deposit.setText("已缴纳保证金");
                }
                setPriceTxt.setText("¥ " + detailBean.getMaster_price());
                setEndTimeTxt.setText(TimeUtils.CountTime(detailBean.getEnd_time()));
                break;
            case 6:
                addressFlame.setVisibility(View.VISIBLE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                courierLinear.setVisibility(View.VISIBLE);
                submit.setText("发货");
                deposit.setText("用户已支付尾款请按约定时间发货");
                setPriceTxt.setText("¥ " + detailBean.getMaster_price());
                setEndTimeTxt.setText(TimeUtils.CountTime(detailBean.getEnd_time()));
                receiverName.setText("收货人: " + detailBean.getReceive_name());
                receiverPhone.setText(detailBean.getReceive_tel());
                receiverAddress.setText(detailBean.getReceive_province() + detailBean.getReceive_city() + detailBean.getReceive_area() + detailBean.getReceive_address());
                break;
            case 7://7商家发货
                addressFlame.setVisibility(View.VISIBLE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.VISIBLE);
                courierLinear.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                submit.setText("查看物流");
                deposit.setText("等待用户收货");
                setPriceTxt.setText("¥ " + detailBean.getMaster_price());
                setEndTimeTxt.setText(TimeUtils.CountTime(detailBean.getEnd_time()));
                receiverName.setText("收货人: " + detailBean.getReceive_name());
                receiverPhone.setText(detailBean.getReceive_tel());
                receiverAddress.setText(detailBean.getReceive_province() + detailBean.getReceive_city() + detailBean.getReceive_area() + detailBean.getReceive_address());
                break;
            case 8://8买家收货
                addressFlame.setVisibility(View.VISIBLE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.VISIBLE);
                courierLinear.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                deposit.setText("定制成功");
                setPriceTxt.setText("¥ " + detailBean.getMaster_price());
                setEndTimeTxt.setText(TimeUtils.CountTime(detailBean.getEnd_time()));
                receiverName.setText("收货人: " + detailBean.getReceive_name());
                receiverPhone.setText(detailBean.getReceive_tel());
                receiverAddress.setText(detailBean.getReceive_province() + " " + detailBean.getReceive_city() + " " + detailBean.getReceive_area() + " " + detailBean.getReceive_address());
                break;
            default:
                addressFlame.setVisibility(View.GONE);
                setLinear.setVisibility(View.GONE);
                setLinearTxt.setVisibility(View.GONE);
                courierLinear.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void dingzhiDetailFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        swipe.setRefreshing(false);
        L.e(e.status + "  " + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmOrderSuccess(Boolean aBoolean) {
        isInit = true;
        dialog.dismiss();
        if (aBoolean) {
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            presenter.dingzhiDetail(id, Sp.getInt(this, AppConstant.USER_ACCOUNT_ID));
        }
    }

    @Override
    public void confirmOrderFail(ExceptionHandler.ResponeThrowable e) {
        isInit = true;
        dialog.dismiss();
        L.e(e.message + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        isInit = true;
        dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelOrderSuccess(Boolean aBoolean) {
        isInit = true;
        dialog.dismiss();
        Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void cancelOrderFail(ExceptionHandler.ResponeThrowable e) {
        isInit = true;
        dialog.dismiss();
        L.e(e.message + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dingzhiCourierSiccess(Boolean aBoolean) {
        isInit = true;
        dialog.dismiss();
        if (aBoolean) {
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            presenter.dingzhiDetail(id, Sp.getInt(this, AppConstant.USER_ACCOUNT_ID));
        }
    }

    @Override
    public void dingzhiCourierFail(ExceptionHandler.ResponeThrowable e) {
        isInit = true;
        dialog.dismiss();
        L.e(e.message + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.contact_user, R.id.cancel_order, R.id.confirm_order, R.id.set_end_time, R.id.submit, R.id.select_courier, R.id.scanner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_user:
//                if (isInit) {
//                    ChatActivity.navToChat(this, user_id, TIMConversationType.C2C);
//                }
                break;
            case R.id.cancel_order:
                DeleteDialog deleteDialog = new DeleteDialog(this);
                deleteDialog.setTitle("确定取消订单吗？");
                deleteDialog.setNoOnclickListener(deleteDialog::dismiss);
                deleteDialog.setYesOnclickListener(() -> {
                    if (isInit) {
                        isInit = false;
                        presenter.cancelOrder(id, Sp.getString(ShopDingzhiDetailActivity.this, AppConstant.SHOP_ID));
                        dialog.show();
                    }
                    deleteDialog.dismiss();

                });
                deleteDialog.show();
                break;
            case R.id.confirm_order:
                chooseLinear.setVisibility(View.GONE);
                inputLinear.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                submit.setText("确认订单");
                break;
            case R.id.set_end_time:
                String now1 = sdf.format(new Date());
                try {
                    L.e("time is: " + setEndTime.getText().toString());
                    if (setEndTime.getText().toString().equals("")) {
                        endTimePicker.show(now1);
                    } else {
                        endTimePicker.show(setEndTime.getText().toString());
                    }
                } catch (Exception e) {
                    L.e(e.getMessage());
                    endTimePicker.show(now1);
                }
                break;
            case R.id.submit:
                switch (statusCode) {
                    case 1://确认接单
                        if (isInit) {
                            isInit = false;
                            presenter.confirmOrder(id, Sp.getString(this, AppConstant.SHOP_ID), setPrice.getText().toString(), endtime);
                        }
                        break;
                    case 6://发货
                        if (isInit) {
                            isInit = false;
                            presenter.dingzhiCourier(id, Sp.getString(this, AppConstant.SHOP_ID),courier_id,courierNum.getText().toString());
                        }
                        break;
                    case 7://查看物流
                        if(courier_num!=null&&courier_num.length()>0&&view_courier_id!=null&&view_courier_id.length()>0){
//                            Intent intent=new Intent(this,LogisticsTracingActivity.class);
//                            intent.putExtra("image","");
//                            intent.putExtra("logistics",courier_num);
//                            intent.putExtra("logistics_id",view_courier_id);
//                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "物流信息有误", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
            case R.id.select_courier:
//                Intent intent = new Intent(this, CourierListActivity.class);
//                intent.putExtra("type", 1);//自己发货
//                startActivityForResult(intent, 1002);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1002:
                if (resultCode == RESULT_OK && data.getIntExtra("type", -1) ==1) {
                    selectCourier.setText(data.getStringExtra("courier_name") + "\b\b");
                    courier_id=data.getStringExtra("courier_id");
                }
                break;
            default:
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
                    courierNum.setText(result);
                    dialog.dismiss();
                });
                dialog.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
