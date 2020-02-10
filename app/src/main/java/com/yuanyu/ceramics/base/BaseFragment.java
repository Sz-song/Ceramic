package com.yuanyu.ceramics.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter>extends Fragment {
    protected   P presenter;
    Unbinder unbinder;
    private boolean isFirstLoad = false;
    private View rootView;
    protected boolean isAlive;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        isAlive = true;
        if (null == rootView){
            rootView = initView(inflater, container);//让子类实现初始化视图
            unbinder = ButterKnife.bind(this,rootView);
            presenter=initPresent();
            presenter.attachView(this);
            initEvent(rootView);//初始化事件
            isFirstLoad = true;//视图创建完成，将变量置为true
            if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
                initData();
                isFirstLoad = false;
            }
        }else {
            /**
             * 缓存的rootView需要判断是否已经被加过parent，
             * 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
             */
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            initData();
            isFirstLoad = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;
        isAlive = false;
        presenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //初始化视图接口，子类必须实现
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract P initPresent();
    //初始化事件接口，留给子类实现
    protected abstract void initEvent(View view);

    //数据加载接口，留给子类实现
    protected abstract void initData();
}
