package com.yuanyu.ceramics.order.refund;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.order.OrderDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ApplyRefundPresenter extends BasePresenter<ApplyRefundConstract.IApplyRefundView> implements ApplyRefundConstract.IApplyRefundPresenter {
    private ApplyRefundConstract.IApplyRefundModel model;

    ApplyRefundPresenter() {
        model=new ApplyRefundModel();
    }

    @Override
    public void getOrderDetail(int useraccountid, String ordernum) {
        model.getOrderDetail(useraccountid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<OrderDetailBean>())
                .subscribe(new BaseObserver<OrderDetailBean>() {
                    @Override
                    public void onNext(OrderDetailBean orderDetailBean) {
                        if(view!=null){view.getOrderDetailSuccess(orderDetailBean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getOrderDetailFail(e);}
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list).observeOn(Schedulers.io())
                .map(list1 -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(context).load(list1).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    if(view!=null){
                        view.compressImagesSuccess(files);
                    }
                });
    }

    @Override
    public void uploadImage(List<File> images) {
        for(int i=0;i<images.size();i++){
            L.e(images.get(i).getAbsolutePath());
        }
        model.uploadImage(images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {if(view!=null){view.uploadImageSuccess(list);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.uploadImageFail(e);}}
                });
    }

    @Override
    public void submitRefund(RefundBean bean) {
        model.submitRefund(bean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.submitRefundFail(e);}}

                    @Override
                    public void onNext(Object o) {if(view!=null){view.submitRefundSuccess();}}
                });
    }

}
