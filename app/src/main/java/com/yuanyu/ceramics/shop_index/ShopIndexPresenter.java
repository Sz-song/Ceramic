package com.yuanyu.ceramics.shop_index;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopIndexPresenter extends BasePresenter<ShopIndexConstract.IShopIndexView> implements ShopIndexConstract.IShopIndexPresenter {
    private ShopIndexConstract.IShopIndexModel model;

    public ShopIndexPresenter() {
        model=new ShopIndexModel();
    }
    @Override
    public void getShopIndexData(String shop_id, String useraccountid) {
        model.getShopIndexData(shop_id, useraccountid)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<ShopIndexBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShopIndexBean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                       if(view!=null){view.getShopIndexDataFail(e);}
                    }

                    @Override
                    public void onNext(ShopIndexBean bean) {
                        if(view!=null){view.getShopIndexDataSuccess(bean);}
                    }
                });
    }

    @Override
    public void focusShop(int type, String shop_id, String useraccountid) {
        model.focusShop(type,shop_id, useraccountid)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<ShopIndexBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.focusShopFail(e);}
                    }

                    @Override
                    public void onNext(Boolean bool) {
                        if(view!=null){view.focusShopSuccess(bool);}
                    }
                });
    }
    @Override
    public void saveScreenshot(Bitmap bitmap, int type){
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

    @Override
    public void shareShop(String shop_id, String useraccountid) {
        model.shareShop(shop_id,useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.shareShopSuccess();}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.shareShopFail(e);}
                    }
                });

    }
}
