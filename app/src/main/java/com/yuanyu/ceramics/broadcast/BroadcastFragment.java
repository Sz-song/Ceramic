package com.yuanyu.ceramics.broadcast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class BroadcastFragment extends BaseFragment<BroadcastFragmentPresenter> implements BroadcastFragmentConstract.IBroadcastFragmentView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.frame)
    FrameLayout frame;
    private List<BroadcastBean> list;
    private BroadcastAdapter adapter;
    private int page;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected BroadcastFragmentPresenter initPresent() {
        return new BroadcastFragmentPresenter();
    }

    @Override
    protected void initEvent(View view) {
        list = new ArrayList<>();
        page=0;
        adapter = new BroadcastAdapter(getContext(), list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else return 1;
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page=0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>16) {
                        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page);
    }

    @Override
    public void initDataSuccess(List<BroadcastBean> beans) {
        list.addAll(beans);
        if(isAlive){
            adapter.notifyItemRangeInserted(list.size() - beans.size(),beans.size());
            swipe.setRefreshing(false);
            page++;
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            }else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        BroadcastBean broadcastBean=new BroadcastBean("","","","","");
        list.add(broadcastBean);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if(isAlive){
            swipe.setRefreshing(false);
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            }else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }
}
