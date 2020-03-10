package com.yuanyu.ceramics.seller.delivery;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CourierPresenter extends BasePresenter<CourierConstract.ICourierView> implements CourierConstract.ICourierPresenter {
    private CourierConstract.ICourierModel model;

    CourierPresenter() {
        model=new CourierModel();
    }
    @Override
    public void getCourierData(String status, DeliveryBean bean) {
        model.getCourierData(status,bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<CourierBean>>())
                .subscribe(new BaseObserver<List<CourierBean>>() {
                    @Override
                    public void onNext(List<CourierBean> courierBeans) {
                        if(view!=null){ view.getCourierDataSuccess(courierBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.getCourierDataFail(e);}
                    }
                });
    }

    @Override
    public void getCourierCompany() {
        model.getCourierCompany()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<CourierBean>>())
                .subscribe(new BaseObserver<List<CourierBean>>() {
                    @Override
                    public void onNext(List<CourierBean> courierBeans) {
                        if(view!=null){ view.getCourierCompanySuccess(courierBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.getCourierDataFail(e);}
                    }
                });
    }
}
