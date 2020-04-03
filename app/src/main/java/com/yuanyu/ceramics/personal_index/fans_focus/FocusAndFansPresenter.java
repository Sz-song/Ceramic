package com.yuanyu.ceramics.personal_index.fans_focus;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FocusAndFansPresenter extends BasePresenter<FocusAndFansConstract.IFocusAndFansView> implements FocusAndFansConstract.IFocusAndFansPresenter {
    private FocusAndFansConstract.IFocusAndFansModel model;
    FocusAndFansPresenter() {
        model=new FocusAndFansModel();
    }
    @Override
    public void getFocusAndFansList(String useraccountid, String userid, int focustype, int page) {
        model.getFocusAndFansList(useraccountid,userid,focustype,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<FocusAndFansBean>>())
                .subscribe(new BaseObserver<List<FocusAndFansBean>>() {
                    @Override
                    public void onNext(List<FocusAndFansBean> focusAndFansBeans) {if(view!=null){view.getFocusAndFansListSuccess(focusAndFansBeans);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.getFocusAndFansListFail(e);}}
                });
    }
    @Override
    public void focus(String useraccountid, String userid,int position) {
        model.focus(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.focusSuccess(b,position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.focusFail(e);}}
                });
    }
}
