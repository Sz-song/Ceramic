package com.yuanyu.ceramics.address_manage;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressManagePresenter extends BasePresenter<AddressManageConstract.IAddressManageView> implements AddressManageConstract.IAddressManagePresenter {
    private AddressManageConstract.IAddressManageModel model;
    AddressManagePresenter() {model=new AddressManageModel();}
    @Override
    public void getAddressData(String useraccountid) {
        model.getAddressData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<AddressManageBean>>())
                .subscribe(new BaseObserver<List<AddressManageBean>>() {
                    @Override
                    public void onNext(List<AddressManageBean> addressManageBeans) {
                        if(view!=null){view.getAddressDataSuccess(addressManageBeans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getAddressDataFail(e);}
                    }
                });
    }

    @Override
    public void deleteAddress(String useraccountid, String id, int position) {
        model.deleteAddress(useraccountid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver<String []>() {
                    @Override
                    public void onNext(String[] strings) { view.deleteAddressSuccess(position);}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        view.deleteAddressFail(e);
                        L.e(e.message+"  "+e.status);
                    }
                });
    }

    @Override
    public void setDefaultAddress(String useraccountid, String id, int position) {
        model.setDefaultAddress(useraccountid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver<String []>() {
                    @Override
                    public void onNext(String[] strings) {view.setDefaultAddressSuccess(position);}

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {view.setDefaultAddressFail(e);}
                });
    }
}
