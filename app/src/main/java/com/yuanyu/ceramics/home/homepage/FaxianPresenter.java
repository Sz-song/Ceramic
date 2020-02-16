package com.yuanyu.ceramics.home.homepage;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FaxianPresenter extends BasePresenter<FaxianConstract.IFaxianView> implements FaxianConstract.IFaxianPresenter {
    private FaxianConstract.IFaxianModel model;

    FaxianPresenter() {
        model=new FaxianModel();
    }

    @Override
    public void initData(String useraccountid) {
        model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<HomepageBean>())
                .subscribe(new BaseObserver<HomepageBean>() {
                    @Override
                    public void onNext(HomepageBean bean) {
                        if(view!=null){view.initDataSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}

