package com.yuanyu.ceramics.item;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ItemDetailPresenter extends BasePresenter<ItemDetailConstract.IItemDetailView> implements ItemDetailConstract.IItemDetailPresenter {

    private ItemDetailConstract.IItemDetailModel model;

    ItemDetailPresenter() {model=new ItemDetailModel();}

    @Override
    public void getItemDetail(String useraccountid, String itemid) {
        List<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        ItemBean itemBean=new ItemBean("",888,888,"",list,list,list,true,"",1,"","","","","","","","","","","");
        StoreBean storeBean=new StoreBean(1,"","","","","","");
        ItemDetailBean itemDetailBean=new ItemDetailBean(itemBean,storeBean,"","","",",",true,"","","","",",",true,"",list);
        if(view!=null){view.getItemDetailSuccess(itemDetailBean);}
//        model.getItemDetail(useraccountid, itemid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<ItemDetailBean>())
//                .subscribe(new BaseObserver<ItemDetailBean>() {
//                    @Override
//                    public void onNext(ItemDetailBean itemDetailBean) {
//                        if(view!=null){view.getItemDetailSuccess(itemDetailBean);}
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if(view!=null){view.getItemDetailFail(e);}
//                    }
//                });
    }

    @Override
    public void collectItem(String useraccountid, String itemid, int shopid) {
        model.collectItem(useraccountid, itemid,  shopid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.collectItemSuccess(aBoolean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.collectItemFail(e);}
                    }
                });
    }

    @Override
    public void addCart(String useraccountid, String itemid) {
        model.addCart(useraccountid, itemid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){view.addCartSuccess();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.addCartFail(e);}}
                });
    }

    @Override
    public void loadMoreAds(Integer page) {
        model.loadMoreAds(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<AdsCellBean>>())
                .subscribe(new BaseObserver<List<AdsCellBean>>() {
                    @Override
                    public void onNext(List<AdsCellBean> beans) {if(view!=null){view.loadMoreAdsSuccess(beans);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.loadMoreAdsFail(e);}}
                });
    }

    @Override
    public void focus(String useraccountid, String userid) {
        model.focus(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.focusSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.focusFail(e);}}
                });
    }

    @Override
    public void saveScreenshot(Bitmap bitmap, int type) {
        if (bitmap != null) {
            try {// 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + System.currentTimeMillis()+".JPEG";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                Uri uri = Uri.fromFile(file);
                if(view!=null){view.saveScreenshotSuccess(uri,type,filePath);}
            } catch (Exception e) {
                L.e(e.getMessage());
                if(view!=null){view.saveScreenshotFail(type);}
            }
        }else{
            if(view!=null){view.saveScreenshotFail(type);}
        }
    }
}
