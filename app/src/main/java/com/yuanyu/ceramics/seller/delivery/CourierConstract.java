package com.yuanyu.ceramics.seller.delivery;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface CourierConstract {
    interface ICourierModel{
        Observable<BaseResponse<List<CourierBean>>> getCourierData(String status, DeliveryBean bean);
        Observable<BaseResponse<List<CourierBean>>> getCourierCompany();
    }

    interface ICourierView{
        void getCourierDataSuccess(List<CourierBean> list);
        void getCourierCompanySuccess(List<CourierBean> list);
        void getCourierDataFail(ExceptionHandler.ResponeThrowable e);
    }

    interface ICourierPresenter{
        void getCourierData(String status, DeliveryBean bean);
        void getCourierCompany();
    }
}
