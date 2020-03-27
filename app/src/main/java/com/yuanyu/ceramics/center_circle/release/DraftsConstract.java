package com.yuanyu.ceramics.center_circle.release;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface DraftsConstract {
//    要调的接口方法
    interface  IDraftsModel {
//        获取草稿列表
        Observable<BaseResponse<List<DraftsBean>>> getDrafts(String useraccoundid, int page, int pagesize);
    }
//    调完接口要执行的方法
    interface IDraftsView{
        void getDraftsSuccess(List<DraftsBean> list);
        void getDraftsFail(ExceptionHandler.ResponeThrowable e);
}
    //要用的方法
    interface IDraftsPresenter{
        void getDrafts(String useraccoundid, int page, int pagesize);
}
}
