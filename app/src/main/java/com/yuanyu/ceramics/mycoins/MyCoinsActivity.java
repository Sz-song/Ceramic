package com.yuanyu.ceramics.mycoins;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CustomScrollViewPager;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCoinsActivity extends BaseActivity<MyCoinsPresenter> implements MyCoinsConstract.IMyCoinsView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coins_num)
    TextView coinsNum;
    @BindView(R.id.coins_num_detail)
    TextView coinsNumDetail;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.viewpager)
    CustomScrollViewPager viewpager;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_coins;
    }

    @Override
    protected MyCoinsPresenter initPresent() {
        return new MyCoinsPresenter();
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
        swipe.setColorSchemeResources(R.color.colorPrimary);
        MyConinsFragmentAdapter adapter = new MyConinsFragmentAdapter(getSupportFragmentManager(), 0);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        presenter.getMyCoinsDate(Sp.getString(this, AppConstant.USER_ACCOUNT_ID));
        swipe.setOnRefreshListener(() -> {
            presenter.getMyCoinsDate(Sp.getString(this, AppConstant.USER_ACCOUNT_ID));
            adapter.reflash();
        });
    }

    @Override
    public void getMyCoinsDateSuccess(String num) {
        coinsNum.setText(num);
        swipe.setRefreshing(false);
    }

    @Override
    public void getMyCoinsDateFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        swipe.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.coins_num_detail)
    public void onViewClicked() {
        Intent intent = new Intent(this, MyCoinsDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
