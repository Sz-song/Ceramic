package com.yuanyu.ceramics.order.refund;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefundListPresenter extends BasePresenter<RefundListConstract.IRefundListView> implements RefundListConstract.IRefundListPresenter {
    private RefundListConstract.IRefundListModel model;

    RefundListPresenter() {model=new RefundListModel();}

    @Override
    public void getRefundList(String useraccountid, int page) {
        model.getRefundList(useraccountid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<RefundListBean>>())
                .subscribe(new BaseObserver<List<RefundListBean>>() {
                    @Override
                    public void onNext(List<RefundListBean> refundListBeans) {
                        if(view!=null){view.getRefundListSuccess(refundListBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getRefundListFail(e);}
                    }
                });
    }
}
