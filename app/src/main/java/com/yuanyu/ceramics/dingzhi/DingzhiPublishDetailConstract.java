package com.yuanyu.ceramics.dingzhi;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

interface DingzhiPublishDetailConstract {
    interface IDingzhiPublishDetailModel {
        Observable<BaseResponse<String[]>> dingzhiPublish(int useraccountid, String master_id, String detail, String useage, String birthday, int priceType, String fenlei, String ticai);
    }
    interface IDingzhiPublishDetailView {
        void showToast(String msg);
        void dingzhiPublishSuccess();
        void dingzhiPublishFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IDingzhiPublishDetailPresenter {
        void initData(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> ticaiList);
        void dingzhiPublish(int useraccountid, String master_id, String detail, String useage, String birthday, int priceType, String fenlei, String ticai);
    }
}
