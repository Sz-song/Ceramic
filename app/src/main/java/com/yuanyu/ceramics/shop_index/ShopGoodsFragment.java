package com.yuanyu.ceramics.shop_index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ShopGoodsFragment extends BaseFragment<ShopGoodsPresenter> implements ShopGoodsConstract.IShopGoodsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<ShopGoodsBean> list;
    private ShopGoodsAdapter adapter;
    private int page = 0;
    private int type;
    private String shopid;
    private boolean enable=true;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected ShopGoodsPresenter initPresent() {
        return new ShopGoodsPresenter();
    }

    @Override
    protected void initEvent(View view) {
        list = new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(200,200)
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        Bundle bundle = getArguments();
        shopid = bundle.getString("shopid");
        type=bundle.getInt("type");
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new ShopGoodsAdapter(getContext(),list);
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case 2:if(isAlive) { Glide.with(getContext()).pauseRequests();}
                        break;
                    case 1:if(isAlive) {Glide.with(getContext()).resumeRequests();}
                    case 0:
                        if(isAlive) {
                        Glide.with(getContext()).resumeRequests();}
                        break;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                }
                if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>8&&enable) {
                    enable=false;
                    presenter.getShopGoodsList(shopid,page,type);
                }
            }
        });
        swipe.setOnRefreshListener(() -> {
            if(enable) {
                page = 0;
                list.clear();
                adapter.notifyDataSetChanged();
                enable=false;
                presenter.getShopGoodsList(shopid, page, type);
            }else{
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getShopGoodsList(shopid,page,type);
    }

    @Override
    public void getShopGoodsSuccess(List<ShopGoodsBean> listbean) {
        if(isAlive) {
            list.addAll(listbean);
            adapter.notifyItemRangeInserted(list.size() - listbean.size(), listbean.size());
            page++;
            swipe.setRefreshing(false);
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
            enable=true;
        }
    }

    @Override
    public void getShopGoodsFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
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
        enable=true;
    }
}
