package com.yuanyu.ceramics.mycoins;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExchangeCoinsFragment extends BaseFragment<ExchangeCoinsPresenter> implements ExchangeCoinsConstract.IExchangeCoinsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.root)
    LinearLayout root;
    private ExchangeCoinsAdapter adapter;
    private List<ExchangeCoinsBean> list;
    private ExchangeCoinsPopupwindow exchangeCoinsPopupwindow;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_exchange_coins, container, false);
    }

    @Override
    protected ExchangeCoinsPresenter initPresent() {
        return new ExchangeCoinsPresenter();
    }

    @Override
    protected void initEvent(View view) {
        list = new ArrayList<>();
        exchangeCoinsPopupwindow = new ExchangeCoinsPopupwindow(getActivity());
        exchangeCoinsPopupwindow.setExchangeListener((true_name, alipay_num, id) -> presenter.ExchangeCoins(id, Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), alipay_num, true_name));
        adapter = new ExchangeCoinsAdapter(getContext(), list);
        adapter.setChangeClickListener(position -> {
            Intent intent;
            if (null != Sp.getString(getContext(), AppConstant.MOBILE) && Sp.getString(getContext(), AppConstant.MOBILE).length() > 8) {
                exchangeCoinsPopupwindow.setId(list.get(position).getId());
                exchangeCoinsPopupwindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            } else {
//                intent = new Intent(getContext(), BindPhoneActivity.class);
//                intent.putExtra("type", 1);
//                getContext().startActivity(intent);
            }
        });
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        presenter.getMyCoinsExchange(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void getMyCoinsExchangeSuccess(List<ExchangeCoinsBean> beans) {
        list.clear();
        list.addAll(beans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyCoinsExchangeFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ExchangeCoinsSuccess() {
        exchangeCoinsPopupwindow.dismiss();
        Toast.makeText(getContext(), "兑换成功，预计3个工作日内到账", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ExchangeCoinsFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }
    public void reflash() {
        presenter.getMyCoinsExchange(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }
}
