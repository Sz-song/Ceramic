package com.yuanyu.ceramics.seller.shop_shelve.shelve_audit;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ShelveAuditConstract {
    interface IShelveAuditModel {
        Observable<BaseResponse<List<ShelveAuditBean>>> getShelveAuditData(String shopid, int type, int page);
        Observable<BaseResponse<String[]>> deleteShelveAudit(String shopid, String id);
    }

    interface IShelveAuditView {
        void getShelveAuditDataSuccess(List<ShelveAuditBean> list);
        void deleteShelveAuditSuccess(int position);
        void getShelveAuditDataFail(ExceptionHandler.ResponeThrowable e);
        void deleteShelveAuditFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShelveAuditPresenter {
        void getShelveAuditData(String shopid, int type, int page);
        void deleteShelveAudit(String shopid, String id, int position);
    }
}
