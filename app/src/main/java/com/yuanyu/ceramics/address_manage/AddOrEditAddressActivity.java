package com.yuanyu.ceramics.address_manage;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.view.mypicker.AddressPickerPopupWindow;
import com.yuanyu.ceramics.common.view.mypicker.DataPickerDialog;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddOrEditAddressActivity extends BaseActivity<AddOrEditAddressPresenter> implements AddOrEditAddressConstract.IAddAddressView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.location_txt)
    TextView locationTxt;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.location_relat)
    RelativeLayout locationRelat;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.isdefault_txt)
    TextView isdefaultTxt;
    @BindView(R.id.isdefault)
    TextView isdefault;
    @BindView(R.id.isdefault_relat)
    RelativeLayout isdefaultRelat;
    @BindView(R.id.queding)
    Button queding;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private AddressPickerPopupWindow pickerPopupWindow;
    private AddressManageBean addressBean;
    private String type;
    @Override
    protected int getLayout(){return R.layout.activity_add_or_edit_address;}
    @Override
    protected AddOrEditAddressPresenter initPresent(){return new AddOrEditAddressPresenter();}
    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        queding.setActivated(false);
        name.addTextChangedListener(new EditTextWatcher());
        phone.addTextChangedListener(new EditTextWatcher());
        address.addTextChangedListener(new EditTextWatcher());
        location.addTextChangedListener(new EditTextWatcher());
        Intent intent = getIntent();
        if(null!=intent.getStringExtra("type")){
            type=intent.getStringExtra("type");
            if(type.equals("0")){//新增
                title.setText("新增收货地址");
            }else if(type.equals("1")){//编辑
                addressBean = (AddressManageBean) intent.getSerializableExtra("addressbean");
                name.setText(addressBean.getName());
                phone.setText(addressBean.getPhone());
                location.setText(addressBean.getProvince()+" "+addressBean.getCity()+" "+addressBean.getExparea());
                address.setText(addressBean.getAddress());
                if (addressBean.getIsdefault() == 1) {
                    isdefaultRelat.setVisibility(View.GONE);
                } else {
                    isdefault.setText("否");
                }
            }else if(type.equals("2")){//修改
                addressBean = (AddressManageBean) intent.getSerializableExtra("addressbean");
                name.setText(addressBean.getName());
                phone.setText(addressBean.getPhone());
                location.setText(addressBean.getProvince()+" "+addressBean.getCity()+" "+addressBean.getExparea());
                address.setText(addressBean.getAddress());
                isdefaultRelat.setVisibility(View.GONE);
            }
        }else {
            finish();
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.location_relat)
    public void onViewClicked() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        pickerPopupWindow = new AddressPickerPopupWindow(AddOrEditAddressActivity.this);
        pickerPopupWindow.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
        pickerPopupWindow.setAddressPickerSure((address, provinceCode, cityCode, districtCode) -> {
            if(address.split(" ")[1].equals("市辖区")||address.split(" ")[1].equals("县")){
                location.setText(address.split(" ")[0].substring(0,address.split(" ")[0].length()-1)+" "+address.split(" ")[0]+" "+address.split(" ")[2]);
            }else {
                location.setText(address);
            }
            pickerPopupWindow.dismiss();
        });
    }
    @OnClick(R.id.isdefault_relat)
    public void onIsdefaultRelatClicked() {
        List<String> stringList = new ArrayList<>();
        stringList.add("是");
        stringList.add("否");
        showDefaultDialog(stringList);
    }
    @OnClick(R.id.queding)
    public void onQuedingClicked() {
        if (queding.isActivated()) {
            if(type.equals("0")){//新增
                int ifdefault=isdefault.getText().toString().equals("是")?1:0;
                presenter.addAddress(Sp.getInt(AddOrEditAddressActivity.this, "useraccountid"), name.getText().toString(), phone.getText().toString(), location.getText().toString().split(" ")[0], location.getText().toString().split(" ")[1], location.getText().toString().split(" ")[2] ,address.getText().toString(), ifdefault);
            }else if(type.equals("1")){//编辑
                int ifdefault=isdefault.getText().toString().equals("是")?1:0;
                presenter.editAddress(Sp.getInt(AddOrEditAddressActivity.this, "useraccountid"), addressBean.getAddressid(), name.getText().toString(), phone.getText().toString(), location.getText().toString().split(" ")[0], location.getText().toString().split(" ")[1], location.getText().toString().split(" ")[2]  , address.getText().toString(), ifdefault);
            }else if(type.equals("2")){//修改
                Intent intent = new Intent(this, AddOrEditAddressActivity.class);
                intent.putExtra("type","2");
                AddressManageBean bean=new AddressManageBean
                        (name.getText().toString(),
                                phone.getText().toString(),
                                location.getText().toString().split(" ")[0],
                                location.getText().toString().split(" ")[1],
                                location.getText().toString().split(" ")[2] ,
                                address.getText().toString());
                intent.putExtra("addressbean",bean);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
    @Override
    public void Success() {
        setResult(RESULT_OK);
        finish();
    }
    @Override
    public void Fail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(AddOrEditAddressActivity.this, e.message, Toast.LENGTH_SHORT).show();
    }

    //是否默认选择器
    private void showDefaultDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        DataPickerDialog defaultDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        isdefault.setText(itemValue);
                    }
                    @Override
                    public void onCancel() {}
                }).create();
        defaultDialog.show();
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
    //Edittext填写监听器
    class EditTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void afterTextChanged(Editable editable) {
            if (name.getText().toString().length() != 0 && phone.getText().toString().length() == 11 && location.getText().toString().length() != 0 && address.getText().toString().length() != 0) {
                queding.setActivated(true);
            } else {
                queding.setActivated(false);
            }
        }
    }
}