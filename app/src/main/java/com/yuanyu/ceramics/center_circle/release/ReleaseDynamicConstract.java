package com.yuanyu.ceramics.center_circle.release;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


public interface ReleaseDynamicConstract {
    interface IReleaseDynamicModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);//上传图片
        Observable<BaseResponse<Boolean>> releaseDynamic(String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent);//发布文章
    }

    interface IReleaseDynamicView {
        void compressImagesSuccess(List<File> list);
        void compressImagesFail();

        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);

        void releaseDynamicSuccess(Boolean b);
        void releaseDynamicFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IReleaseDynamicPresenter {
        void compressImages(Context context, List<String> list);//压缩图片
        void uploadImage(List<File> images);//上传图片
        void releaseDynamic(String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent);//发布文章
    }
}
