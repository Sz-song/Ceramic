package com.yuanyu.ceramics.center_circle.release;

import android.content.Context;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.center_circle.detail.ArticleContentBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface ReleaseArticleConstract {
    interface IReleaseArticleModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);//上传图片
        Observable<BaseResponse<Boolean>> releaseArticle(String cover, String title, String useraccountid, List<ArticleContentBean> content);//发布文章
    }

    interface IReleaseArticleView {
        void compressImagesSuccess(List<File> list);
        void compressImagesFail();

        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);

        void releaseArticleSuccess(Boolean b);
        void releaseArticleFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IReleaseArticlePresenter {
        void compressImages(Context context, List<String> list);//压缩图片
        void uploadImage(List<File> images);//上传图片
        void releaseArticle(String cover, String title, String useraccountid, List<ArticleContentBean> content);//发布文章
    }
}
