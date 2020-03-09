package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IndexPresenter extends BasePresenter<IndexConstract.IMineView> implements IndexConstract.IMinePresenter {
    private IndexConstract.IMineModel model;
    IndexPresenter() {model=new IndexModel();}
    @Override
    public void initData(String shopid) {
        IndexBean bean=new IndexBean(1,"img/banner1.jpg","李建宏工作室","瓷艺是个辛苦活，真正是需要“工匠精神”");
        if(view!=null){view.initDataSuccess(bean);}
        //        model.initData(useraccountid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<IndexBean>())
//                .subscribe(new BaseObserver<IndexBean>() {
//                    @Override
//                    public void onNext(IndexBean bean) {
//                        if(view!=null){view.initDataSuccess(bean);}
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if(view!=null){view.initDataFail(e);}
//                    }
//                });
    }
}
