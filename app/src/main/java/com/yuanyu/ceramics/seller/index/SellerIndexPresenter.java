package com.yuanyu.ceramics.seller.index;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class SellerIndexPresenter extends BasePresenter<SellerIndexConstract.IMineView> implements SellerIndexConstract.IMinePresenter {
    private SellerIndexConstract.IMineModel model;
    SellerIndexPresenter() {model=new SellerIndexModel();}
    @Override
    public void initData(String shopid) {
                model.initData(shopid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<SellerIndexBean>())
                .subscribe(new BaseObserver<SellerIndexBean>() {
                    @Override
                    public void onNext(SellerIndexBean bean) {
                        if(view!=null){view.initDataSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
    @Override
    public void compressImage(Context context, List<String> image, int type, String shop_id) {
        Flowable.just(image).observeOn(Schedulers.io())
                .map(list -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(context).load(list).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> uploadImage(files, type,shop_id));
    }

    @Override
    public void uploadImage(List<File> images, int type, String shop_id) {
        model.uploadImage(images).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<ArrayList<String>>())
                .subscribe(new BaseObserver<ArrayList<String>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){ view.replaceImageFail(e);}
                    }
                    @Override
                    public void onNext(ArrayList<String> list) {
                        if(view!=null){replaceImage(shop_id,type,list.get(0));}
                    }
                });
    }

    @Override
    public void replaceImage(String shopid, int type, String image) {
        model.replaceImage(shopid, type, image)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<String[]>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){ view.replaceImageSuccess(image,type);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.replaceImageFail(e); }}
                });
    }
    @Override
    public void setCount(TextView view, int count) {
        if (count <1) view.setVisibility(View.GONE);
        else if (count >=99){
            view.setVisibility(View.VISIBLE);
            view.setText("99");
        }
        else {
            view.setVisibility(View.VISIBLE);
            view.setText(count+"");
        }
    }
}
