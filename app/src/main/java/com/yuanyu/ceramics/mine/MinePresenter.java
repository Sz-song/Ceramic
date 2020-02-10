package com.yuanyu.ceramics.mine;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends BasePresenter<MineConstract.IMineView> implements MineConstract.IMinePresenter {
    private MineConstract.IMineModel model;
    MinePresenter() {model=new MineModel();}

    @Override
    public void initData(String useraccountid) {
        model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<MineBean>())
                .subscribe(new BaseObserver<MineBean>() {
                    @Override
                    public void onNext(MineBean bean) {
                        if(view!=null){view.initDataSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
