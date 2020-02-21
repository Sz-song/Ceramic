package com.yuanyu.ceramics.dingzhi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShopDingzhiFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"定制订单", "正在定制", "定制成功"};

    ShopDingzhiFragmentAdapter(FragmentManager fm) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            ShopDingzhiFragment fragment0=new ShopDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment0.setArguments(bundle);
            return fragment0;
        }
        else if (position == 1) {
            ShopDingzhiFragment fragment1=new ShopDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position == 2) {
            ShopDingzhiFragment fragment2=new ShopDingzhiFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment2.setArguments(bundle);
            return fragment2;
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
