package com.yuanyu.ceramics.login;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import io.reactivex.Observable;

public interface SplashContract {
    interface ISplashModel {
        Observable<BaseResponse<LoginBean>> autoLogin(String token);
        Observable<BaseResponse<TokenBean>> refreshToken(String refresh_token);

    }
    interface ISplashView {
        void autoLoginSuccess(LoginBean bean);
        void refreshTokenSuccess(TokenBean bean);
        void autoLoginFail(ExceptionHandler.ResponeThrowable e);
        void refreshTokenFail(ExceptionHandler.ResponeThrowable e);
    }
    interface ISplashPresenter {
        void autoLogin(String token);
        void refreshToken(String refresh_token);
    }
}
