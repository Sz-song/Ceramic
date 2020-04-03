package com.yuanyu.ceramics.login;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.home.HomeActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.ISplashView {
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter initPresent() {
        return new SplashPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        GlideApp.with(this)
                .load(R.drawable.splash_bg)
                .into(image);
        presenter.autoLogin(Sp.getString(this,AppConstant.TOKEN));
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void autoLoginSuccess(LoginBean bean) {
        Sp.putString(this, AppConstant.USER_ACCOUNT_ID,bean.getUseraccountid());
        Sp.putString(this,AppConstant.MOBILE,bean.getMobile());
        Sp.putString(this,AppConstant.TOKEN,bean.getToken());
        Sp.putString(this, AppConstant.USERNAME,bean.getUsername());
        Sp.putString(this,AppConstant.PROTRAIT,bean.getLogo());
        Sp.putString(this,AppConstant.USERSIG,bean.getUsersig());
        presenter.IMLogin(bean.getUseraccountid(),bean.getUsersig(),bean.getUsername(),bean.getLogo());
    }

    @Override
    public void refreshTokenSuccess(TokenBean bean) {
        Sp.putString(this,AppConstant.TOKEN,bean.getToken());
        Sp.putString(this,AppConstant.REFRESH_TOKEN,bean.getRefresh_token());
        presenter.autoLogin(Sp.getString(this,AppConstant.TOKEN));
    }

    @Override
    public void autoLoginFail(ExceptionHandler.ResponeThrowable e) {
        presenter.refreshToken(Sp.getString(this,AppConstant.REFRESH_TOKEN));
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void refreshTokenFail(ExceptionHandler.ResponeThrowable e) {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void IMLoginSuccess() {
        Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().setBackgroundDrawable(null);
    }
}
