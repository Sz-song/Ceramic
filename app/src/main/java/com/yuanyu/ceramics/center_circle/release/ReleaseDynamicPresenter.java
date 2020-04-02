package com.yuanyu.ceramics.center_circle.release;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.common.FriendBean;
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
        List<String> newlist = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).startsWith("back/img/")){
            }else {
                newlist.add(list.get(i));
            }
        }
        Flowable.just(newlist).observeOn(Schedulers.io())
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
    public void releaseDynamic(String id,String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent) {
        List<DynamicContentBean> list=new ArrayList<>();
        for(int i=0;i<listcontent.size();i++){
            if(listcontent.get(i).getContent().length()>0){
                list.add(listcontent.get(i));
            }
        }
        model.releaseDynamic(id,useraccountid,listimage,listfriends,isopen,list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.releaseDynamicSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.releaseDynamicFail(e);}}
                });
    }
    @Override
//    mlist
    public void savedraftsDynamic(String id, String useraccountid, List<DynamicContentBean> inputcontent, String inputstr, List<String> imglist, List<FriendBean> peoplelist, boolean isopen) {
        List<DynamicContentBean> list=new ArrayList<>();
        for(int i=0;i<inputcontent.size();i++){
            if(inputcontent.get(i).getContent().length()>0){
                list.add(inputcontent.get(i));
            }
        }
        model.savedraftsDynamic(id,useraccountid,inputcontent,inputstr,imglist,peoplelist,isopen)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.savedraftsDynamicSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.savedraftsDynamicFail(e);}}
                });
    }

    @Override
    public void getDynamic(String id) {
        model.getDynamic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<DraftsDynamic>() {
                    @Override
                    public void onNext(DraftsDynamic b) {if(view!=null){view.getDynamicSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.getDynamicFail(e);}}
                });
    }
}
