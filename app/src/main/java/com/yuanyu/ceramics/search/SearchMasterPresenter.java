package com.yuanyu.ceramics.search;

import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchMasterPresenter extends BasePresenter<SearchMasterConstract.ISearchMasterView> implements SearchMasterConstract.ISearchMasterPresenter {
    private SearchMasterConstract.ISearchMasterModel model;
    public SearchMasterPresenter() {model=new SearchMasterModel();}

    @Override
    public void SearchMasterList(int page, String useraccountid, int type, String search, int outsidetype) {
        model.SearchMasterList(page,useraccountid,type,search,outsidetype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<SearchBean>())
                .subscribe(new BaseObserver<SearchBean>() {
                    @Override
                    public void onNext(SearchBean bean) {if(view!=null){view.SearchMasterSuccess(bean);}}
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
