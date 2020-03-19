package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetFriendsActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private int page = 0;
    private int pagesize = 20;
    private GetFriendsAdapter adapter;
    private List<FriendBean> list;
    private ReleaseModel model = new ReleaseModel();

    @Override
    protected int getLayout() {
        return R.layout.activity_get_friends;
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
        title.setText("好友列表");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter = new GetFriendsAdapter(this, list);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        adapter.setItemOnClick(new GetFriendsAdapter.ItemOnClick() {
            @Override
            public void click(FriendBean friendBean) {
                Intent intent=new Intent();
                intent.putExtra("friend_data",friendBean);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        initList();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                list.clear();
                adapter.notifyDataSetChanged();
                initList();
            }
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount()-1) {
                        initList();
                    }
                }
            }
        });
    }
    private void initList() {
        model.getFriends(Sp.getString(this, "useraccountid"), page, pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<FriendBean>>())
                .subscribe(new BaseObserver<List<FriendBean>>() {
                    @Override
                    public void onNext(List<FriendBean> friendBeans) {
                        for (int i = 0; i < friendBeans.size(); i++) {
                            list.add(friendBeans.get(i));
                        }
                        adapter.notifyItemRangeInserted(list.size() - friendBeans.size(), friendBeans.size());
                        swipe.setRefreshing(false);
                        page++;
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.message + e.status);
                        swipe.setRefreshing(false);
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
