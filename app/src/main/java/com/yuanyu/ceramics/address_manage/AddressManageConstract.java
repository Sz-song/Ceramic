package com.yuanyu.ceramics.address_manage;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface AddressManageConstract {
    interface IAddressManageModel{
        Observable<BaseResponse<List<AddressManageBean>>> getAddressData(String useraccountid);
        Observable<BaseResponse<String[]>> deleteAddress(String useraccountid, String id);
        Observable<BaseResponse<String[]>> setDefaultAddress(String useraccountid, String id);
    }
    interface IAddressManageView{
        void getAddressDataSuccess(List<AddressManageBean> list);
        void getAddressDataFail(ExceptionHandler.ResponeThrowable e);
        void deleteAddressSuccess(int position);
        void deleteAddressFail(ExceptionHandler.ResponeThrowable e);
        void setDefaultAddressSuccess(int position);
        void setDefaultAddressFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IAddressManagePresenter{
       void getAddressData(String useraccountid);
       void deleteAddress(String useraccountid, String id, int position);
       void setDefaultAddress(String useraccountid, String id, int position);
    }
}
