package com.yuanyu.ceramics.shop_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.common.ResultBean;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopIndexSearchActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private ResultAdapter adapter;
    private List<ResultBean> list = new ArrayList<>();
    private int page = 0;
    private ShopIndexModel model = new ShopIndexModel();
    private String feilei;
    private String zhonglei;
    private String ticai;
    private String min_price;
    private String max_price;
    private String min_weight;
    private String max_weight;
    private String shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);
        ButterKnife.bind(this);
        title.setText("店铺搜索结果");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();
        feilei = intent.getStringExtra("feilei");
        zhonglei = intent.getStringExtra("zhonglei");
        ticai = intent.getStringExtra("ticai");
        min_price = intent.getStringExtra("min_price");
        max_price = intent.getStringExtra("max_price");
        min_weight = intent.getStringExtra("min_weight");
        max_weight = intent.getStringExtra("max_weight");
        shop_id = intent.getStringExtra("shop_id");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            initData();
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new ResultAdapter(list);
        recyclerview.setAdapter(adapter);
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂无数据");
        initData();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        initData();
                    }
                }
            }
        });
    }

    private void initData() {
        model.getShopFenleiData(page, feilei, zhonglei, ticai, min_price, max_price, min_weight, max_weight, shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<ResultBean>>())
                .subscribe(new BaseObserver<List<ResultBean>>() {
                    @Override
                    public void onNext(List<ResultBean> resultBeans) {
                        if (resultBeans.size() > 0) {
                            list.addAll(resultBeans);
                            adapter.notifyItemRangeInserted(list.size() - resultBeans.size(), resultBeans.size());
                            page++;
                        }
                        if (list.size() == 0) {
                            nodataImg.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.VISIBLE);
                        }else {
                            nodataImg.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);
                        }
                        swipe.setRefreshing(false);
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.message + " " + e.status);
                        swipe.setRefreshing(false);
                        if (list.size() == 0) {
                            nodataImg.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.VISIBLE);
                        }else {
                            nodataImg.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
