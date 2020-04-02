package com.yuanyu.ceramics.center_circle.release;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


public interface ReleaseDynamicConstract {
    interface IReleaseDynamicModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);//上传图片
        Observable<BaseResponse<Boolean>> releaseDynamic(String id,String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent);//发布文章
        Observable<BaseResponse<Boolean>> savedraftsDynamic(String id,String useraccountid, List<DynamicContentBean> inputcontent, String inputstr, List<String> imglist,List<FriendBean> peoplelist,boolean isopen);
        Observable<BaseResponse<DraftsDynamic>> getDynamic(String id);
    }

    interface IReleaseDynamicView {
        void compressImagesSuccess(List<File> list);
        void compressImagesFail();

        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);

        void releaseDynamicSuccess(Boolean b);
        void releaseDynamicFail(ExceptionHandler.ResponeThrowable e);

        void savedraftsDynamicSuccess(Boolean b);
        void savedraftsDynamicFail(ExceptionHandler.ResponeThrowable e);

        void getDynamicSuccess(DraftsDynamic b);
        void getDynamicFail(ExceptionHandler.ResponeThrowable e);

    }
    interface IReleaseDynamicPresenter {
        void compressImages(Context context, List<String> list);//压缩图片
        void uploadImage(List<File> images);//上传图片
        void releaseDynamic(String id,String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent);//发布文章
        void savedraftsDynamic(String id, String useraccountid, List<DynamicContentBean> inputcontent, String inputstr, List<String> imglist, List<FriendBean> peoplelist, boolean isopen);//保存到草稿箱
        void getDynamic(String id);
    }
}
