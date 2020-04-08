package com.yuanyu.ceramics.dingzhi;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

interface DingzhiPublishDetailConstract {
    interface IDingzhiPublishDetailModel {
        Observable<BaseResponse<String[]>> dingzhiPublish(String useraccountid, String master_id, String detail, String useage,  int priceType, String fenlei, String zhonglei,String waiguan);
    }
    interface IDingzhiPublishDetailView {
        void showToast(String msg);
        void dingzhiPublishSuccess();
        void dingzhiPublishFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IDingzhiPublishDetailPresenter {
        void initData(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> zhongleiList,List<FenleiTypeBean> waiguanList);
        void dingzhiPublish(String useraccountid, String master_id, String detail, String useage,  int priceType, String fenlei, String zhonglei,String waiguan);
    }
}
