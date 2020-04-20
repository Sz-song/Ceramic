package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCoinsDetailPresenter extends BasePresenter<MyCoinsDetailConstract.IMyCoinsDetailView> implements MyCoinsDetailConstract.IMyCoinsDetailPresenter{
    private MyCoinsDetailConstract.IMyCoinsDetailModel model;

    MyCoinsDetailPresenter() {model=new MyCoinsDetailModel();}

    @Override
    public void getMyCoinsDetailList(String useraccountid,int page) {
        model.getMyCoinsDetailList(useraccountid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MyCoinsDetailBean>>())
                .subscribe(new BaseObserver<List<MyCoinsDetailBean>>() {
                    @Override
                    public void onNext(List<MyCoinsDetailBean> myCoinsDetailBeans) {
                        if(view!=null){view.getMyCoinsDetailListSuccess(myCoinsDetailBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getMyCoinsDetailListFail(e);}
                    }
                });
    }
}
