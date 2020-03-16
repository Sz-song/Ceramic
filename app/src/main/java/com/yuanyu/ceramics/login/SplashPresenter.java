package com.yuanyu.ceramics.login;

import android.content.Intent;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.home.HomeActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashContract.ISplashView> implements SplashContract.ISplashPresenter {
    private SplashContract.ISplashModel model;

    SplashPresenter() {
        model=new SplashModel();
    }
    @Override
    public void autoLogin(String token) {
        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        L.e("onForceOffline");//被其他终端踢下线
                    }
                    @Override
                    public void onUserSigExpired() {
                        L.e( "onUserSigExpired");//用户签名过期了，需要刷新 userSig 重新登录 IM SDK
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() { L.e("onConnected"); }
                    @Override
                    public void onDisconnected(int code, String desc) { L.e("onDisconnected"); }
                    @Override
                    public void onWifiNeedAuth(String name) { L.e("onWifiNeedAuth"); }
                })
                //设置群组事件监听器
                .setGroupEventListener(elem -> L.e( "onGroupTipsEvent, type: " + elem.getTipsType()))
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {L.e("onRefresh");}
                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        L.e("onRefreshConversation, conversation size: " + conversations.size());
                    }
                });
        userConfig.disableStorage(); //禁用本地所有存储
        userConfig.enableReadReceipt(true);//开启消息已读回执
        TIMManager.getInstance().setUserConfig(userConfig);//将用户配置与通讯管理器进行绑定
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
    public void IMLogin(String useraccountid, String usersig) {
        TIMManager.getInstance().login(useraccountid, usersig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                L.d("login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {
                L.d( "login succ");
                if(view!=null){view.IMLoginSuccess();}
            }
        });
    }

}
