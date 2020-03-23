package com.yuanyu.ceramics.dingzhi;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ShopDingzhiDetailConstract {

    interface IShopDingzhiDetailModel {
        Observable<BaseResponse<DingzhiDetailBean>> dingzhiDetail(String dingzhi_id, String useraccountid);
        Observable<BaseResponse<Boolean>> confirmOrder(String dingzhi_id, String shop_id, String price, String time);
        Observable<BaseResponse<Boolean>> cancelOrder(String dingzhi_id, String shop_id);
        Observable<BaseResponse<Boolean>> dingzhiCourier(String dingzhi_id, String shop_id, String courier_id, String courier_num);
    }
    interface IShopDingzhiDetailView {
        void dingzhiDetailSuccess(DingzhiDetailBean detailBean);
        void dingzhiDetailFail(ExceptionHandler.ResponeThrowable e);

        void confirmOrderSuccess(Boolean aBoolean);
        void confirmOrderFail(ExceptionHandler.ResponeThrowable e);
        void showToast(String msg);

        void cancelOrderSuccess(Boolean aBoolean);
        void cancelOrderFail(ExceptionHandler.ResponeThrowable e);

        void dingzhiCourierSiccess(Boolean aBoolean);
        void dingzhiCourierFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopDingzhiDetailPresenter {
        void dingzhiDetail(String dingzhi_id, String useraccountid);
        void confirmOrder(String dingzhi_id, String shop_id, String price, String time);
        void cancelOrder(String dingzhi_id, String shop_id);
        void dingzhiCourier(String dingzhi_id, String shop_id, String courier_id, String courier_num);
    }
}
