package com.yuanyu.ceramics.search;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.WrapContentLinearLayoutManager;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchShopFragment extends BaseFragment<SearchShopPresenter> implements SearchShopConstract.ISearchShopView {

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
    private List<SearchShopBean> list;
    private SearchShopAdapter adapter;
    private int page;
    private String query;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected SearchShopPresenter initPresent() {
        return new SearchShopPresenter();
    }

    @Override
    protected void initEvent(View view) {
        query = getArguments().getString("query");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        list = new ArrayList<>();
        page = 0;
        adapter = new SearchShopAdapter(getContext(), list);
        adapter.setListener(() -> {
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        });
        recyclerview.setAdapter(adapter);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.SearchShopList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), page,query);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((WrapContentLinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 8) {
                        presenter.SearchShopList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), page,query);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.SearchShopList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), page,query);
    }

    @Override
    public void SearchShopListSuccess(List<SearchShopBean> beans) {
        list.addAll(beans);
        if (isAlive) {
            adapter.notifyItemRangeInserted(list.size() - beans.size(), beans.size());
            swipe.setRefreshing(false);
            page++;
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void SearchShopListFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + e.status);
        if (isAlive) {
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

    @Override
    public void Search(String query) {
        this.query=query;
        if(list!=null){
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.SearchShopList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), page,query);
        }
    }
}