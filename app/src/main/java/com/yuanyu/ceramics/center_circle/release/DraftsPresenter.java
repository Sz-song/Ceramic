package com.yuanyu.ceramics.center_circle.release;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DraftsPresenter extends BasePresenter<DraftsConstract.IDraftsView> implements DraftsConstract.IDraftsPresenter {
    private DraftsConstract.IDraftsModel model;
    public DraftsPresenter(){
        model = new  DraftsModel();
    }
    @Override
    public void getDrafts(String useraccoundid, int page, int pagesize) {
        model.getDrafts(useraccoundid,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<DraftsBean>>())
                .subscribe(new BaseObserver<List<DraftsBean>>() {
                    @Override
                    public void onNext(List<DraftsBean> draftsBeans) {
                        if (view != null){
                            view.getDraftsSuccess(draftsBeans);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if (view != null){
                            view.getDraftsFail(e);
                        }
                    }
                });
    }
    @Override
    public void deletedrafts(String useraccountid, String id, int type,int position) {
        model.deletedrafts(useraccountid,id,type,position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){view.deleteSuccess(position);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.deleteFail(e);}}
                });
    }

}
