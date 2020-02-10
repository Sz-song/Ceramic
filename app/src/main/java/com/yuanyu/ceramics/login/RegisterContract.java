package com.yuanyu.ceramics.login;

import com.yuanyu.ceramics.base.BaseResponse;

import io.reactivex.Observable;

public interface RegisterContract {
    interface IRegisterModel{
        Observable<BaseResponse<String[]>> register(String mobile, String validCode, String userName, String password);
        Observable<BaseResponse<String[]>> getValidCode(String mobile);
        Observable<BaseResponse<Boolean>> viewUserName(String name);
    }
    interface IRegisterView{
        void registerSuccess();
        void showToast(String msg);
    }
    interface IRegisterPresenter{
        //注册
        void register(String mobile, String validCode, String userName, String password, String rePassword);
        //获取验证码
        void getValidCode(String mobile);
        //验证用户名是否可用
        void viewUserName(String mobile, String validCode, String userName, String password, String rePassword);
    }
}
