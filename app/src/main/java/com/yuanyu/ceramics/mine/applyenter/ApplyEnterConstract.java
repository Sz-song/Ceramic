package com.yuanyu.ceramics.mine.applyenter;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


public interface ApplyEnterConstract {
    interface IApplyEnterModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);
        Observable<BaseResponse<String[]>> ApplyEnter(String useraccountid, String name, String telephone, String idcard_num, String shop_name, String address, String city, List<String> image);
    }
    interface IApplyEnterView {
        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);
        void ApplyEnterSuccess();
        void ApplyEnterFail(ExceptionHandler.ResponeThrowable e);
        void showToast(String msg);
    }

    interface IApplyEnterPresenter {
        String getCity(String address);
        void compressImages(Context context, String name, String telephone, String idcard_num, String shop_name, String location, String address, String city, String image_positive, String image_reverse, String image_shop);
        void uploadImage(List<File> images);
        void ApplyEnter(String useraccountid, String name, String telephone, String idcard_num, String shop_name, String address, String city, List<String> image);
    }
}
