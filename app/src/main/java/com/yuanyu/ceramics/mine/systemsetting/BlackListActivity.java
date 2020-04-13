package com.yuanyu.ceramics.mine.systemsetting;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BlackListActivity extends BaseActivity<BlackListPresenter> implements BlackListConstract.IBlackListView {
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
    private BlackListAdapter adapter;
    List<BlackListBean> list;
    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }

    @Override
    protected BlackListPresenter initPresent() {
        return new BlackListPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("黑名单管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list=new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getBlacklist(Sp.getString(this, "useraccountid"));
        });
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(200, 200)
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter = new BlackListAdapter(list, this);
        recyclerview.setAdapter(adapter);
        adapter.setOnRemoveBlacklist(position ->
                presenter.removeBlacklist(Sp.getString(this, "useraccountid"),list.get(position).getBlackuserid(),position)
        );
        presenter.getBlacklist(Sp.getString(this, "useraccountid"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void getBlacklistSuccess(List<BlackListBean> beans) {
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
    public void removeBlacklistSuccess(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, list.size() - position);
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
