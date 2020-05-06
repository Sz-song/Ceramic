package com.yuanyu.ceramics.dingzhi;

import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DingzhiDetailUserPresenter extends BasePresenter<DingzhiDetailUserConstact.IDingzhiDetailUserView> implements DingzhiDetailUserConstact.IDingzhiDetailUserPresenter {
    private DingzhiDetailUserConstact.IDingzhiDetailUserModel model;

    DingzhiDetailUserPresenter() { model=new DingzhiDetailUserModel();}

    @Override
    public void dingzhiDetail(String dingzhi_id, String useraccountid) {
        model.dingzhiDetail(dingzhi_id,useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<DingzhiDetailBean>())
                .subscribe(new BaseObserver<DingzhiDetailBean>() {
                    @Override
                    public void onNext(DingzhiDetailBean detailBean) { if(view!=null){view.dingzhiDetailSuccess(detailBean);}}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.dingzhiDetailFail(e);} }
                });
    }

    @Override
    public void generateBondOrder(String dingzhi_id, String useraccountid, int type, int paytype, AddressManageBean bean) {
        model.generateBondOrder(dingzhi_id,useraccountid,type,paytype, bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<GenerateOrdersBean>() {
                    @Override
                    public void onNext(GenerateOrdersBean s) {
                        if(view!=null){view.generateBondOrderSuccess(s,paytype);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.generateBondOrderFail(e);}
                    }
                });
    }

    @Override
    public void BondPay(String id, int type , String out_trade_no, String trade_no) {
        model.BondPay(id,type,out_trade_no,trade_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.BondPaySuccess(aBoolean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.BondPayFail(e);}
                    }
                });
    }

    @Override
    public void confirmReceipt(String dingzhi_id, String useraccountid) {
        model.confirm_receipt(dingzhi_id,useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.confirmReceiptSuccess(aBoolean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.confirmReceiptFail(e);}
                    }
                });
    }
}
