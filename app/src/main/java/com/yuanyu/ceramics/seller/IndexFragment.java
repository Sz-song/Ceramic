package com.yuanyu.ceramics.seller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.base.BasePresenter;

public class IndexFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void initEvent(View view) {

    }

    @Override
    protected void initData() {

    }
}
