package com.yuanyu.ceramics.shop_index;


import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopPinglunPresenter extends BasePresenter<ShopPinglunConstract.IShopPinglunView> implements ShopPinglunConstract.IShopPinglunPresenter {
    private ShopPinglunConstract.IShopPinglunModel model;

    public ShopPinglunPresenter() {
        model=new ShopPinglunModel();
    }
    @Override
    public void getShopPinglun(String shopid, int page, int type) {
        model.getShopPinglun(shopid,page,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<ShopPinglunBean>())
                .subscribe(new BaseObserver<ShopPinglunBean>() {
                    @Override
                    public void onNext(ShopPinglunBean shopPinglunBean) {
                        if(view!=null){ view.getShopPinglunSuccess(shopPinglunBean); }
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.getShopPinglunFail(e);}
                    }
                });
    }
}
