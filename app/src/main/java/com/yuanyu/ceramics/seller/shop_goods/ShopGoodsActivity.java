package com.yuanyu.ceramics.seller.shop_goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.seller.shop_shelve.ShelveActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopGoodsActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayout() {
        return R.layout.common_tablayout;
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_shop);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        title.setText("我的商品");
        int position = getIntent().getIntExtra("position", 0);
        ShopGoodsFragmentAdapter adapter = new ShopGoodsFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablelayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shelve_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.apply_shelve:
                Intent intent = new Intent(this, ShelveActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
