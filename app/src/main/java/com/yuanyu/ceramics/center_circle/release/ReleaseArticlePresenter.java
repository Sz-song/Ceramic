package com.yuanyu.ceramics.center_circle.release;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.center_circle.detail.ArticleContentBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ReleaseArticlePresenter extends BasePresenter<ReleaseArticleConstract.IReleaseArticleView> implements ReleaseArticleConstract.IReleaseArticlePresenter {
    private ReleaseArticleConstract.IReleaseArticleModel model;

    public ReleaseArticlePresenter() {
        model=new ReleaseArticleModel();
    }

    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list).observeOn(Schedulers.io())
                .map(list1 -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(context).load(list1).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    if(view!=null){
                        view.compressImagesSuccess(files);
                    }
                });
    }

    @Override
    public void uploadImage(List<File> images) {
        for(int i=0;i<images.size();i++){
            L.e(images.get(i).getAbsolutePath());
        }
        model.uploadImage(images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {if(view!=null){view.uploadImageSuccess(list);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.uploadImageFail(e);}}
                });
    }

    @Override
    public void releaseArticle(String cover, String title, int useraccountid, List<ArticleContentBean> content) {
        //去除多余
        List<ArticleContentBean> list=new ArrayList<>();
        for(int i=0;i<content.size();i++){
            if(content.get(i).getContent().length()>0){
                list.add(content.get(i));
            }
        }
        model.releaseArticle(cover,title,useraccountid,list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.releaseArticleSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.releaseArticleFail(e);}}
                });
    }
}
