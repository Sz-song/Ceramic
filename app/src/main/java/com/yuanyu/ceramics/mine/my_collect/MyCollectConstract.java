package com.yuanyu.ceramics.mine.my_collect;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import java.util.List;
import io.reactivex.Observable;

public interface MyCollectConstract {
    interface IMyCollectModel{
        Observable<BaseResponse<List<MyCollectBean>>> getMyCollect(String useraccountid);
    }
    interface IMyCollectView{
        void getMyCollectSuccess(List<MyCollectBean> beans);
        void getMyCollectFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMyCollectPresenter{
        void getMyCollect(String useraccountid);
    }
}
