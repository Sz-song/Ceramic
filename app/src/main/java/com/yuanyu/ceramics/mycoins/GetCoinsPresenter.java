package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetCoinsPresenter extends BasePresenter<GetCoinsConstract.IGetCoinsView> implements GetCoinsConstract.IGetCoinsPresenter {
    private GetCoinsConstract.IGetCoinsModel model;
    GetCoinsPresenter() {model=new GetCoinsModel(); }
    @Override
    public void getMyCoinsTask(String useraccountid, String shop_id) {
        model.getGetCoinsTask(useraccountid,shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<GetCoinsBean>())
                .subscribe(new BaseObserver<GetCoinsBean>() {
                    @Override
                    public void onNext(GetCoinsBean bean) {
                        if(view!=null){view.getMyCoinsTaskSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if (view!=null){view.getMyCoinsTaskFail(e);}
                    }
                });
    }
}
