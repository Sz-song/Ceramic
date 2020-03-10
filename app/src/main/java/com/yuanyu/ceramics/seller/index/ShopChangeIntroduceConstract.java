package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ShopChangeIntroduceConstract {
    interface IShopChangeIntroduceModel{
        Observable<BaseResponse<Boolean>> ShopChangeIntroduce(String shop_id, String introduce);
    }

    interface IShopChangeIntroduceView{
        void ShopChangeIntroduceSuccess(Boolean bool);
        void ShopChangeIntroduceFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopChangeIntroducePresenter{
        void ShopChangeIntroduce(String shop_id, String introduce);
    }
}
