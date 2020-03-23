package com.yuanyu.ceramics.dingzhi;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopDingzhiDetailPresenter extends BasePresenter<ShopDingzhiDetailConstract.IShopDingzhiDetailView> implements ShopDingzhiDetailConstract.IShopDingzhiDetailPresenter {
    private ShopDingzhiDetailConstract.IShopDingzhiDetailModel model;

    ShopDingzhiDetailPresenter() { model=new ShopDingzhiDetailModel();}

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
    public void confirmOrder(String dingzhi_id, String shop_id, String price, String time) {
        if(price.length()==0){
            if(view!=null){view.showToast("请填写价格");}
            return;
        }
        if(time.length()==0){
            if(view!=null){view.showToast("请选择完成时间");}
            return;
        }
        try {
            if((new Date().getTime()/1000)>(Long.parseLong(time)-(7*24*60*60))){
                if(view!=null){view.showToast("完成时间需要大于当前7天");}
                return;
            }
        }catch (Exception e){
            L.e(e.getMessage());
            if(view!=null){view.showToast("请选择正确时间");}
            return;
        }
        model.confirmOrder(dingzhi_id,shop_id,price,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aboolean) { if(view!=null){view.confirmOrderSuccess(aboolean);}}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.confirmOrderFail(e);} }
                });
    }

    @Override
    public void cancelOrder(String dingzhi_id, String shop_id) {
        model.cancelOrder(dingzhi_id,shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aboolean) { if(view!=null){view.cancelOrderSuccess(aboolean);}}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.cancelOrderFail(e);} }
                });
    }

    @Override
    public void dingzhiCourier(String dingzhi_id, String shop_id, String courier_id, String courier_num) {
        if(courier_id.trim().length()==0){
            if(view!=null){view.showToast("请选择快递公司");}
            return;
        }
        if(courier_num.trim().length()==0){
            if(view!=null){view.showToast("请填写快递单号");}
            return;
        }
        model.dingzhiCourier(dingzhi_id,shop_id,courier_id,courier_num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aboolean) { if(view!=null){view.dingzhiCourierSiccess(aboolean);}}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.dingzhiCourierFail(e);} }
                });
    }
}
