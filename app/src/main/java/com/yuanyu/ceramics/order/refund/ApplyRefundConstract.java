package com.yuanyu.ceramics.order.refund;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.order.OrderDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface ApplyRefundConstract {
    interface IApplyRefundModel {
        Observable<BaseResponse<OrderDetailBean>> getOrderDetail(int useraccountid, String ordernum);
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);//上传图片
        Observable<BaseResponse> submitRefund(RefundBean bean);
    }

    interface IApplyRefundView {
        void getOrderDetailSuccess(OrderDetailBean bean);
        void getOrderDetailFail(ExceptionHandler.ResponeThrowable e);

        void compressImagesSuccess(List<File> list);
        void compressImagesFail();

        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);

        void submitRefundSuccess();
        void submitRefundFail(ExceptionHandler.ResponeThrowable e);

    }
    interface IApplyRefundPresenter {
        void getOrderDetail(int useraccountid, String ordernum);
        void compressImages(Context context, List<String> list);//压缩图片
        void uploadImage(List<File> images);//上传图片
        void submitRefund(RefundBean bean);
    }
}
