package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;
public interface SearchActivityConstract {
    interface ISearchModel {
        Observable<BaseResponse<List<String>>> getSearchHotList(String useraccountid);
    }
    interface ISearchView {
        void getSearchHotListSuccess(List<String> strings);
        void getSearchHotListFail(ExceptionHandler.ResponeThrowable e);

    }
    interface ISearchPresenter {
        void getSearchHotList(String useraccountid, List<String> historyList);
    }
}
