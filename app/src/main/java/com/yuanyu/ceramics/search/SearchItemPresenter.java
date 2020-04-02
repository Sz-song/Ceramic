package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchItemPresenter extends BasePresenter<SearchItemConstract.ISearchItemView> implements SearchItemConstract.ISearchItemPresenter {
    private SearchItemConstract.ISearchItemModel model;

    SearchItemPresenter() {model=new SearchItemModel();}
    @Override
    public void SearchItemList(String useraccountid, int page, String query) {
        model.SearchItemList(useraccountid,page,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<SearchItemBean>>())
                .subscribe(new BaseObserver<List<SearchItemBean>>() {
                    @Override
                    public void onNext(List<SearchItemBean> beans) {
                        if(view!=null){view.SearchItemListSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.SearchItemListFail(e);}
                    }
                });
    }
}
