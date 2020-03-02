package com.yuanyu.ceramics.mine.systemsetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemSettingActivity extends NormalActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.quit)
    TextView quit;
    private String[] list=new String[]{"清除缓存","黑名单管理","账户安全","意见反馈","关于青花瓷App","联系客服"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        title.setText("系统设置");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        SystemSettingAdapter adapter = new SystemSettingAdapter(this, list);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        quit.setOnClickListener(view -> {
            Intent intent=new Intent(SystemSettingActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//            SpUtils.putBoolean(SystemSettingActivity.this,AppConstant.IS_LOGIN,false);
//            SpUtils.putBoolean(SystemSettingActivity.this,AppConstant.IS_MERCHANT,false);
            startActivity(intent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
