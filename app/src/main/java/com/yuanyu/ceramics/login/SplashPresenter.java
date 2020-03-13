package com.yuanyu.ceramics.login;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
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

}
