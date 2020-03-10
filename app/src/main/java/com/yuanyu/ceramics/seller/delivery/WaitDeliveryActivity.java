package com.yuanyu.ceramics.seller.delivery;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WaitDeliveryActivity extends BaseActivity<WaitDeliveryPresenter> implements WaitDeliveryConstract.IWaitDeliveryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    private WaitDeliveryAdapter adapter;
    private List<WaitDeliveryBean> list;
    private int page = 0;
    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }
    @Override
    protected WaitDeliveryPresenter initPresent() {
        return new WaitDeliveryPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("发货");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        swipe.setRefreshing(true);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getDeliveryOrdersResult(Sp.getString(this,AppConstant.SHOP_ID),page);
        });
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        list= new ArrayList<>();
        adapter = new WaitDeliveryAdapter(list, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>6) {
                        swipe.setRefreshing(true);
                        presenter.getDeliveryOrdersResult(Sp.getString(WaitDeliveryActivity.this,AppConstant.SHOP_ID),page);
                    }
                }
            }
        });
    }
    @Override
    public void setDataSuccess(List<WaitDeliveryBean> beans) {
        L.e("获取成功");
        if (beans.size() > 0) {
            page++;
        }
        list.addAll(beans);
        initIsTop(list);
        initIsBottom(list);
        adapter.notifyItemRangeInserted(list.size() - beans.size(), beans.size());
        swipe.setRefreshing(false);
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        swipe.setRefreshing(false);
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    private void initIsBottom(List<WaitDeliveryBean> beanList) {
        if (beanList.size() > 0) beanList.get(beanList.size() - 1).setIsbottom(true);
        for (int i = beanList.size() - 2; i >= 0; i--) {
            if (beanList.get(i).getOrdernum().equals(beanList.get(i + 1).getOrdernum())) {
                beanList.get(i).setIsbottom(false);
            } else {
                beanList.get(i).setIsbottom(true);
            }
        }
    }
    private void initIsTop(List<WaitDeliveryBean> beanList) {
        if (beanList.size() > 0) beanList.get(0).setIstop(true);
        for (int i = 1; i < beanList.size(); i++) {
            if (beanList.get(i).getOrdernum().equals(beanList.get(i - 1).getOrdernum())) {
                beanList.get(i).setIstop(false);
            } else {
                beanList.get(i).setIstop(true);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        presenter.getDeliveryOrdersResult(Sp.getString(this,AppConstant.SHOP_ID),page);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @OnClick({R.id.nodata_img, R.id.nodata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nodata_img:
            case R.id.nodata:
                page = 0;
                list.clear();
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(true);
                presenter.getDeliveryOrdersResult(Sp.getString(this, AppConstant.SHOP_ID),page);
                break;
        }
    }

}
