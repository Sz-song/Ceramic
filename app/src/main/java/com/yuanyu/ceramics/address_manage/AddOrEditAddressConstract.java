package com.yuanyu.ceramics.address_manage;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface AddOrEditAddressConstract {
    interface IAddAddressModel {
        Observable<BaseResponse<String[]>> addAddress(int useraccountid, String name, String phone, String province, String city, String exparea, String address, int isdefault);
        Observable<BaseResponse<String[]>> editAddress(int useraccountid,String id,String name,String phone,String province,String city,String exparea,String address,int isdefault);
    }
    interface IAddAddressView {
        void Success();
        void Fail(ExceptionHandler.ResponeThrowable e);
    }

    interface IAddAddressPresenter {
        void addAddress(int useraccountid,String name,String phone,String province,String city,String exparea,String address,int isdefault);
        void editAddress(int useraccountid,String id,String name,String phone,String province,String city,String exparea,String address,int isdefault);
    }
}
