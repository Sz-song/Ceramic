package com.yuanyu.ceramics.meet_master;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetMasterActivity extends NormalActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private MeetMasterAdapter adapter;
    private List<MeetMasterBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_master);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back1);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(false);
        swipe.setOnRefreshListener(() -> {
//            page = 0;
//            L.e("initdata page "+page);
//            mList.clear();
//            adapter.notifyDataSetChanged();
//            loadData();
        });
        MeetMasterBean mb1=new MeetMasterBean(1,"1","黄建宏工作室","img/banner1.jpg",6,80,"做瓷艺是个辛苦活，真正是需要“工匠精神”");
        MeetMasterBean mb2=new MeetMasterBean(2,"2","王锡良工作室","img/banner1.jpg",6,80,"做瓷艺是个辛苦活，真正是需要“工匠精神”");

        mList.add(mb1);
        mList.add(mb2);
        adapter = new MeetMasterAdapter(this,mList);
        recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(lm);
//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
//                int lastPosition;
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
//                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&recycleState) {
//                        loadData();
//                    }
//                }
//            }
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                recycleState = dy > 0;
//            }
//        });
//        loadData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
