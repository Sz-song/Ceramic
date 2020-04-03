package com.yuanyu.ceramics.login;

import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.yuanyu.ceramics.MyApplication;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.HashMap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashContract.ISplashView> implements SplashContract.ISplashPresenter {
    private SplashContract.ISplashModel model;

    SplashPresenter() {
        model=new SplashModel();
    }
    @Override
    public void autoLogin(String token) {
        model.autoLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<LoginBean>())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean bean) {
                        view.autoLoginSuccess(bean);
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        view.autoLoginFail(e);
                    }
                });
    }

    @Override
    public void refreshToken(String refresh_token) {
        model.refreshToken(refresh_token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(new HttpServiceInstance.ErrorTransformer<TokenBean>())
            .subscribe(new BaseObserver<TokenBean>() {
                @Override
                public void onNext(TokenBean bean) {
                    if(view!=null){view.refreshTokenSuccess(bean);}
                }

                @Override
                public void onError(ExceptionHandler.ResponeThrowable e) {
                    if(view!=null){view.refreshTokenFail(e);}
                }
            });
    }

    @Override
    public void IMLogin(String useraccountid, String usersig,String nickname,String logo) {
        L.e("imsdk login");
        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        L.e("被挤下线");
                        Toast.makeText(MyApplication.getContext(), "其他人登陆了此账号", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onUserSigExpired() { L.e( "userSig签名过期"); }
                });
        userConfig.disableStorage(); //禁用本地所有存储
        userConfig.enableReadReceipt(true);//开启消息已读回执
        TIMManager.getInstance().setUserConfig(userConfig);//将用户配置与通讯管理器进行绑定

        TIMManager.getInstance().login(useraccountid, usersig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                if(view!=null){view.showToast("IM登录失败");}
                L.d("login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {
                L.d( "login succ and modify user message");
                HashMap<String, Object> profileMap = new HashMap<>();
                profileMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, nickname);
                profileMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, logo);
                TIMFriendshipManager.getInstance().modifySelfProfile(profileMap, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        L.e("modifySelfProfile failed: " + code + " desc" + desc);
                    }
                    @Override
                    public void onSuccess() {
                        L.e("modifySelfProfile success");
                    }
                });
                if(view!=null){view.IMLoginSuccess();}
            }
        });
    }

}
