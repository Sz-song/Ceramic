package com.yuanyu.ceramics.broadcast;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BroadcastFragmentPresenter extends BasePresenter<BroadcastFragmentConstract.IBroadcastFragmentView> implements BroadcastFragmentConstract.IBroadcastFragmentPresenter {
    private BroadcastFragmentConstract.IBroadcastFragmentModel model;
    BroadcastFragmentPresenter() {model=new BroadcastFragmentModel();}

    @Override
    public void initData(String useraccountid,int page) {
        model.initData(useraccountid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<BroadcastBean>>())
                .subscribe(new BaseObserver<List<BroadcastBean>>() {
                    @Override
                    public void onNext(List<BroadcastBean> beans) {
                        if(view!=null){view.initDataSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }

    @Override
    public void subscribeLive(String useraccountid, String live_id,int position) {
        model.subscribeLive(useraccountid,live_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean subscribe) {
                        if(view!=null){view.subscribeLiveSuccess(subscribe,position);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.subscribeLiveFail(e);}
                    }
                });
    }
}
