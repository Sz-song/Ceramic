package com.yuanyu.ceramics.dingzhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectDashiActivity extends NormalActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private int page = 0;
    private SelectDashiModel model = new SelectDashiModel();
    private List<DashiCellBean> mList = new ArrayList<>();
    private SelectDashiAdapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dashi);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        intent = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back1_gray);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(false);
        swipe.setOnRefreshListener(() -> initData());
        adapter = new SelectDashiAdapter(this, mList, position -> {
            intent.putExtra("name",mList.get(position).getName());
            intent.putExtra("id",mList.get(position).getId());
            setResult(RESULT_OK,intent);
            finish();
        });
        recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(lm);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>8) {
                        loadData();
                    }
                }
            }
        });
        initData();
    }

    public void initData(){
        page = 0;
        mList.clear();
        adapter.notifyDataSetChanged();
        loadData();
    }

    public void loadData(){
        model.chooseDashi(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<DashiCellBean>>())
                .subscribe(new BaseObserver<List<DashiCellBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("onError: "+e.message+e.status );
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<DashiCellBean> list) {
                        page++;
                        mList.addAll(list);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
