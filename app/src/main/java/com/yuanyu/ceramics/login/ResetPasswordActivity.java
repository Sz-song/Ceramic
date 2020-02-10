package com.yuanyu.ceramics.login;

import android.os.CountDownTimer;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity<ResetPwdPresenter> implements ResetPwdContract.IResetPwdView {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.linear_code)
    LinearLayout linearCode;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.password_again)
    EditText passwordAgain;
    @BindView(R.id.linear_pwd)
    LinearLayout linearPwd;
    @BindView(R.id.submit)
    TextView submit;
    private CountDownTimer timer;

    @Override
    protected int getLayout() {return R.layout.activity_reset_password;}

    @Override
    protected ResetPwdPresenter initPresent() {return new ResetPwdPresenter();}

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
        title.setText("重置密码");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetPwdSuccess() {
        Toast.makeText(this, "重置成功,请登录", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnClick({R.id.get_code, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                presenter.getValidCode(account.getText().toString());
                timer = new CountDownTimer(60 * 1000 + 1050, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCode.setEnabled(false);
                        getCode.setText(((int) millisUntilFinished / 1000 - 1) + " s");
                    }
                    @Override
                    public void onFinish() {
                        getCode.setEnabled(true);
                        getCode.setText("获取验证码");
                    }
                }.start();
                break;
            case R.id.submit:
                presenter.resetPassword(account.getText().toString(),code.getText().toString(),loginPassword.getText().toString(),passwordAgain.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
