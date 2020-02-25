package com.yuanyu.ceramics.shop_index;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopGoodsPresenter extends BasePresenter<ShopGoodsConstract.IShopGoodsView> implements ShopGoodsConstract.IShopGoodsPresenter {
    private ShopGoodsConstract.IShopGoodsModel model;

    ShopGoodsPresenter() {
        model=new ShopGoodsModel();
    }

    @Override
    public void getShopGoodsList(String shopid, int page, int type) {

        model.getShopGoodsList(shopid,page,type)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<List<ShopGoodsBean>>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ShopGoodsBean>>() {
                    @Override
                    public void onNext(List<ShopGoodsBean> shopGoodsBeans) {
                        if(view!=null){view.getShopGoodsSuccess(shopGoodsBeans);}
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getShopGoodsFail(e);}
                    }
                });
    }
}
