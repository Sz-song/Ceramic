package com.yuanyu.ceramics.dingzhi;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopDingzhiPresenter  extends BasePresenter<ShopDingzhiFragmentConstract.IShopDingzhiView> implements ShopDingzhiFragmentConstract.IShopDingzhiPresenter {
    private ShopDingzhiFragmentConstract.IShopDingzhiModel model;

    ShopDingzhiPresenter() {model=new ShopDingzhiModel();}
    @Override
    public void getShopDingzhi(int page, int status, String shop_id) {
        model.getShopDingzhi(page,status,shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MyDingzhiBean>>())
                .subscribe(new BaseObserver<List<MyDingzhiBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getShopDingzhizhiFail(e);}
                    }

                    @Override
                    public void onNext(List<MyDingzhiBean> list) {
                        if(view!=null){view.getShopDingzhiSuccess(list);}
                    }
                });
    }
}