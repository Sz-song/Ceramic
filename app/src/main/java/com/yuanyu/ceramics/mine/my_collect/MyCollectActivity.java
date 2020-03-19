package com.yuanyu.ceramics.mine.my_collect;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

public class MyCollectActivity extends BaseActivity<MyCollectPresenter> implements MyCollectConstract.IMyCollectView {
    @Override
    protected int getLayout() {
        return R.layout.common_layout;
    }

    @Override
    protected MyCollectPresenter initPresent() {
        return new MyCollectPresenter();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void getMyCollectSuccess(List<MyCollectBean> beans) {

    }

    @Override
    public void getMyCollectFail(ExceptionHandler.ResponeThrowable e) {

    }
}
