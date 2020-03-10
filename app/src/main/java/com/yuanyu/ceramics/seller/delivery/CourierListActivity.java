package com.yuanyu.ceramics.seller.delivery;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourierListActivity extends BaseActivity<CourierPresenter> implements CourierConstract.ICourierView {

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
    private CourierAdapter adapter;
    private List<CourierBean> list;
    private DeliveryBean deliveryBean;
    private String status;
    private int type;
    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }

    @Override
    protected CourierPresenter initPresent() {
        return new CourierPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("其他快递");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        type=getIntent().getIntExtra("type",-1);
        swipe.setRefreshing(true);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("请稍后再试");
        adapter = new CourierAdapter(this, list);
        adapter.setListener(position -> {
            Intent intent = new Intent();
            intent.putExtra("type",type);
            intent.putExtra("courier_id", list.get(position).getId());
            intent.putExtra("courier_name", list.get(position).getName());
            setResult(RESULT_OK, intent);
            finish();
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        if(type==0) {//智选物流
            if (null != getIntent().getStringExtra("status") && null != getIntent().getSerializableExtra("deliverybean")) {
                status = getIntent().getStringExtra("status");
                deliveryBean = (DeliveryBean) getIntent().getSerializableExtra("deliverybean");
                presenter.getCourierData(status, deliveryBean);
                swipe.setOnRefreshListener(() -> {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    presenter.getCourierData(status, deliveryBean);
                });
            }
        }else if(type==1){//自己发货
            presenter.getCourierCompany();
            swipe.setOnRefreshListener(() -> {
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getCourierCompany();
            });
        }
    }
    @Override
    public void getCourierDataSuccess(List<CourierBean> beans) {
        list.addAll(beans);
        adapter.notifyDataSetChanged();
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
    public void getCourierCompanySuccess(List<CourierBean> beans) {
        for (int i=0;i<beans.size();i++){
            beans.get(i).setType(1);
            list.add(beans.get(i));
        }
        adapter.notifyDataSetChanged();
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
    public void getCourierDataFail(ExceptionHandler.ResponeThrowable e) {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
