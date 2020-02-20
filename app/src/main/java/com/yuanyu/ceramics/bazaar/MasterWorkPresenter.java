package com.yuanyu.ceramics.bazaar;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MasterWorkPresenter extends BasePresenter<MasterWorkConstract.IMasterWorkView> implements MasterWorkConstract.IMasterWorkPresenter {
    private MasterWorkConstract.IMasterWorkModel model;
    MasterWorkPresenter() {model=new MasterWorkModel();}
    @Override
    public void getMasterWorkList(int page) {
        model.getMasterWorkList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MasterWorkBean>>())
                .subscribe(new BaseObserver<List<MasterWorkBean>>() {
                    @Override
                    public void onNext(List<MasterWorkBean> masterWorkBeans) {
                        if(view!=null){view.getMasterWorkSuccess(masterWorkBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getMasterWorkFail(e);}
                    }
                });
    }
}
