package com.yuanyu.ceramics.login;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter {
    private RegisterContract.IRegisterModel model;
    RegisterPresenter(){ model = new RegisterModel();}
    @Override
    public void register(String mobile, String validCode, String userName, String password, String rePassword) {
        model.register(mobile,validCode,userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){
                            L.e("error is "+e.status+e.message);
                            view.showToast(e.message);
                        }
                    }
                    @Override
                    public void onNext(String[] o) {
                        if(view!=null){view.registerSuccess();}
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
       model.getValidCode(mobile).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
               .subscribe(new BaseObserver<String[]>() {
                   @Override
                   public void onError(ExceptionHandler.ResponeThrowable e) {
                       if(view!=null){
                           L.e("error is "+e.status+e.message);
                           view.showToast(e.message);
                       }
                   }
                   @Override
                   public void onNext(String[] str) {
                       if(view!=null){
                           L.e("成功获取验证码");
                           view.showToast("成功获取验证码");
                       }
                   }
               });

    }

    @Override
    public void viewUserName(String mobile, String validCode, String userName, String password, String rePassword) {
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
        if (validCode.length()==0){
            if(view!=null){view.showToast("验证码不为空");}
            return;
        }
        if (userName.length()==0){
            if(view!=null){view.showToast("用户名不为空");}
            return;
        }
        else if (password.length() <6||password.length() >16){
            if(view!=null){view.showToast("密码长度需为6至16位");}
            return;
        }
        else if (!password.equals(rePassword)){
            if(view!=null){view.showToast("两次输入的密码不一致");}
            return;
        }
        model.viewUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.showToast(e.message);}
                    }
                    @Override
                    public void onNext(Boolean bool) {
                        if(view!=null){
                            if (bool)view.showToast("该昵称已被占用");
                            else register(mobile,validCode,userName,password,rePassword);
                        }
                    }
                });
    }
}
