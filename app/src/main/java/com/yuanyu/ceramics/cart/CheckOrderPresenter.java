package com.yuanyu.ceramics.cart;
import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckOrderPresenter extends BasePresenter<CheckOrderConstract.ICheckOrderView> implements CheckOrderConstract.ICheckOrderPresenter {
    private CheckOrderConstract.ICheckOrderModel model;

    CheckOrderPresenter() {model=new CheckOrderModel();}

    @Override
    public void initList(List<GoodsBean> payList, List<SumOrderBean> list, String order_num) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < payList.size(); i++) {
            if (!integerList.contains(payList.get(i).getShopid())) {
                integerList.add(payList.get(i).getShopid());
            }
        }
        list.clear();
        for (int i = 0; i < integerList.size(); i++) {//生成list
            List<ItemCellbean> item_list = new ArrayList<>();
            String comment = "";
            for (int j = 0; j < payList.size(); j++) {//生成item_list
                if (payList.get(j).getShopid() == integerList.get(i)) {
                    ItemCellbean itemCellbean = new ItemCellbean(payList.get(j).getId(), 1, order_num,payList.get(j).getCoupons_id());
                    item_list.add(itemCellbean);
                    if (null!=payList.get(j).getComment()&&!payList.get(j).getComment().equals("")) {
                        comment = payList.get(j).getComment();
                    }
                }
            }
            SumOrderBean sumOrderBean = new SumOrderBean(item_list, comment, integerList.get(i));
            list.add(sumOrderBean);
        }
    }


    @Override
    public void getAddressData(String useraccountid) {
        model.getAddressData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<AddressManageBean>>())
                .subscribe(new BaseObserver<List<AddressManageBean>>() {
                    @Override
                    public void onNext(List<AddressManageBean> addressBeans) {
                        if(view!=null){view.getAddressDataSuccess(addressBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getAddressDataFail(e);}
                    }
                });
    }

    @Override
    public void loadMoreAds(int page) {
        model.loadMoreAds(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<AdsCellBean>>())
                .subscribe(new BaseObserver<List<AdsCellBean>>() {
                    @Override
                    public void onNext(List<AdsCellBean> beans) {
                        if(view!=null){view.loadMoreAdsSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.loadMoreAdsFail(e);}
                    }
                });
    }

    @Override
    public void generateOrders(String useraccountid, int paytype, int type, int tag, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list) {
        model.generateOrders(useraccountid,paytype,type,tag,name,address,province,city,area,tel,list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<GenerateOrdersBean>())
                .subscribe(new BaseObserver<GenerateOrdersBean>() {
                    @Override
                    public void onNext(GenerateOrdersBean bean) {
                        if(view!=null){view.generateOrdersSuccess(bean,paytype);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.generateOrdersFail(e);}
                    }
                });
    }

    @Override
    public void initOrdernum(List<String> stringList, List<SumOrderBean> list) {
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).getItem_list().size();j++){
                if(list.get(i).getItem_list().get(j).getOrdernum().trim().length()==0&&stringList.size()>0){
                    list.get(i).getItem_list().get(j).setOrdernum(stringList.get(0));
                    stringList.remove(0);
                }
            }
        }
    }

    @Override
    public List<String> getOrderList(List<SumOrderBean> list) {
        List<String> stringList=new ArrayList<>();
        for(int j=0;j<list.size();j++){
            for(int n=0;n<list.get(j).getItem_list().size();n++){
                if(list.get(j).getItem_list().get(n).getOrdernum().trim().length()>0){
                    stringList.add(list.get(j).getItem_list().get(n).getOrdernum());
                }
            }
        }
        return stringList;
    }
}
