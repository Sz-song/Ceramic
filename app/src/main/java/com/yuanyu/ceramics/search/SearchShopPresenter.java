package com.yuanyu.ceramics.search;


import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchShopPresenter extends BasePresenter<SearchShopConstract.ISearchShopView> implements SearchShopConstract.ISearchShopPresenter {
    private SearchShopConstract.ISearchShopModel model;
    SearchShopPresenter() {model=new SearchShopModel();}

    @Override
    public void SearchShopList(String useraccountid, int page, String query) {
        model.SearchShopList(useraccountid,page,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<SearchShopBean>>())
                .subscribe(new BaseObserver<List<SearchShopBean>>() {
                    @Override
                    public void onNext(List<SearchShopBean> beans) {
                        if(view!=null){view.SearchShopListSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.SearchShopListFail(e);}
                    }
                });
    }
}
