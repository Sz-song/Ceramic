package com.yuanyu.ceramics.logistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogisticsActivity extends BaseActivity<LogisticsPresenter> implements LogisticsConstract.ILogisticsTracingView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.courier_num)
    TextView courierNum;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<LogisticsBean.Logistics> list = new ArrayList<>();
    private LogisticsAdapter adapter;
    private String logistics;
    private String logistics_id;
    @Override
    protected int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    protected LogisticsPresenter initPresent() {
        return new LogisticsPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("物流跟踪");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        Intent intent = getIntent();
        String images = intent.getStringExtra("image");
        logistics = intent.getStringExtra("logistics");
        logistics_id = intent.getStringExtra("logistics_id");
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + images)
                .override(100, 100)
                .placeholder(R.drawable.img_default)
                .into(image);
        switch (logistics_id) {
            case "SF":
                courierNum.setText("顺丰快递:" + logistics);
                break;
            case "ZTO":
                courierNum.setText("中通快递:" + logistics);
                break;
            case "HTKY":
                courierNum.setText("百世快递:" + logistics);
                break;
            case "YTO":
                courierNum.setText("圆通速递:" + logistics);
                break;
            case "YD":
                courierNum.setText("韵达速递:" + logistics);
                break;
            case "EMS":
                courierNum.setText("邮政EMS:" + logistics);
                break;
            case "DBL":
                courierNum.setText("德邦快递:" + logistics);
                break;
            case "ZJS":
                courierNum.setText("宅急送:" + logistics);
                break;
        }
        adapter = new LogisticsAdapter(this, list);
        recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(lm);
        swipe.setRefreshing(true);
        presenter.getLogisticsTracing(logistics, logistics_id);
        swipe.setOnRefreshListener(() -> {
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getLogisticsTracing(logistics, logistics_id);
        });
    }

    @Override
    public void getLogisticsTracingSuccess(LogisticsBean beans) {
        swipe.setRefreshing(false);
        switch (beans.getState()) {
            case "2":
                status.setText("运输中");
                break;
            case "3":
                status.setText("已签收");
                break;
            case "4":
                status.setText("问题件");
                break;
            default:
                status.setText("处理中");
                break;
        }

        if (beans.getTraces().size() > 0) {
            recyclerview.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            for (int i = 0; i < beans.getTraces().size(); i++) {
                list.add(0, beans.getTraces().get(i));
            }
            adapter.notifyDataSetChanged();
        } else {
            recyclerview.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        L.e(e.message + "" + e.status);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
