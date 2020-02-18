package com.yuanyu.ceramics.cart;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartPresenter extends BasePresenter<CartConstract.ICartView> implements CartConstract.ICartPresenter {
    private CartConstract.ICartModel model;
    CartPresenter() {model=new CartModel(); }
    @Override
    public void getGoodsdata(String useraccountid) {
        model.getGoodsdata(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<GoodsBean>>())
                .subscribe(new BaseObserver<List<GoodsBean>>() {
                    @Override
                    public void onNext(List<GoodsBean> beans) {
                        if(view!=null){view.getGoodsdataSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getGoodsdataFail(e);}
                    }
                });
    }

    @Override
    public void initList(List<CartBean> list, List<GoodsBean> goodsBeans) {
        for(int i=0;i<goodsBeans.size();i++){
            boolean equals=false;
            for(int j=0;j<list.size();j++){
                if((goodsBeans.get(i).getShopid()+"").equals(list.get(j).getShopid())){
                    equals=true;
                    CartBean.CartItemBean itembean=list.get(j).new CartItemBean(goodsBeans.get(i).getId(),goodsBeans.get(i).getImage(),goodsBeans.get(i).getCommodityname(),goodsBeans.get(i).getPrice(),true);
                    list.get(j).getList().add(itembean);
                }
            }
            if(!equals){
                CartBean cartBean=new CartBean(goodsBeans.get(i).getShoplogo(),goodsBeans.get(i).getShopname(),goodsBeans.get(i).getShopid()+"");
                CartBean.CartItemBean itembean=cartBean.new CartItemBean(goodsBeans.get(i).getId(),goodsBeans.get(i).getImage(),goodsBeans.get(i).getCommodityname(),goodsBeans.get(i).getPrice(),true);
                cartBean.getList().add(itembean);
                list.add(cartBean);
            }
        }
    }

    @Override
    public void deleteCartItem(String useraccountid, String id) {
        model.deleteCartItem(useraccountid,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.deleteCartItemSuccess(id);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.deleteCartItemFail(e);}
                    }
                });

    }

    @Override
    public void initpayList(List<CartBean> list, List<GoodsBean> payList) {
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).getList().size();j++){
                if(list.get(i).getList().get(j).isSelect()){
                    try {
                        GoodsBean goodsBean = new GoodsBean(list.get(i).getList().get(j).getId(), list.get(i).getShoplogo(), list.get(i).getShopname(), Integer.parseInt(list.get(i).getShopid()),
                                list.get(i).getList().get(j).getImage(), list.get(i).getList().get(j).getCommodityname(), "", list.get(i).getList().get(j).getPrice(), true, true, true);
                        payList.add(goodsBean);
                    }catch (Exception e){
                        L.e(e.getMessage());
                    }
                }
            }
        }

    }
}
