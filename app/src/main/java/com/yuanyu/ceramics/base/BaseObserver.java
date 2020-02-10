package com.yuanyu.ceramics.base;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    public BaseObserver() {}
    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandler.ServerException){
            onError(ExceptionHandler.handleException(e));
        }
        else if(e instanceof ExceptionHandler.ResponeThrowable){
            onError((ExceptionHandler.ResponeThrowable)e);
        }
        else {
            onError(ExceptionHandler.handleException(e));
        }
    }

    @Override
    public void onSubscribe(Disposable d) { }

    @Override
    public void onComplete() {
        //加载完毕
    }
    public abstract void onError(ExceptionHandler.ResponeThrowable e);

}
