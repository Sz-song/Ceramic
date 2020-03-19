package com.yuanyu.ceramics.mine.my_collect;

import com.yuanyu.ceramics.base.BasePresenter;

public class MyCollectPresenter extends BasePresenter<MyCollectConstract.IMyCollectView> implements MyCollectConstract.IMyCollectPresenter {
    private MyCollectConstract.IMyCollectModel model;
    MyCollectPresenter() { model=new MyCollectModel();}

    @Override
    public void getMyCollect(String useraccountid) {

    }
}
