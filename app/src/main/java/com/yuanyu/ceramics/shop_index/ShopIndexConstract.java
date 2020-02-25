package com.yuanyu.ceramics.shop_index;

import android.graphics.Bitmap;
import android.net.Uri;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ShopIndexConstract {
    interface IShopIndexModel {
        Observable<BaseResponse<ShopIndexBean>>getShopIndexData(String shop_id, String useraccountid);
        Observable<BaseResponse<Boolean>>focusShop(int type, String shop_id, String useraccountid);
        Observable<BaseResponse<Boolean>>shareShop(String shop_id, String useraccountid);
    }

    interface IShopIndexView {
        void getShopIndexDataSuccess(ShopIndexBean bean);
        void getShopIndexDataFail(ExceptionHandler.ResponeThrowable e);
        void focusShopSuccess(Boolean b);
        void focusShopFail(ExceptionHandler.ResponeThrowable e);
        void saveScreenshotSuccess(Uri uri, int type, String filePath);
        void saveScreenshotFail(int type);
        void shareShopSuccess();
        void shareShopFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopIndexPresenter {
        void getShopIndexData(String shop_id, String useraccountid);
        void focusShop(int type, String shop_id, String useraccountid);
        void saveScreenshot(Bitmap bitmap, int type);
        void shareShop(String shop_id, String useraccountid);
    }
}
