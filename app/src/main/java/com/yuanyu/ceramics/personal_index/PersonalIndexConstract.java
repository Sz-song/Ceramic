package com.yuanyu.ceramics.personal_index;

import android.graphics.Bitmap;
import android.net.Uri;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface PersonalIndexConstract {
    interface IPersonalIndexModel {
        Observable<BaseResponse<PersonalIndexBean>> getPersonalIndexData(String useraccountid, String userid);
        Observable<BaseResponse<Boolean>> focus(String useraccountid, String userid);
        Observable<BaseResponse<Boolean>> addBlacklist(String useraccountid, String userid);
    }

    interface IPersonalIndexView {
        void getPersonalIndexDataSuccess(PersonalIndexBean bean);
        void getPersonalIndexDataFail(ExceptionHandler.ResponeThrowable e);
        void focusSuccess(Boolean b);
        void focusFail(ExceptionHandler.ResponeThrowable e);
        void addBlacklistSuccess(Boolean b);
        void addBlacklistFail(ExceptionHandler.ResponeThrowable e);
        void saveScreenshotSuccess(Uri uri, int type, String filePath);
        void saveScreenshotFail(int type);
    }
    interface IPersonalIndexPresenter {
        void getPersonalIndexData(String useraccountid, String userid);
        void focus(String useraccountid, String userid);
        void addBlacklist(String useraccountid, String userid);
        void saveScreenshot(Bitmap bitmap, int type);
    }
}
