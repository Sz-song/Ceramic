package com.yuanyu.ceramics.item;

import android.graphics.Bitmap;
import android.net.Uri;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ItemDetailConstract {
    interface IItemDetailModel {
        Observable<BaseResponse<ItemDetailBean>> getItemDetail(String useraccountid, String itemid);
        Observable<BaseResponse<Boolean>>collectItem(String useraccountid, String itemid, int shopid);
        Observable<BaseResponse<String[]>> addCart(String useraccountid, String itemid);
        Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(Integer page);
        Observable<BaseResponse<Boolean>> focus(String useraccountid, String userid);
    }
    interface IItemDetailView {
        void getItemDetailSuccess(ItemDetailBean bean);
        void getItemDetailFail(ExceptionHandler.ResponeThrowable e);

        void collectItemSuccess(Boolean b);
        void collectItemFail(ExceptionHandler.ResponeThrowable e);

        void addCartSuccess();
        void addCartFail(ExceptionHandler.ResponeThrowable e);

        void loadMoreAdsSuccess(List<AdsCellBean> adsCellBeanList);
        void loadMoreAdsFail(ExceptionHandler.ResponeThrowable e);

        void focusSuccess(Boolean b);
        void focusFail(ExceptionHandler.ResponeThrowable e);

        void saveScreenshotSuccess(Uri uri, int type, String filePath);
        void saveScreenshotFail(int type);
    }

    interface IItemDetailPresenter {
        void getItemDetail(String useraccountid, String itemid);
        void collectItem(String useraccountid, String itemid,int shopid);
        void addCart(String useraccountid, String itemid);
        void loadMoreAds(Integer page);
        void focus(String useraccountid, String userid);
        void saveScreenshot(Bitmap bitmap, int type);
    }
}
