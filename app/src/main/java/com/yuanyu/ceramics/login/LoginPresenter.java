package com.yuanyu.ceramics.login;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter{
    private LoginContract.ILoginModel model;
    LoginPresenter(){model = new LoginModel();}
    @Override
    public void login(int type, String username, String password) {
          if (username.equals("")){
              if(view!=null){view.showToast("请输入手机号");}
              return;
          }
          if (password.equals("")){
              if(type==4){
                  if(view!=null){view.showToast("请输入密码");}
              }else if(type==5){
                  if(view!=null){view.showToast("请输入验证码");}
              }
              return;
          }
        model.login(type,username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean bean) {
                        if(view!=null) {view.loginSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.status+"  "+e.message);
                        if(view!=null) {view.loginFail(e);}
                    }
                });
    }

    @Override
    public void getValidCode(String mobile) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobile.length() != 11) {
            if(view!=null){view.showToast("手机号应为11位数");}
            return;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            if (!isMatch) {
                if(view!=null){view.showToast("请填入正确的手机号");}
                return;
            }
        }
        model.getValidCode(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] o) {
                        if(view!=null){
                            L.e("成功获取验证码");
                            view.getValidCodeSuccess();
                        }
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){
                            L.e("error is "+e.status+e.message);
                            view.getValidCodeFail(e);
                        }
                    }
                });
    }

    @Override
    public void getUnionId(UserInfo userInfo, String openId) {
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    L.e(o.toString());
                    thirdLogin("1",((JSONObject) o).getString("figureurl"), ((JSONObject) o).getString("nickname"),openId);
                }catch (Exception e){
                    L.e(e.getMessage());
                    if(view!=null){view.showToast(e.getMessage());}
                }
            }
            @Override
            public void onError(UiError uiError) {
                if(view!=null){
                    L.e(uiError.errorMessage+"");
                    view.showToast(uiError.errorMessage);
                }
            }
            @Override
            public void onCancel() {if(view!=null){view.showToast("登录取消");}}
        });
    }

    @Override
    public void thirdLogin(String status, String portrait, String nickname, String openid) {
        model.thirdLogin(status, portrait, nickname, openid)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<LoginBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean bean) {
                        view.loginSuccess(bean);
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.loginFail(e);}
                    }
                });
    }
}
