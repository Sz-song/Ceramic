package com.yuanyu.ceramics.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.home.HomeActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.linear_pwd)
    LinearLayout linearPwd;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.linear_code)
    LinearLayout linearCode;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.login_forget)
    TextView loginForget;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.login_linear)
    LinearLayout loginLinear;
    @BindView(R.id.login_qq)
    LinearLayout loginQq;
    @BindView(R.id.login_wechat)
    LinearLayout loginWechat;
    @BindView(R.id.login_weibo)
    LinearLayout loginWeibo;
    @BindView(R.id.login_duanxin)
    LinearLayout loginDuanxin;
    @BindView(R.id.login_mima)
    LinearLayout loginMima;
    private int type = 4;
    private CountDownTimer timer;
    private LoadingDialog dialog;

    @Override
    protected int getLayout() {return R.layout.activity_login;}

    @Override
    protected LoginPresenter initPresent() {return new LoginPresenter();}

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        dialog=new LoadingDialog(this);
        account.setText(Sp.getString(this, AppConstant.MOBILE, ""));
        loginPassword.setText(Sp.getString(this, AppConstant.PASSWORD, ""));
    }

    @OnClick({R.id.get_code, R.id.login, R.id.login_forget, R.id.login_register, R.id.login_qq, R.id.login_wechat, R.id.login_weibo, R.id.login_duanxin, R.id.login_mima})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.get_code:
                getCode.setEnabled(false);
                presenter.getValidCode(account.getText().toString());
                break;
            case R.id.login:
                dialog.show();
                if(type==4){
                    presenter.login(type, account.getText().toString(), loginPassword.getText().toString());
                }else if(type==5){
                    presenter.login(type, account.getText().toString(), code.getText().toString());
                }
                break;
            case R.id.login_forget:
                intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_qq:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_wechat:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_weibo:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_duanxin:
                AnimationSet aset = new AnimationSet(true);
                AlphaAnimation aa = new AlphaAnimation(0, 1);
                aa.setDuration(2000);
                aset.addAnimation(aa);
                loginLinear.startAnimation(aset);
                loginDuanxin.setVisibility(View.GONE);
                loginMima.setVisibility(View.VISIBLE);
                linearCode.setVisibility(View.VISIBLE);
                linearPwd.setVisibility(View.GONE);
                type = 5;
                break;
            case R.id.login_mima:
                AnimationSet aset1 = new AnimationSet(true);
                AlphaAnimation ab = new AlphaAnimation(0, 1);
                ab.setDuration(2000);
                aset1.addAnimation(ab);
                loginLinear.startAnimation(aset1);
                loginDuanxin.setVisibility(View.VISIBLE);
                loginMima.setVisibility(View.GONE);
                linearCode.setVisibility(View.GONE);
                linearPwd.setVisibility(View.VISIBLE);
                type = 4;
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        Sp.putString(this,AppConstant.USER_ACCOUNT_ID,bean.getUseraccountid());
        Sp.putString(this,AppConstant.MOBILE,bean.getUsername());
        Sp.putString(this,AppConstant.PASSWORD,bean.getPassword());
        dialog.dismiss();
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + "  " + e.message);
    }

    @Override
    public void getValidCodeSuccess() {
        Toast.makeText(this, "获取验证码成功", Toast.LENGTH_SHORT).show();
        timer = new CountDownTimer(60 * 1000 + 1050, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCode.setText(((int) millisUntilFinished / 1000 - 1) + " s");
            }
            @Override
            public void onFinish() {
                getCode.setEnabled(true);
                getCode.setText("获取验证码");
            }
        }.start();
    }

    @Override
    public void getValidCodeFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + "  " + e.message);
        getCode.setEnabled(true);
        getCode.setText("获取验证码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer=null;
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
