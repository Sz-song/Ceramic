package com.yuanyu.ceramics.meet_master;

import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetMasterActivity extends BaseActivity<MeetMasterPresenter> implements MeetMasterConstract.IMeetMasterView {
    private MeetMasterAdapter adapter;
    private List<MeetMasterBean> mList = new ArrayList<>();
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
    protected MeetMasterPresenter initPresent() {
        return new MeetMasterPresenter() {};
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
            presenter.initData(Sp.getString(this,AppConstant.USER_ACCOUNT_ID),page);
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>8) {
                        presenter.initData(Sp.getString(MeetMasterActivity.this,AppConstant.USER_ACCOUNT_ID),page);
                    }
                }
            }
        });
        presenter.initData(Sp.getString(this,AppConstant.USER_ACCOUNT_ID),page);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initDataSuccess(List<MeetMasterBean> list) {
            page++;
            swipe.setRefreshing(false);
            mList.addAll(list);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
            L.e(e.message+e.status);
            swipe.setRefreshing(false);
    }
}
