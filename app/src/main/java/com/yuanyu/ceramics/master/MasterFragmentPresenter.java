package com.yuanyu.ceramics.master;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MasterFragmentPresenter extends BasePresenter<MasterFragmentConstract.IMasterFragmentView> implements MasterFragmentConstract.IMasterFragmentPresenter {
    private MasterFragmentConstract.IMasterFragmentModel model;
    public MasterFragmentPresenter() {model=new MasterFragmentModel();}
    @Override
    public void initData(String useraccountid) {
        model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MasterItemBean>>())
                .subscribe(new BaseObserver<List<MasterItemBean>>() {
                    @Override
                    public void onNext(List<MasterItemBean> masterItemBeans) {
                        if(view!=null){view.initDataSuccess(masterItemBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
