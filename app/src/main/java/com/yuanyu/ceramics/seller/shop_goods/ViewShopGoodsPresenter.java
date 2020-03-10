package com.yuanyu.ceramics.seller.shop_goods;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewShopGoodsPresenter extends BasePresenter<ViewShopGoodsConstract.IViewShopGoodsView> implements ViewShopGoodsConstract.IViewShopGoodsPresenter {
    private ViewShopGoodsConstract.IViewShopGoodsModel model;

    public ViewShopGoodsPresenter() { model=new ViewShopGoodsModel();}

    @Override
    public void getShopGoodsDetail(String shopid, String id) {
        model.getShopGoodsDetail(shopid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<ShelvingDetailBean>())
                .subscribe(new BaseObserver<ShelvingDetailBean>() {
                    @Override
                    public void onNext(ShelvingDetailBean shelvingDetailBean) {if(view!=null){view.getShopGoodsDetailSuccess(shelvingDetailBean);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.getShopGoodsDetailFail(e);}}
                });
    }
}
