package com.yuanyu.ceramics.seller.order.detail;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopOrderDetailPresenter extends BasePresenter<ShopOrderDetailConstract.IShopOrderDetailView> implements ShopOrderDetailConstract.IShopOrderDetailPresenter {
    private ShopOrderDetailConstract.IShopOrderDetailModel model;

    public ShopOrderDetailPresenter() {
        model=new ShopOrderDetailModel();
    }

    @Override
    public void shopGetOrderDetail(String shopid, String ordernum) {
        model.shopGetOrderDetail(shopid,ordernum)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<ShopOrderDetailBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShopOrderDetailBean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.shopGetOrderDetailFail(e);} }
                    @Override
                    public void onNext(ShopOrderDetailBean bean){if(view!=null){ view.shopGetOrderDetailSuccess(bean);} }
                });
    }

    @Override
    public void getLogisticsTracing(String couriernum, String courierid) {
        model.getLogisticsTracing(couriernum,courierid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<LogisticsBean>())
                .subscribe(new BaseObserver<LogisticsBean>() {
                    @Override
                    public void onNext(LogisticsBean logisticsBean) { if(view!=null){view.getLogisticsTracingSuccess(logisticsBean);} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.getLogisticsTracingFail(e);}}
                });
    }

    @Override
    public void modityOrderPrice(String shopid, String ordernum, String price) {
        model.modityOrderPrice(shopid,ordernum,price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean bool) {if(view!=null){view.modityOrderPriceSuccess(price);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.modityOrderPriceFail(e);}}
                });
    }
}
