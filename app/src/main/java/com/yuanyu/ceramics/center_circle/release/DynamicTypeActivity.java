package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DynamicTypeActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.check_anyone)
    SmoothCheckBox checkAnyone;
    @BindView(R.id.anyone)
    RelativeLayout anyone;
    @BindView(R.id.check_friend)
    SmoothCheckBox checkFriend;
    @BindView(R.id.friend)
    RelativeLayout friend;
    @BindView(R.id.back)
    TextView back;

    private int dynamic_type;

    @Override
    protected int getLayout() {
        return R.layout.activity_dynamic_type;
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
        title.setText("谁可以见");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        checkAnyone.setChecked(true);
        dynamic_type = 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.release, R.id.check_anyone, R.id.anyone, R.id.check_friend, R.id.friend,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.release:
                Intent intent = new Intent();
                intent.putExtra("dynamic_type", dynamic_type);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.check_anyone:
                checkAnyone.setChecked(true);
                checkFriend.setChecked(false);
                dynamic_type = 0;
                break;
            case R.id.anyone:
                checkAnyone.setChecked(true);
                checkFriend.setChecked(false);
                dynamic_type = 0;
                break;
            case R.id.check_friend:
                checkAnyone.setChecked(false);
                checkFriend.setChecked(true);
                dynamic_type = 1;
                break;
            case R.id.friend:
                checkAnyone.setChecked(false);
                checkFriend.setChecked(true);
                dynamic_type = 1;
                break;
            case R.id.back:
                finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
