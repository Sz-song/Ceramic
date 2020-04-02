package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface SearchItemConstract {
    interface ISearchItemModel {
        Observable<BaseResponse<List<SearchItemBean>>> SearchItemList(String useraccountid, int page, String query);
    }

    interface ISearchItemView {
        void SearchItemListSuccess(List<SearchItemBean> beans);
        void SearchItemListFail(ExceptionHandler.ResponeThrowable e);
        void Search(String query);
    }

    interface ISearchItemPresenter {
        void SearchItemList(String useraccountid, int page, String query);
    }
}
