package com.yuanyu.ceramics.mine.systemsetting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.login.LoginActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ModifyPasswardActivity extends NormalActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.new_passward1)
    EditText newPassward1;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.new_passward2)
    EditText newPassward2;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.getcode)
    TextView getcode;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.input_code)
    EditText inputCode;
    @BindView(R.id.commit)
    TextView commit;
    private SystemSettingModel model=new SystemSettingModel();
    private LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passward);
        ButterKnife.bind(this);
        title.setText("修改密码");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        dialog=new LoadingDialog(this);
        String mobile = Sp.getString(this, "mobile");
        phone.setText(mobile);
    }

    @OnClick({R.id.getcode, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getcode:
                if(phone.getText().toString().trim().length() == 11) {
                    getcode.setClickable(false);
                    final CountDownTimer ctd = new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            getcode.setText(l / 1000 + "s");
                        }
                        @Override
                        public void onFinish() {
                            getcode.setText("获取验证码");
                            getcode.setClickable(true);
                        }
                    };
                    ctd.start();
                    model.getValiDateCode(phone.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                            .subscribe(new BaseObserver<String[]>() {
                                @Override
                                public void onNext(String[] strings) {
                                    Toast.makeText(ModifyPasswardActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(ExceptionHandler.ResponeThrowable e) {
                                    Toast.makeText(ModifyPasswardActivity.this, e.message, Toast.LENGTH_SHORT).show();
                                    ctd.cancel();
                                    getcode.setText("获取验证码");
                                    getcode.setClickable(true);
                                }
                            });
                }else {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.commit:
                if(ischeck()) {
                    dialog.show();
                    model.modifyPassword(phone.getText().toString(), inputCode.getText().toString(), newPassward1.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                            .subscribe(new BaseObserver<String[]>() {
                                @Override
                                public void onNext(String[] strings) {
                                    dialog.dismiss();
                                    Toast.makeText(ModifyPasswardActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ModifyPasswardActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    Sp.putBoolean(ModifyPasswardActivity.this, AppConstant.IS_LOGIN, false);
//                                    Sp.putBoolean(ModifyPasswardActivity.this, AppConstant.IS_MERCHANT, false);
                                    startActivity(intent);
                                }
                                @Override
                                public void onError(ExceptionHandler.ResponeThrowable e) {
                                    dialog.dismiss();
                                    Toast.makeText(ModifyPasswardActivity.this, e.message, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }

    private boolean ischeck() {
        if(!newPassward1.getText().toString().equals(newPassward2.getText().toString())){
            Toast.makeText(ModifyPasswardActivity.this, "两次密码不同", Toast.LENGTH_SHORT).show();
            return false;
        }else if(phone.getText()==null||phone.getText().toString().trim().length()!=11){
            Toast.makeText(ModifyPasswardActivity.this, "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }else if(inputCode.getText()==null||inputCode.getText().toString().length()<3){
            Toast.makeText(ModifyPasswardActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            return false;
        }else if(newPassward1.getText()==null||newPassward1.getText().toString().length()==0){
            Toast.makeText(ModifyPasswardActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(newPassward1.getText().toString().length()<6){
            Toast.makeText(ModifyPasswardActivity.this, "密码太简单", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return newPassward1.getText().toString().equals(newPassward2.getText().toString()) && newPassward1.getText().toString().length() > 5
                    && phone.getText().toString().trim().length() == 11 && newPassward1.getText().toString().length() > 5;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
