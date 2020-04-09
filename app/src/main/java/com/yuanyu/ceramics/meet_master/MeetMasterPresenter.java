package com.yuanyu.ceramics.meet_master;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MeetMasterPresenter extends BasePresenter<MeetMasterConstract.IMeetMasterView> implements MeetMasterConstract.IMeetMasterPresenter {
    private MeetMasterConstract.IMeetMasterModel model;

    MeetMasterPresenter() { model=new MeetMasterModel(); }

    @Override
    public void initData(String useraccountid, int page) {
        model.initData(useraccountid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MeetMasterBean>>())
                .subscribe(new BaseObserver<List<MeetMasterBean>>() {
                    @Override
                    public void onNext(List<MeetMasterBean> meetMasterBeans) {
                        if(view!=null){view.initDataSuccess(meetMasterBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                       if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
