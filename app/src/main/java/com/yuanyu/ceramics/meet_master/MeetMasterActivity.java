package com.yuanyu.ceramics.meet_master;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
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

public class MeetMasterActivity extends BaseActivity {
    private MeetMasterAdapter adapter;
    private List<MeetMasterBean> mList = new ArrayList<>();
    private MeetMasterModel model = new MeetMasterModel();
    private boolean recycleState;
    private int page = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    protected int getLayout() {
        return R.layout.activity_meet_master;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back1);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(false);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            L.e("initdata page "+page);
            mList.clear();
            adapter.notifyDataSetChanged();
            loadData();
        });
        adapter = new MeetMasterAdapter(this,mList);
        recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(lm);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&recycleState) {
                        loadData();
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recycleState = dy > 0;
            }
        });
        loadData();
    }

    private void loadData(){
        model.getMasterStudio(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MeetMasterBean>>())
                .subscribe(new BaseObserver<List<MeetMasterBean>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if (!isDestroyed()){
                            L.e(e.message+e.status);
                            swipe.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onNext(List<MeetMasterBean> list) {
                        if (!isDestroyed()){
                            page++;
                            swipe.setRefreshing(false);
                            for (MeetMasterBean bean : list) mList.add(bean);
                            adapter.notifyDataSetChanged();
                        }

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
