package com.yuanyu.ceramics.personal_index.fans_focus;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface FocusAndFansConstract {
    interface IFocusAndFansModel {
        Observable<BaseResponse<List<FocusAndFansBean>>> getFocusAndFansList(String useraccountid, String userid,int focustype,int page);
        Observable<BaseResponse<Boolean>> focus(String useraccountid, String userid);

    }

    interface IFocusAndFansView {
        void getFocusAndFansListSuccess(List<FocusAndFansBean> beans);
        void getFocusAndFansListFail(ExceptionHandler.ResponeThrowable e);
        void focusSuccess(Boolean b,int position);
        void focusFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IFocusAndFansPresenter {
        void getFocusAndFansList(String useraccountid, String userid,int focustype,int page);
        void focus(String useraccountid, String userid,int position);

    }

}
