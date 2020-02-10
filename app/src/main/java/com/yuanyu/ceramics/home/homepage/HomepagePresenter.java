package com.yuanyu.ceramics.home.homepage;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomepagePresenter extends BasePresenter<HomepageConstract.IHomepageView> implements HomepageConstract.IHomepagePresenter {
    private HomepageConstract.IHomepageModel model;

    HomepagePresenter() {
        model=new HomepageModel();
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
