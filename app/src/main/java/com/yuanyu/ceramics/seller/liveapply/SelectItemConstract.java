package com.yuanyu.ceramics.seller.liveapply;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.seller.liveapply.bean.SelectItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface SelectItemConstract {
    interface ISelectItemModel {
        Observable<BaseResponse<SelectItemBean>> getSelectItem(String shop_id, List<String> selected_list, int page);
    }

    interface ISelectItemView {
        void getSelectItemSuccess(SelectItemBean bean);
        void getSelectItemFail(ExceptionHandler.ResponeThrowable e);
    }

    interface ISelectItemPresenter {
        void getSelectItem(String shop_id, List<ItemBean> selected_list, int page);
    }
}
