package com.yuanyu.ceramics.dingzhi;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDingzhiActivity extends NormalActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.divider)
    View divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_tablayout_white);
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
        title.setText("我的定制");
        int position = getIntent().getIntExtra("position", 0);
        ShopDingzhiFragmentAdapter adapter = new ShopDingzhiFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablelayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}