package com.yuanyu.ceramics.center_circle.release;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.DynamicContentBean;
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

public class ReleaseDynamicPresenter extends BasePresenter<ReleaseDynamicConstract.IReleaseDynamicView> implements ReleaseDynamicConstract.IReleaseDynamicPresenter {
    private ReleaseDynamicConstract.IReleaseDynamicModel model;
    public ReleaseDynamicPresenter(){model=new ReleaseDynamicModel();}
    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, List<String> list) {
        for (int i = 0; i <list.size() ; i++) {
            L.e(list.get(i));
        }
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
    public void releaseDynamic(int useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent) {
        List<DynamicContentBean> list=new ArrayList<>();
        for(int i=0;i<listcontent.size();i++){
            if(listcontent.get(i).getContent().length()>0){
                list.add(listcontent.get(i));
            }
        }
        if(view!=null){view.releaseDynamicSuccess(true);}
//        model.releaseDynamic(useraccountid,listimage,listfriends,isopen,list)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
//                .subscribe(new BaseObserver<Boolean>() {
//                    @Override
//                    public void onNext(Boolean b) {if(view!=null){view.releaseDynamicSuccess(b);}}
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.releaseDynamicFail(e);}}
//                });
    }
}
