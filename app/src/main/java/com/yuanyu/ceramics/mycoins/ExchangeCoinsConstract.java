package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ExchangeCoinsConstract {
    interface IExchangeCoinsModel {
        Observable<BaseResponse<List<ExchangeCoinsBean>>> getMyCoinsExchange(String useraccountid);
        Observable<BaseResponse<Boolean>> ExchangeCoins(String id, String useraccountid, String alipay_num, String alipay_name);
    }
    interface IExchangeCoinsView {
        void getMyCoinsExchangeSuccess(List<ExchangeCoinsBean> beans);
        void getMyCoinsExchangeFail(ExceptionHandler.ResponeThrowable e);

        void ExchangeCoinsSuccess();
        void ExchangeCoinsFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IExchangeCoinsPresenter {
        void getMyCoinsExchange(String useraccountid);
        void ExchangeCoins(String id, String useraccountid, String alipay_num, String alipay_name);
    }
}
