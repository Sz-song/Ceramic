package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchMasterPresenter extends BasePresenter<SearchMasterConstract.ISearchMasterView> implements SearchMasterConstract.ISearchMasterPresenter {
    private SearchMasterConstract.ISearchMasterModel model;
    SearchMasterPresenter() {model=new SearchMasterModel();}
    @Override
    public void SearchMasterList(String useraccountid, int page, String query) {
        model.SearchMasterList(useraccountid,page,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<SearchMasterBean>>())
                .subscribe(new BaseObserver<List<SearchMasterBean>>() {
                    @Override
                    public void onNext(List<SearchMasterBean> beans) {if(view!=null){view.SearchMasterSuccess(beans);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.SearchMasterFail(e);}}
                });
    }

    @Override
    public void Focus(String useraccountid, String userid, int position) {
        model.Focus(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.focusSuccess(b,position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.focusFail(e);}}
                });
    }
}
