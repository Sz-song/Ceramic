package com.yuanyu.ceramics.broadcast;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class BroadcastFragment extends BaseFragment<BroadcastFragmentPresenter> implements BroadcastFragmentConstract.IBroadcastFragmentView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<BroadcastBean> list;
    private BroadcastAdapter adapter;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_broadcast, container, false);
    }

    @Override
    protected BroadcastFragmentPresenter initPresent() {
        return new BroadcastFragmentPresenter();
    }

    @Override
    protected void initEvent(View view) {
        list=new ArrayList<>();
        adapter= new BroadcastAdapter(getContext(),list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0) { return 2; }
                else return 1;
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
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
    public void initDataSuccess(List<BroadcastBean> beans) {
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
