package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BasePresenter;

public class SellerIndexPresenter extends BasePresenter<SellerIndexConstract.IMineView> implements SellerIndexConstract.IMinePresenter {
    private SellerIndexConstract.IMineModel model;
    SellerIndexPresenter() {model=new SellerIndexModel();}
    @Override
    public void initData(String shopid) {
        SellerIndexBean bean=new SellerIndexBean(1,"img/banner1.jpg","李建宏工作室","瓷艺是个辛苦活，真正是需要“工匠精神”");
        if(view!=null){view.initDataSuccess(bean);}
        //        model.initData(useraccountid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<SellerIndexBean>())
//                .subscribe(new BaseObserver<SellerIndexBean>() {
//                    @Override
//                    public void onNext(SellerIndexBean bean) {
//                        if(view!=null){view.initDataSuccess(bean);}
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if(view!=null){view.initDataFail(e);}
//                    }
//                });
    }
}
