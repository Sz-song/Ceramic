package com.yuanyu.ceramics.order;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyOrderFragmentPresenter extends BasePresenter<MyOrderFragmentConstract.IOrderFragmentView> implements MyOrderFragmentConstract.IOrderFragmentPresenter{
    private MyOrderFragmentConstract.IOrderFragmentModel model;
    MyOrderFragmentPresenter() {model=new MyOrderFragmentModel();}
    @Override
    public void getOrderList(String useraccountid, int page, int status) {
        model.getOrderList(useraccountid, page, status)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<List<MyOrderFragmentBean>>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MyOrderFragmentBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getOrderListFail(e);}
                    }

                    @Override
                    public void onNext(List<MyOrderFragmentBean> list) {
                        if(view!=null){view.getOrderListSuccess(list);}
                    }
                });
    }

    @Override
    public void cancelOrder(String uid, String order_num, int position) {
        model.cancelOrder(uid, order_num)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.cancelOrderFail(e);}
                    }

                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.cancelOrderSuccess(position);}
                    }
                });
    }

    @Override
    public void deleteOrder(String uid, String order_num, int position) {
        model.deleteOrder(uid, order_num)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.deleteOrderFail(e);}
                    }

                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.deleteOrderSuccess(position);}
                    }
                });
    }

    @Override
    public void remindDelivery(String uid, String order_id, int position) {
        model.remindDelivery(uid, order_id)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.remindDeliveryFail(e);}
                    }

                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.remindDeliverySuccess(position);}
                    }
                });
    }

    @Override
    public void confirmReceived(String useraccountid, String ordernum, int position) {
        model.confirmReceived(useraccountid, ordernum)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.confirmReceivedFail(e);}
                    }

                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.confirmReceivedSuccess(position);}
                    }
                });
    }
}
