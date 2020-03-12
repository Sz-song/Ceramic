package com.yuanyu.ceramics.seller.liveapply;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.seller.liveapply.bean.SelectItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectItemPresenter extends BasePresenter<SelectItemConstract.ISelectItemView> implements SelectItemConstract.ISelectItemPresenter {
    private SelectItemConstract.ISelectItemModel model;
    public SelectItemPresenter() {
        model=new SelectItemModel();
    }
    @Override
    public void getSelectItem(String shop_id, List<ItemBean> selected_list, int page) {
        List<String> list = new ArrayList<>();
        for (ItemBean bean : selected_list) {
            list.add(bean.getId() + "");
        }
        model.getSelectItem(shop_id,list,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<SelectItemBean>())
                .subscribe(new BaseObserver<SelectItemBean>() {
                    @Override
                    public void onNext(SelectItemBean bean) {
                        if(view!=null){view.getSelectItemSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getSelectItemFail(e);}
                    }
                });
    }
}
