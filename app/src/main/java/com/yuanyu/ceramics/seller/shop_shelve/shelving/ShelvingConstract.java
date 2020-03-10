package com.yuanyu.ceramics.seller.shop_shelve.shelving;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface ShelvingConstract {
    interface IShelvingModel {
        Observable<BaseResponse<String[]>> Shelving(String shopid, ShelvingDetailBean bean);
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);
        Observable<BaseResponse<VideoBean>> uploadVideo(File video);
    }

    interface IShelvingView {
        void compressImagesSuccess(List<File> list);
        void uploadImageSuccess(List<String> list);
        void compressVideoSuccess(String path);
        void uploadVideoSuccess(VideoBean videoBean);
        void ShelvingSuccess();

        void compressImagesFail();
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);
        void compressVideoFail();
        void uploadVideoFail(ExceptionHandler.ResponeThrowable e);
        void ShelvingFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShelvingPresenter {
        void Shelving(String shopid, ShelvingDetailBean bean);
        void compressImages(Context context, List<String> list);
        void compressVideo(Context context, String path);
        void uploadImage(List<File> images);
        void uploadVideo(String video);
        void initList(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> chanzhuangList, List<FenleiTypeBean> ticaiList, List<FenleiTypeBean> piseList, List<FenleiTypeBean> zhongleiList);
    }
}
