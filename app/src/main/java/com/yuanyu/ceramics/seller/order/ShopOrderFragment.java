package com.yuanyu.ceramics.seller.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import butterknife.OnClick;

public class ShopOrderFragment extends BaseFragment<ShopOrderPresenter> implements ShopOrderConstract.IShopOrderView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.frame)
    FrameLayout frame;
    private ShopOrderAdapter adapter;
    private List<ShopOrderBean> list;
    private int page;
    private int type;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, null);
    }

    @Override
    protected ShopOrderPresenter initPresent() {
        return new ShopOrderPresenter();
    }

    @Override
    protected void initEvent(View view) {
        type = getArguments().getInt("type");
        list = new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        frame.setBackgroundColor(getResources().getColor(R.color.background_gray));
        WrapContentLinearLayoutManager manager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        adapter = new ShopOrderAdapter(getContext(), list);
        recyclerview.setAdapter(adapter);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getOrderList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 7) {
                        presenter.getOrderList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        page = 0;
        swipe.setRefreshing(true);
        presenter.getOrderList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
    }

    @Override
    public void getOrderListSuccess(List<ShopOrderBean> beans) {
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
    public void getOrderListFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
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

    @OnClick({R.id.nodata_img, R.id.nodata})
    public void onViewClicked(View view) {
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        presenter.getOrderList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
    }
}
