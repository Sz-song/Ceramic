package com.yuanyu.ceramics.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private ViewImageFragmentAdapter adapter;
    private List<String> list;
    private int position;
    public static void actionStart(Context context, int position, ArrayList list) {
        Intent intent = new Intent(context, ViewImageActivity.class);//这里的Target.class是启动目标Activity
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("list",list);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_view_image;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        list=intent.getStringArrayListExtra("list");
        if(position>=list.size()||position<=0){
            position=0;
        }
    }

    private void initView() {
        adapter=new ViewImageFragmentAdapter(getSupportFragmentManager(),list);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(position);
        int i = position + 1;
        tvNum.setText("" + i+"/"+list.size());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int i = position + 1;
                tvNum.setText("" + i+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
