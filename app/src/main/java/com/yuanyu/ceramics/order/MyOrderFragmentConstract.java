package com.yuanyu.ceramics.order;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MyOrderFragmentConstract {
    interface IOrderFragmentModel{
        Observable<BaseResponse<List<MyOrderFragmentBean>>> getOrderList(String useraccountid, int page, int status);
        Observable<BaseResponse<String[]>> cancelOrder(String uid, String order_num);
        Observable<BaseResponse<String[]>> deleteOrder(String uid, String order_num);
        Observable<BaseResponse<String[]>> remindDelivery(String uid, String order_id);
        Observable<BaseResponse<String[]>> confirmReceived(String useraccountid, String ordernum);
    }
    interface IOrderFragmentView{
        void getOrderListSuccess(List<MyOrderFragmentBean> listbean);
        void getOrderListFail(ExceptionHandler.ResponeThrowable e);

        void cancelOrderSuccess(int position);
        void cancelOrderFail(ExceptionHandler.ResponeThrowable e);

        void deleteOrderSuccess(int position);
        void deleteOrderFail(ExceptionHandler.ResponeThrowable e);

        void remindDeliverySuccess(int position);
        void remindDeliveryFail(ExceptionHandler.ResponeThrowable e);

        void confirmReceivedSuccess(int position);
        void confirmReceivedFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IOrderFragmentPresenter{
        void getOrderList(String useraccountid, int page, int status);
        void cancelOrder(String uid, String order_num,int position);
        void deleteOrder(String uid, String order_num,int position);
        void remindDelivery(String uid, String order_id,int position);
        void confirmReceived(String useraccountid, String ordernum,int position);
    }
}
