package com.yuanyu.ceramics.seller.shop_goods;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopGoodsPresenter extends BasePresenter<ShopGoodsConstract.IShopGoodsView> implements ShopGoodsConstract.IShopGoodsPresenter {
    private ShopGoodsConstract.IShopGoodsModel model;
    public ShopGoodsPresenter() { model=new ShopGoodsModel(); }
    @Override
    public void getShopGoodsList(String shopid, int page, int type) {
        List<ShopGoodsBean> list=new ArrayList<>();
        ShopGoodsBean shopGoodsBeans=new ShopGoodsBean("1","img/banner1.jpg","元代青花山水瓶仿品","山水","1","18000",1);
        list.add(shopGoodsBeans);
        if (view!=null){view.getShopGoodsSuccess(list);}
//        model.getShopGoodsList(shopid,page,type)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<List<ShopGoodsBean>>())
//                .subscribe(new BaseObserver<List<ShopGoodsBean>>() {
//                    @Override
//                    public void onNext(List<ShopGoodsBean> shopGoodsBeans) {if (view!=null){view.getShopGoodsSuccess(shopGoodsBeans);}}
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {if (view!=null){view.getShopGoodsFail(e);} }
//                });
    }

    @Override
    public void shopGoodsDelete(String shopid, String id, int position) {
        model.shopGoodsDelete(shopid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if (view!=null){view.shopGoodsDeleteSuccess(position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if (view!=null){view.shopGoodsDeleteFail(e);} }
                });
    }

    @Override
    public void shopGoodsOffShelves(String shopid, String id, int position) {
        model.shopGoodsOffShelves(shopid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if (view!=null){view.shopGoodsOffShelvesSuccess(position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if (view!=null){view.shopGoodsOffShelvesFail(e);} }
                });
    }
}
