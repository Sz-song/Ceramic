package com.yuanyu.ceramics.address_manage;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddOrEditAddressPresenter extends BasePresenter<AddOrEditAddressConstract.IAddAddressView>  implements AddOrEditAddressConstract.IAddAddressPresenter{
    private AddOrEditAddressConstract.IAddAddressModel model;
    AddOrEditAddressPresenter() {model=new AddOrEditAddressModel();}
    @Override
    public void addAddress(String useraccountid,String name,String phone,String province,String city,String exparea,String address,int isdefault) {
        model.addAddress(useraccountid, name, phone,province,city,exparea,address,isdefault)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){  view.Success();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.Fail(e); }}
                });
    }

    @Override
    public void editAddress(String useraccountid,String id,String name,String phone,String province,String city,String exparea,String address,int isdefault) {
        model.editAddress(useraccountid,id,name, phone,province,city,exparea,address,isdefault)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if(view!=null){ view.Success(); }}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.Fail(e);}}
                });
    }
}
