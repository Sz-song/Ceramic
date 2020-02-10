package com.yuanyu.ceramics.utils;



import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpServiceInstance {
    private static HttpService instance = null;
    public static HttpService getInstance(){
        if (instance==null){
            synchronized (HttpService.class){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstant.BASE_URL)
                        .addConverterFactory(ResponseConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                instance = retrofit.create(HttpService.class);
            }
        }
        return instance;
    }


    //处理错误的变换
    public static class ErrorTransformer<T> implements ObservableTransformer {
        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
        }
    }


    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(throwable);
            //return Observable.error(new ExceptionHandler.ResponeThrowable(throwable,10));
        }
    }

    public static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) throws Exception {
            if (!response.isOk()) {
                // throw new RuntimeException(response.getStatus() + "" + response.getMsg() != null ? response.getMsg() : "");
                throw new ExceptionHandler.ServerException(response.getStatus(),response.getMsg());
            }
            return response.getData();
        }
    }

}

