package com.yuanyu.ceramics.mine.systemsetting;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface BlackListConstract {

    interface IBlackListModel{
        Observable<BaseResponse<List<BlackListBean>>> getBlacklist(String useraccountid);
        Observable<BaseResponse<String[]>>removeBlacklist(String useraccountid,String blackUser);
        Observable<BaseResponse<Boolean>> addBlacklist(String useraccountid, String blackUser);
    }

    interface IBlackListView{
        void getBlacklistSuccess(List<BlackListBean> beans);
        void removeBlacklistSuccess(int position);
        void showToast(String msg);
    }

    interface IBlackListPresenter{
       void getBlacklist(String useraccountid);
       void removeBlacklist(String useraccountid,String blackUser,int position);
       void addBlacklist(String useraccountid, String blackUser);
    }
}
