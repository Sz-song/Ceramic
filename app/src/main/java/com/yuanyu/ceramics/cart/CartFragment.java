package com.yuanyu.ceramics.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

public class CartFragment extends BaseFragment<CartPresenter> implements CartConstract.ICartView {
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected CartPresenter initPresent() {
        return new CartPresenter();
    }

    @Override
    protected void initEvent(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void getGoodsdataSuccess(List<GoodsBean> beans) {

    }

    @Override
    public void getGoodsdataFail(ExceptionHandler.ResponeThrowable e) {

    }

    @Override
    public void deleteCartItemSuccess(String id) {

    }

    @Override
    public void deleteCartItemFail(ExceptionHandler.ResponeThrowable e) {

    }
}
