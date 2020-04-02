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
        Observable<BaseResponse<Boolean>> releaseArticle(String id,String cover, String title, String useraccountid, List<ArticleContentBean> content);//发布文章
        Observable<BaseResponse<Boolean>> saveArticle(String id,String cover, String title, String useraccountid,List<ArticleContentBean> content );//保存文章
        Observable<BaseResponse<DraftsArticle>> getArticle(String id);
        Observable<BaseResponse<String[]>> deleteArticle(String useraccountid, String id);
    }

    interface IReleaseArticleView {
        void compressImagesSuccess(List<File> list);
        void compressImagesFail();

        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);

        void releaseArticleSuccess(Boolean b);
        void releaseArticleFail(ExceptionHandler.ResponeThrowable e);

        void saveArticleSuccess(Boolean b);
        void saveArticleFail(ExceptionHandler.ResponeThrowable e);

        void getArticleSuccess(DraftsArticle b);
        void getArticleFail(ExceptionHandler.ResponeThrowable e);

        void deleteArticleSuccess(String[] b);
        void deleteArticleFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IReleaseArticlePresenter {
        void compressImages(Context context, List<String> list);//压缩图片
        void uploadImage(List<File> images);//上传图片
        void releaseArticle(String id,String cover, String title, String useraccountid, List<ArticleContentBean> content);//发布文章
        void saveArticle(String id,String cover, String title, String useraccountid, List<ArticleContentBean> content);
        void getArticle(String id);
        void deleteArticle(String useraccountid,String id);
    }
}
