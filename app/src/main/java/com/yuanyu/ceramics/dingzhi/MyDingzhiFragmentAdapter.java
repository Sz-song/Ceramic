package com.yuanyu.ceramics.dingzhi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyDingzhiFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"定制审核", "审核成功", "正在定制", "定制成功"};

    MyDingzhiFragmentAdapter(FragmentManager fm) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            MyDingzhiFragment fragment0=new MyDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment0.setArguments(bundle);
            return fragment0;
        }
        else if (position == 1) {
            MyDingzhiFragment fragment1=new MyDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position == 2) {
            MyDingzhiFragment fragment2=new MyDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        else if (position == 3) {
            MyDingzhiFragment fragment3=new MyDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment3.setArguments(bundle);
            return fragment3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
