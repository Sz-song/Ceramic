package com.yuanyu.ceramics.seller.delivery;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WaitDeliveryPresenter extends BasePresenter<WaitDeliveryConstract.IWaitDeliveryView> implements WaitDeliveryConstract.IWaitDeliveryPresenter {
    private WaitDeliveryConstract.IWaitDeliveryModel model;
    public WaitDeliveryPresenter() {
        model=new WaitDeliveryModel();
    }
    @Override
    public void getDeliveryOrdersResult(String shopid, int page) {
        model.getDeliveryOrdersResult(shopid, page)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<List<WaitDeliveryBean>>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WaitDeliveryBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.setDataFail(e);}
                    }
                    @Override
                    public void onNext(List<WaitDeliveryBean> list) {
                        if(view!=null){ view.setDataSuccess(list);}
                    }
                });
    }
}
