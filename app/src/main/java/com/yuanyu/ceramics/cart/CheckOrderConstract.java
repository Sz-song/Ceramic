package com.yuanyu.ceramics.cart;

import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface CheckOrderConstract {
    interface ICheckOrderModel{
        Observable<BaseResponse<List<AddressManageBean>>> getAddressData(String useraccountid);
        Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(int page);
        Observable<BaseResponse<GenerateOrdersBean>> generateOrders(String useraccountid, int paytype, int type, int tag, String name, String address, String province,
                                                                                             String city, String area, String tel, List<SumOrderBean> list);
    }
    interface ICheckOrderView{

        void getAddressDataSuccess(List<AddressManageBean> addressBeans);
        void getAddressDataFail(ExceptionHandler.ResponeThrowable e);

        void loadMoreAdsSuccess(List<AdsCellBean> adsCellBeans);
        void loadMoreAdsFail(ExceptionHandler.ResponeThrowable e);

        void generateOrdersSuccess(GenerateOrdersBean generateOrdersBean, int paytype);
        void generateOrdersFail(ExceptionHandler.ResponeThrowable e);

    }
    interface ICheckOrderPresenter{
        void initList(List<GoodsBean> payList, List<SumOrderBean> list, String order_num);
        void getAddressData(String useraccountid);
        void loadMoreAds(int page);
        void generateOrders(String useraccountid, int paytype, int type, int tag,String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list);
       void initOrdernum(List<String> stringList, List<SumOrderBean> list);
        List<String> getOrderList(List<SumOrderBean> list);
    }
}
