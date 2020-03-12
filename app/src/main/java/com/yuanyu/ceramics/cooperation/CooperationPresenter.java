package com.yuanyu.ceramics.cooperation;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CooperationPresenter extends BasePresenter<CooperationListConstract.ICooperationListView> implements CooperationListConstract.ICooperationListPresenter  {
    private CooperationListConstract.ICooperationListModel model;

    CooperationPresenter() {model = new CooperationModel();}

    @Override
    public void getCooperationList() {
        model.getCooperationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<CooperationListBean>>())
                .subscribe(new BaseObserver<List<CooperationListBean>>() {
                    @Override
                    public void onNext(List<CooperationListBean> cooperationListBeans) {
                        if(view!=null){view.getCooperationListSuccess(cooperationListBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getCooperationListFail(e);}
                    }
                });
    }
}
