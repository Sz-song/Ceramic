package com.yuanyu.ceramics.utils;
import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson,Type type){
        this.gson = gson;
        this.type = type;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        L.e("response is "+response);
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        BaseResponse httpResult = gson.fromJson(response, BaseResponse.class);
        if (httpResult.getStatus()==100){
            //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
            return gson.fromJson(response,type);
        }else {
            //抛一个自定义ResultException 传入失败时候的状态码，和信息
            //Toast.makeText(MyApplication.getContext(),"异常信息是"+response,Toast.LENGTH_SHORT).show();
            throw new ExceptionHandler.ServerException(httpResult.getStatus(),httpResult.getMsg());
        }
    }
}
