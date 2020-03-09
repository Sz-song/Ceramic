package com.yuanyu.ceramics.seller.liveapply;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class LiveApplyPresenter extends BasePresenter<LiveApplyConstract.ILiveApplyView> implements LiveApplyConstract.ILiveApplyPresenter {
    private LiveApplyConstract.ILiveApplyModel model;

    public LiveApplyPresenter() {model=new LiveApplyModel();}

    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list)
                .observeOn(Schedulers.io())
                .map(list1 -> Luban.with(context).load(list1).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    if(view!=null){uploadImage(files);}
                });
    }
    @Override
    public void uploadImage(List<File> images) {
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
    public void getLiveApplyState(String shop_id) {
        List<ItemBean> list=new ArrayList<>();
        LiveApplyStatusBean bean=new LiveApplyStatusBean("1","","标题1","2020-03-10 09:30","1",list,3,"1","1");
         if(view!=null){view.getLiveApplyStateSuccess(bean);}
//        model.getLiveApplyState(shop_id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<LiveApplyStatusBean>())
//                .subscribe(new BaseObserver<LiveApplyStatusBean>() {
//                    @Override
//                    public void onNext(LiveApplyStatusBean bean) {if(view!=null){view.getLiveApplyStateSuccess(bean);}}
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.getLiveApplyStateFail(e);}}
//                });
    }

    @Override
    public void liveApply(String id, int uid, int shop_id, String title, String coverimg, String time, int type, List<ItemBean> item_list) {
        if(title==null||title.trim().length()==0){
            if(view!=null){view.showToast("请填写直播标题");}
            return;
        }else if(coverimg==null||coverimg.trim().length()==0){
            if(view!=null){view.showToast("请上传直播封面");}
            return;
        }else if(time==null||time.trim().length()==0){
            if(view!=null){view.showToast("请选择开播时间");}
            return;
        }else if(type<2||type>4){
            if(view!=null){view.showToast("请选择直播类型");}
            return;
        }
        List<String> stringList=new ArrayList<>();
        for(int i=0;i<item_list.size();i++){
            stringList.add(item_list.get(i).getId());
        }
        LiveApplyBean bean = new LiveApplyBean(id,uid,shop_id,title,coverimg,time,type,stringList);
        model.liveApply(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] s) {if(view!=null){view.liveApplySuccess();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.liveApplyFail(e);}}
                });
    }

}
