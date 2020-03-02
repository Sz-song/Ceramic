package com.yuanyu.ceramics.personal_index.fans_focus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FocusAndFansActicity extends NormalActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private FocusAndFansFragmentAdapter fragmentAdapter;
    private String[] tab = new String[]{"推荐", "我的关注", "我的粉丝"};
    private String[] tab1 = new String[]{"推荐", "TA的关注", "TA的粉丝"};
    private String userid;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_tablayout_white);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        position = intent.getIntExtra("position", 0);
        title.setText("我的好友");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if (userid.equals(Sp.getString(this, "useraccountid"))) {
            fragmentAdapter = new FocusAndFansFragmentAdapter(getSupportFragmentManager(), tab, userid);
        } else {
            fragmentAdapter = new FocusAndFansFragmentAdapter(getSupportFragmentManager(), tab1, userid);
        }
        viewPager.setAdapter(fragmentAdapter);
        tablelayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
