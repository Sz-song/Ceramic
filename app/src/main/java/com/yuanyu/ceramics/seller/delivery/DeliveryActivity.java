package com.yuanyu.ceramics.seller.delivery;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddOrEditAddressActivity;
import com.yuanyu.ceramics.address_manage.AddressManageActivity;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.ScanActivity;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.common.view.mypicker.CourierTimePickerPopupwindow;
import com.yuanyu.ceramics.dingzhi.AddressBean;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class DeliveryActivity extends BaseActivity<DeliveryPresenter> implements DeliveryConstract.IDeliveryView {
    private static final int COURIER_CODE = 201;
    private static final int POST_ADDRESS_CODE = 202;
    private static final int RECEIVE_ADDRESS_CODE = 203;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    SquareImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.post_name)
    TextView postName;
    @BindView(R.id.post_phone)
    TextView postPhone;
    @BindView(R.id.post_addressbook)
    TextView postAddressbook;
    @BindView(R.id.post_address)
    TextView postAddress;
    @BindView(R.id.get_name)
    TextView getName;
    @BindView(R.id.get_phone)
    TextView getPhone;
    @BindView(R.id.get_addressbook)
    TextView getAddressbook;
    @BindView(R.id.get_address)
    TextView getAddress;
    @BindView(R.id.booking_txt)
    TextView bookingTxt;
    @BindView(R.id.booking_img)
    ImageView bookingImg;
    @BindView(R.id.booking_btn)
    FrameLayout bookingBtn;
    @BindView(R.id.unbooking_txt)
    TextView unbookingTxt;
    @BindView(R.id.unbooking_img)
    ImageView unbookingImg;
    @BindView(R.id.unbooking_btn)
    FrameLayout unbookingBtn;
    @BindView(R.id.unbooking_select_courier)
    TextView unbookingSelectCourier;
    @BindView(R.id.unbooking_courier_num)
    EditText unbookingCourierNum;
    @BindView(R.id.scanner)
    ImageView scanner;
    @BindView(R.id.unbooking_courier)
    FrameLayout unbookingCourier;
    @BindView(R.id.select_courier)
    TextView selectCourier;
    @BindView(R.id.booking_courier)
    FrameLayout bookingCourier;
    @BindView(R.id.courier_subscribe)
    TextView courierSubscribe;
    @BindView(R.id.courier_weight)
    EditText courierWeight;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.note_courier)
    TextView noteCourier;
    @BindView(R.id.booking)
    FrameLayout booking;
    @BindView(R.id.commit_delivery)
    TextView commitDelivery;
    @BindView(R.id.scoll)
    ScrollView scoll;
    private DeliveryBean deliveryBean;
    private LoadingDialog dialog;
    private String time_now;
    private List<Boolean> list;
    private boolean enable;
    private boolean deliveryType;

    @Override
    protected int getLayout() {
        return R.layout.activity_delivery;
    }

    @Override
    protected DeliveryPresenter initPresent() {
        return new DeliveryPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("发货");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(false);
        }
        Intent intent = getIntent();
        if (intent.getStringExtra("ordernum") != null && intent.getStringExtra("ordernum").length() > 0) {
            deliveryBean = new DeliveryBean(intent.getStringExtra("ordernum"), "1", "", "");
        } else {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        deliveryType = true;
        dialog = new LoadingDialog(this);
        dialog.show();
        enable = false;
        presenter.getDeliveryMsg(deliveryBean.getOrdernum(), Sp.getString(this, AppConstant.SHOP_ID));
    }

    @Override
    public void getDeliveryMsgSuccess(DeliveryBean bean) {
        dialog.dismiss();
        name.setText(bean.getCommodity_name());
        GlideApp.with(this)
                .load(BASE_URL + bean.getImage())
                .override(200, 200)
                .placeholder(R.drawable.img_default)
                .into(image);
        num.setText("x" + bean.getNum());
        price.setText("¥" + bean.getPrice());
        time.setText(TimeUtils.CountTime(bean.getTime()));
        note.setText("买家留言:" + bean.getNote());
        postName.setText(bean.getSend_name());
        postPhone.setText(bean.getSend_tel());
        postAddress.setText(bean.getSend_province() + bean.getSend_city() + bean.getSend_area() + bean.getSend_address());
        getName.setText(bean.getReceive_name());
        getPhone.setText(bean.getReceive_tel());
        getAddress.setText(bean.getReceive_province() + bean.getReceive_city() + bean.getReceive_area() + bean.getReceive_address());
        deliveryBean.setCommodity_name(bean.getCommodity_name());
        deliveryBean.setNote(bean.getNote());
        deliveryBean.setCourier_num(bean.getCourier_num());
        deliveryBean.setUseraccountid(bean.getUseraccountid());
        deliveryBean.setCommodity_id(bean.getCommodity_id());
        L.e("useraccountid is " + bean.getUseraccountid());
        //发货地址
        deliveryBean.setSend_name(bean.getSend_name());
        deliveryBean.setSend_tel(bean.getSend_tel());
        deliveryBean.setSend_address(bean.getSend_address());
        deliveryBean.setSend_province(bean.getSend_province());
        deliveryBean.setSend_city(bean.getSend_city());
        deliveryBean.setSend_area(bean.getSend_area());
        //收货地址
        deliveryBean.setReceive_name(bean.getReceive_name());
        deliveryBean.setReceive_tel(bean.getReceive_tel());
        deliveryBean.setReceive_address(bean.getReceive_address());
        deliveryBean.setReceive_province(bean.getReceive_province());
        deliveryBean.setReceive_city(bean.getReceive_city());
        deliveryBean.setReceive_area(bean.getReceive_area());

        deliveryBean.setTime_start(TimeUtils.CountTime((Long.parseLong(bean.getTime_start()) + 900) + "") + ":00");
        deliveryBean.setTime_end(TimeUtils.CountTime((Long.parseLong(bean.getTime_start()) + 7200) + "") + ":00");
        L.e("time start is" + deliveryBean.getTime_start());
        L.e("time end is" + deliveryBean.getTime_end());
        time_now = (Long.parseLong(bean.getTime_start()) + 900) + "";
        long todaytime = Long.parseLong(time_now + "000");
        int now = new Date(todaytime).getHours();
        if (now > 18) {
            Toast.makeText(this, "快递小哥此时可能已经下班", Toast.LENGTH_SHORT).show();
        }
        enable = true;
    }

    @Override
    public void getDeliveryMsgFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(this, "获取数据失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deliverySuccess() {
        dialog.dismiss();
        Toast.makeText(this, "发货成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void deliveryFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message + " " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        enable = true;
    }

    @OnClick({R.id.post_name, R.id.post_phone, R.id.post_addressbook, R.id.post_address})
    public void onPostAddressbookClicked() {//修改发货地址
        if (enable) {
            Intent intent = new Intent(this, AddressManageActivity.class);
            intent.putExtra("finish", "1");
            startActivityForResult(intent, POST_ADDRESS_CODE);
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.get_name, R.id.get_phone, R.id.get_addressbook, R.id.get_address})
    public void onViewClicked(View view) {//修改收货地址
        if (enable) {
            Intent intent = new Intent(this, AddOrEditAddressActivity.class);
            intent.putExtra("type", "2");
            AddressBean addressBean = new AddressBean
                    (deliveryBean.getReceive_name(),
                            deliveryBean.getReceive_tel(),
                            deliveryBean.getReceive_province(),
                            deliveryBean.getReceive_city(),
                            deliveryBean.getReceive_area(),
                            deliveryBean.getReceive_address());
            intent.putExtra("addressbean", addressBean);
            startActivityForResult(intent, RECEIVE_ADDRESS_CODE);
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.select_courier)
    public void onSelectCourierClicked() {//选择快递
        if (enable) {
            if (deliveryBean.getSend_province() != null && deliveryBean.getSend_province().length() > 0 &&
                    deliveryBean.getSend_city() != null && deliveryBean.getSend_city().length() > 0 &&
                    deliveryBean.getSend_area() != null && deliveryBean.getSend_area().length() > 0 &&
                    deliveryBean.getSend_address() != null && deliveryBean.getSend_address().length() > 0 &&
                    deliveryBean.getReceive_province() != null && deliveryBean.getReceive_province().length() > 0 &&
                    deliveryBean.getReceive_city() != null && deliveryBean.getReceive_city().length() > 0 &&
                    deliveryBean.getReceive_area() != null && deliveryBean.getReceive_area().length() > 0 &&
                    deliveryBean.getReceive_address() != null && deliveryBean.getReceive_address().length() > 0) {
                Intent intent = new Intent(this, CourierListActivity.class);
                intent.putExtra("status", "1");
                intent.putExtra("type", 0);//智选物流
                intent.putExtra("deliverybean", deliveryBean);
                startActivityForResult(intent, COURIER_CODE);
            } else {
                Toast.makeText(this, "请先填写完整的地址", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.courier_subscribe)
    public void onCourierSubscribeClicked() { //选择取货时间
        if (enable) {
            CourierTimePickerPopupwindow popupwindow = new CourierTimePickerPopupwindow(this, time_now);
            popupwindow.showAtLocation(scoll, Gravity.BOTTOM, 0, 0);
            popupwindow.setClickListener((time_strat, time_end, text) -> {
                deliveryBean.setTime_start(time_strat);
                deliveryBean.setTime_end(time_end);
                courierSubscribe.setText(text + "  ");
                L.e("time start is" + deliveryBean.getTime_start());
                L.e("time end is" + deliveryBean.getTime_end());
            });
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.pay_type)
    public void onPayTypeClicked() {  //选择快递支付方式
        if (enable) {
            CourierPayTypePopupWindow popupWindow = new CourierPayTypePopupWindow(this, deliveryBean.getCourier_paytype());
            popupWindow.showAtLocation(scoll, Gravity.BOTTOM, 0, 0);
            popupWindow.setPositionClickListener(position -> {
                if (position == 0) {
                    deliveryBean.setCourier_paytype("1");
                    payType.setText("寄件人付");
                } else if (position == 1) {
                    deliveryBean.setCourier_paytype("2");
                    payType.setText("收件人付");
                }
            });
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.note_courier)//快递留言
    public void onViewClicked() {
        if (enable) {
            CourierNotePopupWindow popupWindow = new CourierNotePopupWindow(this, deliveryBean.getRemark(), list);
            popupWindow.showAtLocation(scoll, Gravity.BOTTOM, 0, 0);
            popupWindow.setOnStringCallback(str -> {
                deliveryBean.setRemark(str);
                noteCourier.setText(str);
                L.e(" str is" + str);
            });
        } else {
            Toast.makeText(this, "数据获取失败，请刷新数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.commit_delivery)
    public void onCommitDeliveryClicked() {
        if (enable) {
            if (deliveryType) {
                deliveryBean.setCourier_weight(courierWeight.getText().toString());
                if (list.get(0)) {
                    deliveryBean.setRemark(deliveryBean.getRemark() + "  带纸箱");
                }
                if (list.get(1)) {
                    deliveryBean.setRemark(deliveryBean.getRemark() + "  需要爬楼");
                }
                if (list.get(2)) {
                    deliveryBean.setRemark(deliveryBean.getRemark() + "  带胶带");
                }
                if (list.get(3)) {
                    deliveryBean.setRemark(deliveryBean.getRemark() + "  大件物品");
                }
                if (deliveryBean.getCourierid() == null || deliveryBean.getCourierid().trim().length() < 1) {
                    Toast.makeText(this, "请选择物流", Toast.LENGTH_SHORT).show();
                } else {
                    enable = false;
                    dialog.show();
                    presenter.AutoDelivery(Sp.getString(this, AppConstant.SHOP_ID), deliveryBean);
                }
            } else {
                if (deliveryBean.getCourierid() == null || deliveryBean.getCourierid().trim().length() < 1) {
                    Toast.makeText(this, "请选择物流", Toast.LENGTH_SHORT).show();
                } else {
                    if (unbookingCourierNum.getText().toString().trim().length() > 6) {
                        deliveryBean.setCourier_num(unbookingCourierNum.getText().toString().trim());
                        enable = false;
                        dialog.show();
                        presenter.HandDelivery(Sp.getString(this, AppConstant.SHOP_ID), deliveryBean);
                    } else {
                        Toast.makeText(this, "请输入正确的快递单号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
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
        switch (requestCode) {
            case COURIER_CODE:
                if (resultCode == RESULT_OK && data.getIntExtra("type", -1) > -1) {
                    if (data.getIntExtra("type", -1) == 0) {
                        selectCourier.setText(data.getStringExtra("courier_name") + "\b\b");
                    } else if (data.getIntExtra("type", -1) == 1) {
                        unbookingSelectCourier.setText(data.getStringExtra("courier_name") + "\b\b");
                    }
                    deliveryBean.setCourierid(data.getStringExtra("courier_id"));
                }
                break;
            case POST_ADDRESS_CODE:
                if (resultCode == RESULT_OK) {
                    if (null != data.getSerializableExtra("addressbean")) {
                        AddressBean address = (AddressBean) data.getSerializableExtra("addressbean");
                        postName.setText(address.getName());
                        postPhone.setText(address.getPhone());
                        postAddress.setText(address.getProvince() + address.getCity() + address.getExparea() + address.getAddress());
                        deliveryBean.setSend_name(address.getName());
                        deliveryBean.setSend_tel(address.getPhone());
                        deliveryBean.setSend_address(address.getAddress());
                        deliveryBean.setSend_province(address.getProvince());
                        deliveryBean.setSend_city(address.getCity());
                        deliveryBean.setSend_area(address.getExparea());
                    }
                }
                break;
            case RECEIVE_ADDRESS_CODE:
                if (resultCode == RESULT_OK) {
                    if (null != data.getSerializableExtra("addressbean")) {
                        AddressBean address = (AddressBean) data.getSerializableExtra("addressbean");
                        getName.setText(address.getName());
                        getPhone.setText(address.getPhone());
                        getAddress.setText(address.getProvince() + address.getCity() + address.getExparea() + address.getAddress());
                        deliveryBean.setReceive_name(address.getName());
                        deliveryBean.setReceive_tel(address.getPhone());
                        deliveryBean.setReceive_address(address.getAddress());
                        deliveryBean.setReceive_province(address.getProvince());
                        deliveryBean.setReceive_city(address.getCity());
                        deliveryBean.setReceive_area(address.getExparea());
                    }
                }
                break;
        }
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (scanResult.getContents() != null && scanResult.getContents().length() > 0) {
                String result = scanResult.getContents();
                DeleteDialog dialog = new DeleteDialog(this);
                dialog.setTitle("是否将" + result + "填入快递单号");
                dialog.setNoOnclickListener(dialog::dismiss);
                dialog.setYesOnclickListener(() -> {
                    unbookingCourierNum.setText(result);
                    dialog.dismiss();
                });
                dialog.show();
            }
        }
    }

    @OnClick(R.id.booking_btn)
    public void onBookingBtnClicked() {
        unbookingCourier.setVisibility(View.GONE);
        bookingCourier.setVisibility(View.VISIBLE);
        booking.setVisibility(View.VISIBLE);
        bookingTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
        bookingImg.setBackground(getResources().getDrawable(R.drawable.triangle_right_blue));
        unbookingTxt.setTextColor(getResources().getColor(R.color.gray));
        unbookingImg.setBackground(getResources().getDrawable(R.drawable.triangle_right_gray));
        deliveryType = true;
        L.e("statusis" + deliveryType + "");
    }

    @OnClick(R.id.unbooking_btn)
    public void onUnbookingBtnClicked() {
        unbookingCourier.setVisibility(View.VISIBLE);
        bookingCourier.setVisibility(View.GONE);
        booking.setVisibility(View.GONE);
        bookingTxt.setTextColor(getResources().getColor(R.color.gray));
        bookingImg.setBackground(getResources().getDrawable(R.drawable.triangle_right_gray));
        unbookingTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
        unbookingImg.setBackground(getResources().getDrawable(R.drawable.triangle_right_blue));
        deliveryType = false;
        L.e("statusis" + deliveryType + "");
    }

    @OnClick(R.id.unbooking_select_courier)
    public void onUnBookingSelectCourierClicked() {
        Intent intent = new Intent(this, CourierListActivity.class);
        intent.putExtra("type", 1);//自己发货
        startActivityForResult(intent, COURIER_CODE);
    }

    @OnClick(R.id.scanner)
    public void onScannerClicked() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setCaptureActivity(ScanActivity.class); //设置打开摄像头的Activity
        integrator.setPrompt("请扫描条形码"); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
}
