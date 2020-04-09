package com.yuanyu.ceramics.mine.dashiattestation;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class DashiAttestationPresenter extends BasePresenter<DashiAttestationConstract.IDashiAttestationView> implements DashiAttestationConstract.IDashiAttestationPresenter {
    private DashiAttestationConstract.IDashiAttestationModel model;

    public DashiAttestationPresenter() {
        model=new DashiAttrestModel();
    }

    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list).observeOn(Schedulers.io())
                .map(list1 -> Luban.with(context).load(list1).get()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::uploadImage);//uploadImage(files)
    }

    @Override
    public void uploadImage(List<File> images) {
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
    public void MasterAttestation(String useraccountid, String name, String tel, String idcard, String goodat, List<String> list) {
        model.MasterAttestation(useraccountid, name, tel, idcard,goodat,list)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        view.MasterAttestationFail(e);
                    }
                    @Override
                    public void onNext(String[] strings) {
                        view.MasterAttestationSuccess(strings);
                    }
                });
    }
}
