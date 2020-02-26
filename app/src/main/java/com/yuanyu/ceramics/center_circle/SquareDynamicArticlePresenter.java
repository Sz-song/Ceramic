package com.yuanyu.ceramics.center_circle;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.DynamicBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SquareDynamicArticlePresenter extends BasePresenter<SquareDynamicArticleConstract.IHotFragmentChildView> implements SquareDynamicArticleConstract.IHotFragmentChildPresenter{
    private SquareDynamicArticleConstract.IHotFragmentChildModel model;

    public SquareDynamicArticlePresenter() {
        model=new SquareDynamicArticleModel();
    }

    @Override
    public void getList(String useraccountid, int type, int page) {
        model.getList(useraccountid,type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<DynamicBean>>())
                .subscribe(new BaseObserver<List<DynamicBean>>() {
                    @Override
                    public void onNext(List<DynamicBean> beans) {
                        if(view!=null){view.getListSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getListFail(e);}
                    }
                });
    }

    @Override
    public void focus(String useraccountid, String userid) {
        model.focus(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.focusSuccess(b,userid);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.focusFail(e);}}
                });
    }

    @Override
    public void dianZan(String useraccountid, int type, String id, String source_uid, int position) {
        model.dianZan(useraccountid,type,id,source_uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if (view != null) { view.dianZanSuccess(position); }}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.dianZanFail(e);}}
                });
    }


    @Override
    public void shield(String useraccountid, int type, String id, int position) {
        model.shield(useraccountid,type,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if (view != null) { view.shieldSuccess(position);}}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.shieldFail(e);}}
                });
    }

    @Override
    public void blackList(String useraccountid, String userid) {
        model.blackList(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.blackListSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.blackListFail(e);}}
                });
    }
    @Override
    public void deleteDynamic(String useraccountid, String dynamic_id, int position) {
        model.deleteDynamic(useraccountid,dynamic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){ view.deleteSuccess(position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){ view.deleteFail(e);}}
                });
    }

    @Override
    public void deleteArticle(String useraccountid, String article_id, int position) {
        model.deleteArticle(useraccountid,article_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){ view.deleteSuccess(position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){ view.deleteFail(e);}}
                });
    }
}
