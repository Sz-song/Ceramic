package com.yuanyu.ceramics.seller.refund;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefundPresenter extends BasePresenter<RefundConstract.IRefundView> implements RefundConstract.IRefundPresenter {
    private RefundConstract.IRefundModel model;

    public RefundPresenter() {
        model=new RefundModel();
    }

    @Override
    public void getRefundList(String shopid, int page, int type) {
        model.getRefundList(shopid,page, type)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<List<RefundBean>>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<RefundBean>>() {
                    @Override
                    public void onNext(List<RefundBean> listrefundbean) {
                        if(view!=null){
                            view.getRefundListSuccess(listrefundbean);
                        }
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){
                            view.getRefundListFail(e);
                        }
                    }
                });
    }
}
