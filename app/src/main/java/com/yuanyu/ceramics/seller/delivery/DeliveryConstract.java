package com.yuanyu.ceramics.seller.delivery;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface DeliveryConstract {
    interface IDeliveryModel{
        Observable<BaseResponse<DeliveryBean>> getDeliveryMsg(String ordernum, String shop_id);
        Observable<BaseResponse<String>>AutoDelivery(String shop_id, DeliveryBean bean);
        Observable<BaseResponse<String>>HandDelivery(String shop_id, DeliveryBean bean);
    }

    interface IDeliveryView{
        void getDeliveryMsgSuccess(DeliveryBean bean);
        void getDeliveryMsgFail(ExceptionHandler.ResponeThrowable e);
        void deliverySuccess();
        void deliveryFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IDeliveryPresenter{
        void getDeliveryMsg(String ordernum, String shop_id);
        void AutoDelivery(String shop_id, DeliveryBean bean);
        void HandDelivery(String shop_id, DeliveryBean bean);
    }
}
