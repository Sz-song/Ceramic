package com.yuanyu.ceramics.mine.applyenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ApplyEnterPresenter extends BasePresenter<ApplyEnterConstract.IApplyEnterView> implements ApplyEnterConstract.IApplyEnterPresenter {
    private ApplyEnterConstract.IApplyEnterModel model;

    ApplyEnterPresenter() {model=new ApplyEnterModel();}

    @Override
    public String getCity(String address) {
        String city;
        String[] citys = address.split(" ");
        if (citys[0].trim().equals("上海市")) {
            city = "上海市";
        } else if (citys[0].trim().equals("北京市")) {
            city = "北京市";
        } else if (citys[0].trim().equals("重庆市")) {
            city = "重庆市";
        } else if (citys[0].trim().equals("天津市")) {
            city = "天津市";
        } else {
            city = citys[1];
        }
        return city;
    }


    @SuppressLint("CheckResult")
    @Override
    public void compressImages(Context context, String name, String telephone, String idcard_num, String shop_name, String location, String address, String city, String image_positive, String image_reverse, String image_shop) {
        if(name.trim().length()==0){
            if(view!=null){view.showToast("请填写真实姓名");}
            return;
        }
        if(telephone.trim().length()==0){
            if(view!=null){view.showToast("请填写电话号码");}
            return;
        }
        if(idcard_num.trim().length()!=18){
            if(view!=null){view.showToast("请填写18位身份证号码");}
            return;
        }
        if(shop_name.trim().length()==0){
            if(view!=null){view.showToast("请填写店铺名称");}
            return;
        }
        if(location.trim().length()==0){
            if(view!=null){view.showToast("请选择所在地");}
            return;
        }
        if(address.trim().length()==0){
            if(view!=null){view.showToast("请填写详细地址");}
            return;
        }
        if(image_positive.trim().length()==0){
            if(view!=null){view.showToast("请上传身份证头像页照片");}
            return;
        }
        if(image_reverse.trim().length()==0){
            if(view!=null){view.showToast("请上传身份证国徽页照片");}
            return;
        }
        if(image_shop.trim().length()==0){
            if(view!=null){view.showToast("请上传营业执照照片");}
            return;
        }
        List<String> list=new ArrayList<>();
        list.add(image_positive);
        list.add(image_reverse);
        list.add(image_shop);
        Flowable.just(list)
                .observeOn(Schedulers.io())
                .map(list1 -> Luban.with(context).load(list1).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::uploadImage);
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
    public void ApplyEnter(String useraccountid, String name, String telephone, String idcard_num, String shop_name, String address, String city, List<String> image) {
        model.ApplyEnter(useraccountid,name,telephone,idcard_num,shop_name,address,city,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){view.ApplyEnterSuccess();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.ApplyEnterFail(e);}}
                });
    }
}
