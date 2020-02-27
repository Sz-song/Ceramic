package com.yuanyu.ceramics.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends NormalActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public static void actionStart(Context context, int status) {
        Intent intent = new Intent(context, MyOrderActivity.class);//这里的.class是启动目标Activity
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initView();
    }

    void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablelayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        int status = intent.getIntExtra("status", 0);
        switch (status) {
            case AppConstant.ALL:
                viewPager.setCurrentItem(0);
                break;
            case AppConstant.DAIFUKUAN:
                viewPager.setCurrentItem(1);
                break;
            case AppConstant.DAIFAHUO:
                viewPager.setCurrentItem(2);
                break;
            case AppConstant.DAISHOUHUO:
                viewPager.setCurrentItem(3);
                break;
            case AppConstant.DAIPINGJIA:
                viewPager.setCurrentItem(4);
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}
