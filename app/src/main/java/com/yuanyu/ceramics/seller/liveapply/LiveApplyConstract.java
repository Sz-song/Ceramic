package com.yuanyu.ceramics.seller.liveapply;

import android.content.Context;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface LiveApplyConstract {

    interface ILiveApplyModel {
        Observable<BaseResponse<List<String>>> uploadImage(List<File> images);
        Observable<BaseResponse<LiveApplyStatusBean>> getLiveApplyState(String shop_id);
        Observable<BaseResponse<String[]>> liveApply(String id,String uid,String shop_id,String title,String cover,String time,List<String> item_list );
    }

    interface ILiveApplyView {
        void uploadImageSuccess(List<String> list);
        void uploadImageFail(ExceptionHandler.ResponeThrowable e);
        void getLiveApplyStateSuccess(LiveApplyStatusBean bean);
        void getLiveApplyStateFail(ExceptionHandler.ResponeThrowable e);
        void liveApplySuccess();
        void liveApplyFail(ExceptionHandler.ResponeThrowable e);
        void showToast(String str);
    }

    interface ILiveApplyPresenter {
        void compressImages(Context context, List<String> list);
        void uploadImage(List<File> images);
        void getLiveApplyState(String shop_id);
        void liveApply(String id, String uid, String shop_id, String title, String coverimg, String time, List<ItemBean> item_list);
    }
}
