package com.yuanyu.ceramics.bazaar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreCenterFragment extends BaseFragment<StoreCenterPresenter> implements StoreCenterConstract.IStoreCenterView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private int page;
    private List<StoreCenterBean> list;
    private StoreCenterAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected StoreCenterPresenter initPresent() {
        return new StoreCenterPresenter();
    }

    @Override
    protected void initEvent(View view) {
        page = 0;
        list = new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        recyclerview.setBackgroundColor(getActivity().getResources().getColor(R.color.background_gray));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StoreCenterAdapter(getContext(), list);
        recyclerview.setAdapter(adapter);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getStoreCenterList(page);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case 2:
                        if (isAlive) {
                            Glide.with(getContext()).pauseRequests();}
                        break;
                    case 1:
                    case 0:
                        if (isAlive) {
                            Glide.with(getContext()).resumeRequests();}
                        break;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    }
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 8) {
                        presenter.getStoreCenterList(page);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getStoreCenterList(page);
    }
    @OnClick(R.id.nodata_img)
    public void onViewClicked() {
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        presenter.getStoreCenterList(page);
    }
    @Override
    public void getStoreCenterSuccess(List<StoreCenterBean> beans) {
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
    public void getStoreCenterFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        if(isAlive) {
            swipe.setRefreshing(false);
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

}
