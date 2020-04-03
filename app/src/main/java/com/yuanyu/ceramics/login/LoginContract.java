package com.yuanyu.ceramics.login;
import com.tencent.connect.UserInfo;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface LoginContract {
    interface ILoginModel{
        Observable<BaseResponse<LoginBean>> login(int type, String username, String password);
        Observable<BaseResponse<String[]>> getValidCode(String mobile);
        Observable<BaseResponse<LoginBean>>thirdLogin(String status, String portrait, String nickname, String openid);
    }
    interface ILoginView{
        void showToast(String msg);

        void loginSuccess(LoginBean bean);
        void loginFail(ExceptionHandler.ResponeThrowable e);

        void getValidCodeSuccess();
        void getValidCodeFail(ExceptionHandler.ResponeThrowable e);

        void IMLoginSuccess();

    }
    interface ILoginPresenter{
        void login(int type, String username, String password);
        void getValidCode(String mobile);
        void getUnionId(UserInfo userInfo, String openId);
        void thirdLogin(String status, String portrait, String nickname, String openid);
        void IMLogin(String useraccountid,String usersig,String nickname,String logo);
    }
}

