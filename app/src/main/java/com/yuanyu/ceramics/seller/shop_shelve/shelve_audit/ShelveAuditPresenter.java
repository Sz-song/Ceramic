package com.yuanyu.ceramics.seller.shop_shelve.shelve_audit;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShelveAuditPresenter extends BasePresenter<ShelveAuditConstract.IShelveAuditView> implements ShelveAuditConstract.IShelveAuditPresenter {
    private ShelveAuditConstract.IShelveAuditModel model;

    public ShelveAuditPresenter() {
        model=new ShelveAuditModel();
    }

    @Override
    public void getShelveAuditData(String shopid, int type, int page) {
        model.getShelveAuditData(shopid,type,page)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<List<ShelveAuditBean>>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ShelveAuditBean>>() {
                    @Override
                    public void onNext(List<ShelveAuditBean> list) {if(view!=null) view.getShelveAuditDataSuccess(list); }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null) view.getShelveAuditDataFail(e); }
                });
    }

    @Override
    public void deleteShelveAudit(String shopid, String id, int position) {
        model.deleteShelveAudit(shopid,id)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[]strings) {if(view!=null) view.deleteShelveAuditSuccess(position); }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null) view.getShelveAuditDataFail(e); }
                });
    }
}
