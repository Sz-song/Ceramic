package com.yuanyu.ceramics.seller.order;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopOrderPresenter extends BasePresenter<ShopOrderConstract.IShopOrderView> implements ShopOrderConstract.IShopOrderPresenter {
    private ShopOrderConstract.IShopOrderModel model;
    ShopOrderPresenter() {
        model=new ShopOrderModel();
    }
    @Override
    public void getOrderList(String shopid, int page, int type) {
        model.getOrderList(shopid,page,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<ShopOrderBean>>())
                .subscribe(new BaseObserver<List<ShopOrderBean>>() {
                    @Override
                    public void onNext(List<ShopOrderBean> ordersBeans) { if(view!=null){ view.getOrderListSuccess(ordersBeans);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.getOrderListFail(e);}
                    }
                });
    }
}
