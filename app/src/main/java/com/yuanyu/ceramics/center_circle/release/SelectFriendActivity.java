package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectFriendActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.checkedimg)
    RecyclerView checkedimg;
    private int page = 0;
    private int pagesize = 20;
    private SelectFriendAdapter adapter;
    private GetFriImgAdapter adapter2;
    private List<FriendBean> list;
    private List<FriendBean> list2;
    private List<FriendBean> listarr;
    private ReleaseModel model = new ReleaseModel();


    @Override
    protected int getLayout() {
        return R.layout.activity_select_friend;
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
        title.setText("提醒谁看");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        listarr = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter = new SelectFriendAdapter(this, list);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        adapter.setListener((holder, position) -> {
            if (holder instanceof SelectFriendAdapter.ViewHolder) {
                if (list2.size() < 1){
                    if (list.get(position).isChecked()){
                        list2.add(list.get(position));
                    }
                }else {
//                    判断list2中是否包含选中的元素
                    for (int i = 0; i < list2.size(); i++) {
                        if (list2.get(i).getId() == list.get(position).getId()){
                            if (list.get(position).isChecked()){}else {
                                list2.remove(i);
                                break;
                            }
                        }else {
                            if (list.get(position).isChecked()){
                                list2.add(list.get(position));
                                break;
                            }else {}
                        }
                    }
                }


                adapter2 = new GetFriImgAdapter(SelectFriendActivity.this,list2);
                checkedimg.setAdapter(adapter2);
                ((SelectFriendAdapter.ViewHolder) holder).itemCheckbox.setChecked(list.get(position).isChecked());
                adapter.notifyItemChanged(position);
            }
        });
        initList();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                list.clear();
                list2.clear();
                adapter2.notifyDataSetChanged();
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.release,R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.release:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()) {
                        listarr.add(list.get(i));
                    }
                }
                bundle.putSerializable("friend_data", (Serializable) listarr);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            case R.id.back:
                finish();
        }
    }
}
