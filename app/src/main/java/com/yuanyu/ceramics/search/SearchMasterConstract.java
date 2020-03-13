package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface SearchMasterConstract {
    interface ISearchMasterModel {
        Observable<BaseResponse<SearchBean>> SearchMasterList(int page, String useraccountid, int type, String search, int outsidetype);
        Observable<BaseResponse<Boolean>> Focus(String useraccountid, String userid);
    }
    interface ISearchMasterView {
        void SearchMasterSuccess(SearchBean bean);
        void SearchMasterFail(ExceptionHandler.ResponeThrowable e);

        void focusSuccess(Boolean i, int position);
        void focusFail(ExceptionHandler.ResponeThrowable e);
    }

    interface ISearchMasterPresenter {
        void SearchMasterList(int page, String useraccountid, int type, String search, int outsidetype);
        void Focus(String useraccountid, String userid, int position);
    }
}
