package com.yuanyu.ceramics.shop_index;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.WrapContentLinearLayoutManager;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopPinglunFragment extends BaseFragment<ShopPinglunPresenter> implements ShopPinglunConstract.IShopPinglunView {

    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.nice_pinglun)
    TextView nicePinglun;
    @BindView(R.id.mid_pinglun)
    TextView midPinglun;
    @BindView(R.id.bad_pinglun)
    TextView badPinglun;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.top_linear)
    LinearLayout topLinear;
    private List<EvaluationManageBean> list;
    private ShopPinglunAdapter adapter;
    private String shopid;
    private int page;
    private int type;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop_pinglun, container, false);
    }

    @Override
    protected ShopPinglunPresenter initPresent() {
        return new ShopPinglunPresenter();
    }

    @Override
    public void initEvent(View view) {
        page = 0;
        type = 0;
        list = new ArrayList<>();
        Bundle bundle = getArguments();
        shopid = bundle.getString("shopid");
        all.setActivated(true);
        nicePinglun.setActivated(false);
        midPinglun.setActivated(false);
        badPinglun.setActivated(false);
        adapter = new ShopPinglunAdapter(getContext(), list);
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(400, 400)
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getShopPinglun(shopid, page, type);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
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
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 6) {
                        presenter.getShopPinglun(shopid, page, type);
                    }
                }
            }
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 20) {//表示下滑
//                    if(topLinear.getVisibility()==View.VISIBLE) {
//                        topLinear.setVisibility(View.GONE);
//                        rate.setVisibility(View.GONE);
//                    }
//                } else if (dy < -10) {//表示 上滑
//                    if(topLinear.getVisibility()==View.GONE) {
//                        topLinear.setVisibility(View.VISIBLE);
//                        rate.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
        });
    }

    @Override
    protected void initData() {
        presenter.getShopPinglun(shopid, page, type);
    }

    @Override
    public void getShopPinglunSuccess(ShopPinglunBean bean) {
        if (isAlive) {
            rate.setText("好评度   " + bean.getRate() + "%");
            list.addAll(bean.getList());
            adapter.notifyItemRangeInserted(list.size() - bean.getList().size(), bean.getList().size());
            page++;
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
    public void getShopPinglunFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        L.e(e.message + " " + e.status);
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

    @OnClick({R.id.all, R.id.nice_pinglun, R.id.mid_pinglun, R.id.bad_pinglun, R.id.nodata_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                all.setActivated(true);
                nicePinglun.setActivated(false);
                midPinglun.setActivated(false);
                badPinglun.setActivated(false);
                page = 0;
                type = 0;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getShopPinglun(shopid, page, type);
                break;
            case R.id.nice_pinglun:
                all.setActivated(false);
                nicePinglun.setActivated(true);
                midPinglun.setActivated(false);
                badPinglun.setActivated(false);
                page = 0;
                type = 1;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getShopPinglun(shopid, page, type);
                break;
            case R.id.mid_pinglun:
                all.setActivated(false);
                nicePinglun.setActivated(false);
                midPinglun.setActivated(true);
                badPinglun.setActivated(false);
                page = 0;
                type = 2;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getShopPinglun(shopid, page, type);
                break;
            case R.id.bad_pinglun:
                all.setActivated(false);
                nicePinglun.setActivated(false);
                midPinglun.setActivated(false);
                badPinglun.setActivated(true);
                page = 0;
                type = 3;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getShopPinglun(shopid, page, type);
                break;
            case R.id.nodata_img:
                page = 0;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getShopPinglun(shopid, page, type);
                break;
        }
    }

}
