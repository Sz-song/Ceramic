package com.yuanyu.ceramics.master;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterFragment extends BaseFragment<MasterFragmentPresenter> implements MasterFragmentConstract.IMasterFragmentView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<MasterItemBean> list;
    private MasterFragmentAdapter adapter;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    protected MasterFragmentPresenter initPresent() {return new MasterFragmentPresenter(); }

    @Override
    protected void initEvent(View view) {
        list=new ArrayList<>();
        adapter= new MasterFragmentAdapter(getContext(),list);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
        });
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void initDataSuccess(List<MasterItemBean> beans) {
        list.addAll(beans);
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status+"  "+e.message);
        swipe.setRefreshing(false);
    }

}
