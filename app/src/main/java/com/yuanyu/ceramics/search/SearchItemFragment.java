package com.yuanyu.ceramics.search;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.CommonDecoration;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchItemFragment extends BaseFragment<SearchItemPresenter> implements SearchItemConstract.ISearchItemView {
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
    private List<SearchItemBean> list;
    private SearchItemAdapter adapter;
    private int page;
    private String query;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected SearchItemPresenter initPresent() {
        return new SearchItemPresenter();
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
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        list=new ArrayList<>();
        page=0;
        adapter = new SearchItemAdapter(getContext(),list);
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
            presenter.SearchItemList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,query);
        });
        recyclerview.addItemDecoration(new CommonDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8,this.getResources().getDisplayMetrics())));
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>6){
                        presenter.SearchItemList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,query);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.SearchItemList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,query);
    }

    @Override
    public void SearchItemListSuccess(List<SearchItemBean> beans) {
        list.addAll(beans);
        if (isAlive) {
            L.e("123");
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
    public void SearchItemListFail(ExceptionHandler.ResponeThrowable e) {
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
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        presenter.SearchItemList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,query);
    }
}