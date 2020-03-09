package com.yuanyu.ceramics.base;


public abstract class BasePresenter<V> {

    protected V view;   //View 接口类型的弱引用

    public void attachView(V view) {
       this.view = view;
    }

    public void detachView() {
        if (view != null) {
            view = null;
        }
    }

}
