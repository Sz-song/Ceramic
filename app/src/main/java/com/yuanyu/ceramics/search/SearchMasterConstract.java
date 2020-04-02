package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface SearchMasterConstract {
    interface ISearchMasterModel {
        Observable<BaseResponse<List<SearchMasterBean>>> SearchMasterList(String useraccountid, int page, String query);
        Observable<BaseResponse<Boolean>> Focus(String useraccountid, String userid);
    }
    interface ISearchMasterView {
        void SearchMasterSuccess(List<SearchMasterBean> beans);
        void SearchMasterFail(ExceptionHandler.ResponeThrowable e);

        void focusSuccess(Boolean i, int position);
        void focusFail(ExceptionHandler.ResponeThrowable e);

        void Search(String query);
    }

    interface ISearchMasterPresenter {
        void SearchMasterList(String useraccountid, int page, String query);
        void Focus(String useraccountid, String userid, int position);
    }
}
