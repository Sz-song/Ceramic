package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
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

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsActivity extends BaseActivity<DraftsPresenter> implements DraftsConstract.IDraftsView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    private int page = 0;
    private int pagesize = 20;
    private DraftsAdapter adapter;
    private List<DraftsBean> list;
    private LoadingDialog loaddialog;

    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }

    @Override
    protected DraftsPresenter initPresent() {
        return new DraftsPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("草稿箱");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        swipe.setBackgroundResource(R.color.white);
        swipe.setRefreshing(true);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getDrafts(Sp.getString(this,"useraccountid"),page,pagesize);
         });
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        list = new ArrayList<>();
        adapter = new DraftsAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        adapter.ToresListener(position -> {
            if (list.get(position).getType().equals("0")) {
                Intent intent = new Intent(DraftsActivity.this, ReleaseDynamicActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }else {
                Intent intent = new Intent(DraftsActivity.this, ReleaseArticleActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
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
                        presenter.getDrafts(Sp.getString(DraftsActivity.this,"useraccountid"),page,pagesize);
                    }
                }
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

    @Override
    public void getDraftsSuccess(List<DraftsBean> beans) {
        L.e("获取成功");
        if (beans.size() > 0){
            page++;
        }
        list.addAll(beans);
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
    public void getDraftsFail(ExceptionHandler.ResponeThrowable e) {
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

}
