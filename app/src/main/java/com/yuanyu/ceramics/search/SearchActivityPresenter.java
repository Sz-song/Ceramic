package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivityPresenter extends BasePresenter<SearchActivityConstract.ISearchView> implements SearchActivityConstract.ISearchPresenter {
    private SearchActivityConstract.ISearchModel model;
    SearchActivityPresenter() {model=new SearchActivityModel();}
    @Override
    public void getSearchHotList(String useraccountid, List<String> historyList) {
        model.getSearchHotList(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        if(view!=null){view.getSearchHotListSuccess(strings);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getSearchHotListFail(e);}
                    }
                });
    }
}
