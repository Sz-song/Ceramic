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
        List<ItemBean> list2=new ArrayList<>();
        ItemBean ib1=new ItemBean("1","img/banner1.jpg","元代青花山水瓶仿品","18000",true);
        ItemBean ib2=new ItemBean("2","img/banner1.jpg","元代青花山水瓶仿品","19000",false);
        list2.add(ib1);
        list2.add(ib2);
        SelectItemBean bean=new SelectItemBean("img/banner1.jpg","店铺工作室","2",list2);
        if(view!=null){view.getSelectItemSuccess(bean);}
//        model.getSelectItem(shop_id,list,page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<SelectItemBean>())
//                .subscribe(new BaseObserver<SelectItemBean>() {
//                    @Override
//                    public void onNext(SelectItemBean bean) {
//                        if(view!=null){view.getSelectItemSuccess(bean);}
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if(view!=null){view.getSelectItemFail(e);}
//                    }
//                });
    }
}
