package com.yuanyu.ceramics.seller.index;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface SellerIndexConstract {
    interface IMineModel{
        Observable<BaseResponse<SellerIndexBean>> initData(String shopid);
        Observable<BaseResponse<String[]>>replaceImage(String shopid,int type,String image);
        Observable<BaseResponse<List<String>>>uploadImage(List<File> images);
    }
    interface IMineView{
        void initDataSuccess(SellerIndexBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
        void replaceImageSuccess(String image,int type);
        void replaceImageFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMinePresenter{
        void initData(String shopid);
        void compressImage(Context context, List<String> image, int type, String shop_id);
        void uploadImage(List<File> images,int type,String shop_id);
        void replaceImage(String shopid,int type,String image);
    }
}
