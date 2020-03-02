package com.yuanyu.ceramics.mine.applyenter;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.view.mypicker.AddressPickerPopupWindow;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyEnterActivity extends BaseActivity<ApplyEnterPresenter> implements ApplyEnterConstract.IApplyEnterView {
    private final int SELECT_ID_CARD = 1000;
    private final int SELECT_SHOP_CARD = 2000;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.telephone)
    EditText telephone;
    @BindView(R.id.shop_name)
    EditText shopName;
    @BindView(R.id.main_products)
    TextView mainProducts;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.authentication)
    LinearLayout authentication;
    @BindView(R.id.positive_idcard)
    ImageView positiveIdcard;
    @BindView(R.id.reverse_idcard)
    ImageView reverseIdcard;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.shop_card)
    LinearLayout shopCard;
    @BindView(R.id.shop_card_image)
    ImageView shopCardImage;
    @BindView(R.id.summit)
    TextView summit;
    @BindView(R.id.id_card_num)
    EditText idCardNum;
    @BindView(R.id.authentication_image)
    LinearLayout authenticationImage;
    @BindView(R.id.shop_card_image_linear)
    LinearLayout shopCardImageLinear;
    private AddressPickerPopupWindow pickerPopupWindow;
    private LoadingDialog dialog;
    private String city;
    private String image_positive="";
    private String image_reverse="";
    private String image_shop="";

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_enter;
    }

    @Override
    protected ApplyEnterPresenter initPresent() {
        return new ApplyEnterPresenter();
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
        dialog=new LoadingDialog(this);
        authenticationImage.setVisibility(View.GONE);
        shopCardImageLinear.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.authentication, R.id.positive_idcard, R.id.reverse_idcard, R.id.location, R.id.shop_card, R.id.shop_card_image, R.id.summit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.authentication:
            case R.id.reverse_idcard:
            case R.id.positive_idcard:
                intent = new Intent(this, UploadEnterImageActivity.class);
                intent.putExtra("type", 0);
                startActivityForResult(intent, SELECT_ID_CARD);
                break;
            case R.id.location:
                pickerPopupWindow = new AddressPickerPopupWindow(ApplyEnterActivity.this);
                pickerPopupWindow.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
                pickerPopupWindow.setOnDismissListener(() -> {
                    WindowManager.LayoutParams lp = (ApplyEnterActivity.this).getWindow().getAttributes();
                    lp.alpha = 1f; // 0.0-1.0
                    (ApplyEnterActivity.this).getWindow().setAttributes(lp);
                });
                pickerPopupWindow.setAddressPickerSure((addressdetail, provinceCode, cityCode, districtCode) -> {
                    location.setText(addressdetail);
                    city = presenter.getCity(addressdetail);
                    pickerPopupWindow.dismiss();
                });
                break;
            case R.id.shop_card:
            case R.id.shop_card_image:
                intent = new Intent(this, UploadEnterImageActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, SELECT_SHOP_CARD);
                break;
            case R.id.summit:
                presenter.compressImages(this,name.getText().toString(),telephone.getText().toString(),idCardNum.getText().toString(),shopName.getText().toString(),location.getText().toString(),address.getText().toString(),city,image_positive,image_reverse,image_shop);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_ID_CARD && resultCode == RESULT_OK && null != data) {
            image_positive = data.getStringExtra("image_positive");
            GlideApp.with(this)
                    .load(image_positive)
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(positiveIdcard);
            image_reverse = data.getStringExtra("image_reverse");
            GlideApp.with(this)
                    .load(image_reverse)
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(reverseIdcard);
            authenticationImage.setVisibility(View.VISIBLE);
            authentication.setVisibility(View.GONE);
        } else if (requestCode == SELECT_SHOP_CARD && resultCode == RESULT_OK && null != data) {
            image_shop = data.getStringExtra("image_shop");
            GlideApp.with(this)
                    .load(image_shop)
                    .placeholder(R.drawable.img_default)
                    .override(200, 200)
                    .into(shopCardImage);
            shopCardImageLinear.setVisibility(View.VISIBLE);
            shopCard.setVisibility(View.GONE);
        }
    }

    @Override
    public void uploadImageSuccess(List<String> list) {
        presenter.ApplyEnter(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),name.getText().toString(),telephone.getText().toString(),idCardNum.getText().toString(),shopName.getText().toString(),location.getText().toString()+address.getText().toString(),city,list);
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
    }

    @Override
    public void ApplyEnterSuccess() {
        dialog.dismiss();
        Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void ApplyEnterFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

