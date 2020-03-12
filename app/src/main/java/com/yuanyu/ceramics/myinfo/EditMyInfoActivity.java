package com.yuanyu.ceramics.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.view.mypicker.AddressPickerPopupWindow;
import com.yuanyu.ceramics.common.view.mypicker.DataPickerDialog;
import com.yuanyu.ceramics.common.view.mypicker.DatePickerDialog;
import com.yuanyu.ceramics.common.view.mypicker.DateUtil;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditMyInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.introduction)
    EditText introduction;
    @BindView(R.id.gender_txt)
    TextView genderTxt;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.gender_relat)
    RelativeLayout genderRelat;
    @BindView(R.id.birthday_txt)
    TextView birthdayTxt;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.birthday_relat)
    RelativeLayout birthdayRelat;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.email_relat)
    LinearLayout emailRelat;
    @BindView(R.id.location_txt)
    TextView locationTxt;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.location_relat)
    RelativeLayout locationRelat;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private MyInfoBean infoBean;
    private DatePickerDialog dateDialog;
    private DataPickerDialog genderDialog;
    private AddressPickerPopupWindow pickerPopupWindow;
    private MyInfoModel model=new MyInfoModel();
    @Override
    protected int getLayout() {
        return R.layout.activity_edit_my_info;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        title.setText("编辑资料");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();
        infoBean = (MyInfoBean) intent.getSerializableExtra("infoBean");
        name.setText(infoBean.getName());
        introduction.setText(infoBean.getIntroduce());
        gender.setText(infoBean.getGender());
        birthday.setText(infoBean.getBirthday());
        email.setText(infoBean.getEmail());
        location.setText(infoBean.getLocation());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                String editables = name.getText().toString();
                String strs = stringFilter(editables);
                if (!editables.equals(strs)) {
                    name.setText(strs);
                    //设置新的光标所在位置
                    name.setSelection(strs.length());
                }

                int mTextMaxlenght = 0;
                Editable editable = name.getText();
                String str = editable.toString().trim();
                int selEndIndex = Selection.getSelectionEnd(editable);
                for (int i = 0; i < str.length(); i++) {
                    char charAt = str.charAt(i);
                    if (charAt >= 32 && charAt <= 122) {
                        mTextMaxlenght++;
                    } else {
                        mTextMaxlenght += 2;
                    }
// 当最大字符大于40时，进行字段的截取，并进行提示字段的大小
                    if (mTextMaxlenght > 16) {
                        String newStr = str.substring(0, i);
                        name.setText(newStr);
                        editable = name.getText();
                        int newLen = editable.length();
                        if (selEndIndex > newLen) {
                            selEndIndex = editable.length();
                        }
                        Selection.setSelection(editable, selEndIndex);
                        Toast.makeText(EditMyInfoActivity.this, "最大长度为16个字符或8个汉字！", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    public String stringFilter(String str)throws PatternSyntaxException {
// 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

                birthday.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
            }
            @Override
            public void onCancel() {}
        })
                .setMinYear(1900)
                .setMaxYear(2050)
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    private void showGenderDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        genderDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        gender.setText(itemValue);

                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
        genderDialog.show();
    }
    private void ViewUsername() {
        if(name.getText().toString()==null||name.getText().toString().length()==0){
            Toast.makeText(this, "昵称为空", Toast.LENGTH_SHORT).show();
        }else if(name.getText().toString().equals(infoBean.getName())){
            viewEmail();
        }else{
            model.viewUsername(name.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                    .subscribe(new BaseObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (!aBoolean) {
                                viewEmail();
                            } else {
                                Toast.makeText(EditMyInfoActivity.this, "该昵称已被占用", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ExceptionHandler.ResponeThrowable e) {
                            L.e(e.message + "  " + e.status);
                            Toast.makeText(EditMyInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void viewEmail() {
        String emailstr =email.getText().toString();
        if(emailstr == null || emailstr.trim().length() == 0){
            CommitMyInfo();
        }else if(!emailstr.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
            Toast.makeText(this, "请填写正确的邮箱格式", Toast.LENGTH_SHORT).show();
        }else{
            CommitMyInfo();
        }
    }

    private void CommitMyInfo() {
        String namestr=name.getText().toString();
        String introducestr=introduction.getText().toString();
        String genderstr=gender.getText().toString();
        String birthdaystr=birthday.getText().toString();
        String emailstr=email.getText().toString();
        String locationstr=location.getText().toString();
        model.editUserinfo(Sp.getInt(EditMyInfoActivity.this,"useraccountid"),namestr,introducestr,genderstr,birthdaystr,emailstr,locationstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {
                        Toast.makeText(EditMyInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.message+"  "+e.status);
                        Toast.makeText(EditMyInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @OnClick({R.id.gender_relat, R.id.birthday_relat, R.id.location_relat, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gender_relat:
                List<String> listgender = new ArrayList<>();
                listgender.add("男");
                listgender.add("女");
                showGenderDialog(listgender);
                break;
            case R.id.birthday_relat:
                List<Integer> list = new ArrayList<>();
                Calendar now = Calendar.getInstance();
                list.add(now.get(Calendar.YEAR));
                list.add(now.get(Calendar.MONTH) + 1);
                list.add(now.get(Calendar.DAY_OF_MONTH));
                showDateDialog(list);
                break;
            case R.id.location_relat:
                pickerPopupWindow = new AddressPickerPopupWindow(EditMyInfoActivity.this);
                pickerPopupWindow.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
                pickerPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = (EditMyInfoActivity.this).getWindow().getAttributes();
                        lp.alpha = 1f; // 0.0-1.0
                        (EditMyInfoActivity.this).getWindow().setAttributes(lp);
                    }
                });
                pickerPopupWindow.setAddressPickerSure((address, provinceCode, cityCode, districtCode) -> {
                    location.setText(address);
                    pickerPopupWindow.dismiss();
                });
                break;
            case R.id.commit:
                ViewUsername();
                break;
        }
    }


}
