package com.yuanyu.ceramics.order;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailPresenter extends BasePresenter<OrderDetailConstract.IOrderDetailView> implements OrderDetailConstract.IOrderDetailPresenter {
    private OrderDetailConstract.IOrderDetailModel model;
    OrderDetailPresenter() {model=new OrderDetailModel();}
    @Override
    public void getOrderDetail(String useraccountid, String ordernum) {
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

//    @Override
//    public void getLogisticsTracing(String couriernum, String courierid) {
//        model.getLogisticsTracing(couriernum,courierid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<LogisticsBean>())
//                .subscribe(new BaseObserver<LogisticsBean>() {
//                    @Override
//                    public void onNext(LogisticsBean logisticsBean) { if(view!=null){view.getLogisticsTracingSuccess(logisticsBean);} }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.getLogisticsTracingFail(e);}}
//                });
//    }

    @Override
    public void cancelOrder(String useraccountid, String ordernum) {
        model.cancelOrder(useraccountid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.cancelOrderFail(e);}
                    }
                    @Override
                    public void onNext(Object o) {
                        if(view!=null){view.cancelOrderSuccess();}
                    }
                });
    }

    @Override
    public void comfirmReceived(String useraccountid, String ordernum) {
        model.comfirmReceived(useraccountid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.comfirmReceivedFail(e);}
                    }
                    @Override
                    public void onNext(Object o) {
                        if(view!=null){view.comfirmReceivedSuccess();}
                    }
                });
    }

//    @Override
//    public void generateOrders(String useraccountid, int paytype, int type, int tag, int qiugou_id, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list) {
//        model.generateOrders(useraccountid, paytype, type, 1, qiugou_id, name, address, province, city, area, tel, list)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<GenerateOrdersBean>())
//                .subscribe(new BaseObserver<GenerateOrdersBean>() {
//                    @Override
//                    public void onNext(GenerateOrdersBean generateOrdersBean) {if(view!=null){view.generateOrdersSuccess(generateOrdersBean,paytype);}}
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.generateOrdersFail(e);} }
//                });
//    }
//
//    @Override
//    public List<SumOrderBean> initOrderList(String commodityId, String orderId, String comment, String shopId) {
//        List<SumOrderBean> list = new ArrayList<>();
//        List<ItemCellbean> item_lists = new ArrayList<>();
//        ItemCellbean item = new ItemCellbean(commodityId, 1, orderId);
//        item_lists.add(item);
//        SumOrderBean sumOrderBean = new SumOrderBean(item_lists, comment, Integer.parseInt(shopId));
//        list.add(sumOrderBean);
//        return list;
//    }
//
//    @Override
//    public void sendAlipay(String useraccountid, List<String> order_list, String out_trade_no, String trade_no) {
//        model.sendAlipay(useraccountid, order_list, out_trade_no, trade_no)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
//                .subscribe(new BaseObserver<Boolean>() {
//                    @Override
//                    public void onNext(Boolean b) {if(view!=null){view.sendAlipaySuccess(b);}}
//
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if(view!=null){view.sendAlipayFail(e,out_trade_no,trade_no);}
//                    }
//                });
//    }
//
//    @Override
//    public void notifyOrderException(String orderid, String useraccountid, String out_trade_no, String trade_no) {
//        List<String> orderList = new ArrayList<>();
//        orderList.add(orderid);
//        model.notifyOrderException(orderList, useraccountid,out_trade_no, trade_no)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
//                .subscribe(new BaseObserver<String[]>() {
//                    @Override
//                    public void onNext(String[] strings) {if(view!=null){view.notifyOrderExceptionSuccess();}}
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.notifyOrderExceptionFail(e);}}
//                });
//    }


}
