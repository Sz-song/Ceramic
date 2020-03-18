package com.yuanyu.ceramics.mine;

import android.widget.TextView;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface MineConstract {
    interface IMineModel{
        Observable<BaseResponse<MineBean>> initData(String useraccountid);
    }
    interface IMineView{
        void initDataSuccess(MineBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMinePresenter{
        void initData(String useraccountid);
        void setCount(TextView view, int count);
    }

}
