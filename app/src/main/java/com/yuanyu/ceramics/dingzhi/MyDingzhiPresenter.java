package com.yuanyu.ceramics.dingzhi;



import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyDingzhiPresenter extends BasePresenter<MyDingzhiConstract.IMyDingzhiView> implements MyDingzhiConstract.IMyDingzhiPresenter {
    private MyDingzhiConstract.IMyDingzhiModel model;

    MyDingzhiPresenter() {model=new MyDingzhiModel();}
    @Override
    public void getMyDingzhi(int page, int status, int id) {
        model.getMyDingzhi(page,status,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MyDingzhiBean>>())
                .subscribe(new BaseObserver<List<MyDingzhiBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                       if(view!=null){view.getMyDingzhiFail(e);}
                    }

                    @Override
                    public void onNext(List<MyDingzhiBean> list) {
                        if(view!=null){view.getMyDingzhiSuccess(list);}
                    }
                });

    }
}
