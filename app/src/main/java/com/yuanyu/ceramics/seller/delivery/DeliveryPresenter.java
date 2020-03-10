package com.yuanyu.ceramics.seller.delivery;


import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeliveryPresenter extends BasePresenter<DeliveryConstract.IDeliveryView> implements DeliveryConstract.IDeliveryPresenter  {
    private DeliveryConstract.IDeliveryModel model;
    DeliveryPresenter() {
        model=new DeliveryModel();
    }

    @Override
    public void getDeliveryMsg(String ordernum, String shop_id) {
                model.getDeliveryMsg(ordernum, shop_id)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<DeliveryBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DeliveryBean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getDeliveryMsgFail(e);}
                    }
                    @Override
                    public void onNext(DeliveryBean bean) {
                        if(view!=null){ view.getDeliveryMsgSuccess(bean);}
                    }
                });
    }

    @Override
    public void AutoDelivery(String shop_id, DeliveryBean bean) {
                model.AutoDelivery(shop_id,bean)
                        .subscribeOn(Schedulers.io())
                        .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<DeliveryBean>>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            public void onNext(String s) {if(view!=null){view.deliverySuccess();}}
                            @Override
                            public void onError(ExceptionHandler.ResponeThrowable e) {
                                if(view!=null){view.deliveryFail(e);}}
                        });
    }

    @Override
    public void HandDelivery(String shop_id, DeliveryBean bean) {
        model.HandDelivery(shop_id,bean)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<DeliveryBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {if(view!=null){view.deliverySuccess();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.deliveryFail(e);}}
                });
    }
}
