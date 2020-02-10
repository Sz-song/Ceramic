package com.yuanyu.ceramics.login;
import com.yuanyu.ceramics.base.BaseResponse;
import io.reactivex.Observable;

public class ResetPwdContract {
    interface IResetPwdModel{
        Observable<BaseResponse<String[]>> resetPassword(String mobile, String validCode, String password);
        Observable<BaseResponse<String[]>> getValidCode(String mobile);
    }
    interface IResetPwdView{
        void showToast(String msg);
        void resetPwdSuccess();
    }
    interface IResetPwdPresenter{
        //注册
        void resetPassword(String mobile, String validCode, String password, String rePassword);
        //获取验证码
        void getValidCode(String mobile);
    }

}
