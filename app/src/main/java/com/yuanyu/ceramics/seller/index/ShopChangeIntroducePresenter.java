package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopChangeIntroducePresenter extends BasePresenter<ShopChangeIntroduceConstract.IShopChangeIntroduceView> implements ShopChangeIntroduceConstract.IShopChangeIntroducePresenter {
    private ShopChangeIntroduceConstract.IShopChangeIntroduceModel model;

    public ShopChangeIntroducePresenter() {
        model=new ShopChangeIntroduceModel();
    }

    @Override
    public void ShopChangeIntroduce(String shop_id, String introduce) {
        model.ShopChangeIntroduce(shop_id,introduce)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.ShopChangeIntroduceSuccess(aBoolean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.ShopChangeIntroduceFail(e);}
                    }
                });
    }
}
