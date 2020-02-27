package com.yuanyu.ceramics.order;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import java.util.List;
import io.reactivex.Observable;

public interface OrderDetailConstract {
    interface IOrderDetailModel{
        Observable<BaseResponse<OrderDetailBean>> getOrderDetail(String useraccountid, String ordernum);
//        Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid);
        Observable<BaseResponse<String[]>> cancelOrder(String useraccountid, String ordernum);
        Observable<BaseResponse<String[]>> comfirmReceived(String useraccountid, String ordernum);
//        Observable<BaseResponse<GenerateOrdersBean>> generateOrders(String useraccountid, int paytype, int type, int tag, int qiugou_id, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list);
//        Observable<BaseResponse<Boolean>> sendAlipay(String useraccountid, List<String> order_list, String out_trade_no, String trade_no);
//        Observable<BaseResponse<String[]>> notifyOrderException(List<String> order_list, String useraccountid, String out_trade_no, String trade_no);
    }

    interface IOrderDetailView{
        void getOrderDetailSuccess(OrderDetailBean bean);
        void getOrderDetailFail(ExceptionHandler.ResponeThrowable e);

//        void getLogisticsTracingSuccess(LogisticsBean bean);
//        void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e);

        void cancelOrderSuccess();
        void cancelOrderFail(ExceptionHandler.ResponeThrowable e);

        void comfirmReceivedSuccess();
        void comfirmReceivedFail(ExceptionHandler.ResponeThrowable e);

//        void generateOrdersSuccess(GenerateOrdersBean bean, int paytype);
//        void generateOrdersFail(ExceptionHandler.ResponeThrowable e);

//        void sendAlipaySuccess(Boolean aboolean);
//        void sendAlipayFail(ExceptionHandler.ResponeThrowable e, String out_trade_no, String trade_no);
//
//        void notifyOrderExceptionSuccess();
//        void notifyOrderExceptionFail(ExceptionHandler.ResponeThrowable e);

    }

    interface IOrderDetailPresenter{
        void getOrderDetail(String useraccountid, String ordernum);
//        void getLogisticsTracing(String couriernum, String courierid);
        void cancelOrder(String useraccountid, String ordernum);
        void comfirmReceived(String useraccountid, String ordernum);
//        void generateOrders(int useraccountid, int paytype, int type, int tag, int qiugou_id, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list);
//        List<SumOrderBean> initOrderList(String commodityId, String orderId, String comment, String shopId);
//        void sendAlipay(String useraccountid, List<String> order_list, String out_trade_no, String trade_no);
//        void notifyOrderException(String orderid, String useraccountid, String out_trade_no, String trade_no);
    }
}
