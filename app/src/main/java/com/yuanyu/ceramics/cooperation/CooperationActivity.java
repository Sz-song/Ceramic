package com.yuanyu.ceramics.cooperation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperationActivity extends BaseActivity<CooperationPresenter> implements CooperationListConstract.ICooperationListView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<CooperationListBean> list;
    private CooperationAdapter adapter;
    private LoadingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }

    @Override
    protected CooperationPresenter initPresent() {
        return new CooperationPresenter();
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
        title.setText("合作机构");
        list=new ArrayList<>();
        dialog=new LoadingDialog(this);
//        dialog.show();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        CooperationListBean cb1 = new CooperationListBean("1","img/banner1.jpg","深圳市大数据研究院","深圳市大数据研究院是在深圳市委、市政府的支持下于2016年3月组建，以整合引领深圳市的大数据相关产业与科研计划。研究院的初始资金来源于香港中文大","");
        CooperationListBean cb2 = new CooperationListBean("2","img/banner1.jpg","深圳市智能机器人研究院","深圳市智能机器人研究院是在深圳市政府大力支持下的一个国际化、专业化的新型科研机构。主要研究方向包括：先进智能传感技术、服务机器人、工业机器人与自动化、特种机器人、微纳米机器人以及智能信息系统等","");
        list.add(cb1);
        list.add(cb2);
        adapter = new CooperationAdapter(this,list);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(false);
        swipe.setOnRefreshListener(() -> {
//            list.clear();
//            adapter.notifyDataSetChanged();
//            presenter.getCooperationList();
        });
    }

    @Override
    public void getCooperationListSuccess(List<CooperationListBean> beans) {
        swipe.setRefreshing(false);
        dialog.dismiss();
        list.addAll(beans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getCooperationListFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        dialog.dismiss();
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    //返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
