package com.yuanyu.ceramics.login;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface LoginContract {
    interface ILoginModel{
        Observable<BaseResponse<LoginBean>> login(int type, String username, String password);
        Observable<BaseResponse<String[]>> getValidCode(String mobile);

    }
    interface ILoginView{
        void showToast(String msg);

        void loginSuccess(LoginBean bean);
        void loginFail(ExceptionHandler.ResponeThrowable e);

        void getValidCodeSuccess();
        void getValidCodeFail(ExceptionHandler.ResponeThrowable e);

    }
    interface ILoginPresenter{
        void login(int type, String username, String password);
        void getValidCode(String mobile);
    }
}

