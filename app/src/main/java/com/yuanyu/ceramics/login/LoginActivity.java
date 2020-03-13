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

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.MyApplication;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.home.HomeActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yuanyu.ceramics.AppConstant.QQ_APP_ID;


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
    private Tencent mTencent;
    private QQCallBack qqCallBack;
    private IWXAPI wx_api;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    @Override
    protected int getLayout() {return R.layout.activity_login;}

    @Override
    protected LoginPresenter initPresent() {return new LoginPresenter();}

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        WbSdk.install(this,new AuthInfo(this, AppConstant.APP_KEY, AppConstant.REDIRECT_URL,AppConstant.SCOPE));
        mTencent = Tencent.createInstance(QQ_APP_ID, MyApplication.getContext());
        wx_api = WXAPIFactory.createWXAPI(this,AppConstant.WECHAT_APP_ID,false);
        wx_api.registerApp(AppConstant.WECHAT_APP_ID);
        dialog=new LoadingDialog(this);
        qqCallBack=new QQCallBack();
        mSsoHandler = new SsoHandler(this);
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
                mTencent.login(LoginActivity.this, "all",qqCallBack);
                break;
            case R.id.login_wechat:
                if (!wx_api.isWXAppInstalled()) {
                    Toast.makeText(this, "您尚未安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                long timeStamp = System.currentTimeMillis();
                req.state = timeStamp + "0";
                wx_api.sendReq(req);
                break;
            case R.id.login_weibo:
                mSsoHandler.authorize(new SelfWbAuthListener());
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
        Sp.putString(this,AppConstant.MOBILE,bean.getMobile());
        Sp.putString(this,AppConstant.PASSWORD,loginPassword.getText().toString());
        Sp.putString(this,AppConstant.TOKEN,bean.getToken());
        Sp.putString(this,AppConstant.REFRESH_TOKEN,bean.getRefresh_token());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("requestCode is " + requestCode);
        L.e("resultCode is " + resultCode);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        if (requestCode == 11101)
            Tencent.onActivityResultData(requestCode, resultCode, data,qqCallBack);
    }

    class QQCallBack implements IUiListener {
        @Override
        public void onComplete(Object response) {
            try {
                L.e(response.toString());
                String openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);
                mTencent.setAccessToken(((JSONObject) response).getString("access_token"), ((JSONObject) response).getString("expires_in"));
                UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                L.e(userInfo.toString());
                presenter.getUnionId(userInfo,openidString);
            } catch (JSONException e) {
                L.e(e.getMessage());
            }
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "登陆取消", Toast.LENGTH_SHORT).show();
        }
    }
    private class SelfWbAuthListener implements WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            LoginActivity.this.runOnUiThread(() -> {
                mAccessToken = token;
                if (mAccessToken.isSessionValid()) {
                    AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                    String token1 = mAccessToken.getToken();
                    final String uid = mAccessToken.getUid();
                    L.e("token is " + token1);
                    L.e("uid is " + uid);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("access_token", token1);
                    params.put("uid", uid);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.weibo.com/2/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    HttpService httpService = retrofit.create(HttpService.class);
                    Call<ResponseBody> call = httpService.getWeiboData(params);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String json = response.body().string();
                                L.e("json is " + json);
                                JSONObject object = new JSONObject(json);
                                presenter.thirdLogin("3",object.getString("profile_image_url"), object.getString("name"), uid);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
        @Override
        public void cancel() {
            Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
