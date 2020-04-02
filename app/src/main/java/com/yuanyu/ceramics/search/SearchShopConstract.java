package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface SearchShopConstract {
    interface ISearchShopModel {
        Observable<BaseResponse<List<SearchShopBean>>> SearchShopList(String useraccountid, int page, String query);
    }

    interface ISearchShopView {
        void SearchShopListSuccess(List<SearchShopBean> beans);
        void SearchShopListFail(ExceptionHandler.ResponeThrowable e);
        void Search(String query);
    }

    interface ISearchShopPresenter {
        void SearchShopList(String useraccountid, int page, String query);
    }
}
