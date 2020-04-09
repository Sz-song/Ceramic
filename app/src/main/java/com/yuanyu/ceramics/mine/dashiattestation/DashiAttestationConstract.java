package com.yuanyu.ceramics.mine.dashiattestation;

import android.content.Context;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface DashiAttestationConstract {
    interface IDashiAttestationModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);
        Observable<BaseResponse<String[]>> MasterAttestation(String useraccountid, String name, String tel, String idcard, String goodat, List<String> list);
    }

    interface IDashiAttestationView {
        void uploadImageSuccess(List<String> list);
        void MasterAttestationSuccess(String[] strings);

        void uploadImageFail(ExceptionHandler.ResponeThrowable e);
        void MasterAttestationFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IDashiAttestationPresenter {
        void compressImages(Context context, List<String> list);
        void uploadImage(List<File> images);
        void MasterAttestation(String useraccountid, String name, String tel, String idcard, String goodat, List<String> list);
    }
}
