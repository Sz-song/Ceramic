package com.yuanyu.ceramics.dingzhi;



import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface DingzhiDetailUserConstact {

    interface IDingzhiDetailUserModel {
        Observable<BaseResponse<DingzhiDetailBean>> dingzhiDetail(String dingzhi_id, String useraccountid);
        Observable<BaseResponse<GenerateOrdersBean>> generateBondOrder(String dingzhi_id, String useraccountid, int type, int paytype, AddressManageBean bean);
        Observable<BaseResponse<Boolean>> BondPay(String id, int type, String out_trade_no, String trade_no);
        Observable<BaseResponse<Boolean>> confirm_receipt(String dingzhi_id, String useraccountid);
    }
    interface IDingzhiDetailUserView {
        void dingzhiDetailSuccess(DingzhiDetailBean detailBean);
        void dingzhiDetailFail(ExceptionHandler.ResponeThrowable e);

        void generateBondOrderSuccess(GenerateOrdersBean s, int paytype);
        void generateBondOrderFail(ExceptionHandler.ResponeThrowable e);

        void BondPaySuccess(Boolean b);
        void BondPayFail(ExceptionHandler.ResponeThrowable e);

        void confirmReceiptSuccess(Boolean aboolean);
        void confirmReceiptFail(ExceptionHandler.ResponeThrowable e);

    }

    interface IDingzhiDetailUserPresenter {
        void dingzhiDetail(String dingzhi_id, String useraccountid);
        void generateBondOrder(String dingzhi_id, String useraccountid, int type, int paytype, AddressManageBean bean);
        void BondPay(String id, int type, String out_trade_no, String trade_no);
        void confirmReceipt(String dingzhi_id, String useraccountid);
    }
}
