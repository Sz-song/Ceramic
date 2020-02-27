package com.yuanyu.ceramics.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyOrderFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"全部","待付款","待发货","待收货","待评价"};
    MyOrderFragmentAdapter(FragmentManager fm){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            MyOrderFragment fragment0=new MyOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment0.setArguments(bundle);
            return fragment0;
        }
        else if (position == 1) {
            MyOrderFragment fragment1=new MyOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position == 2) {
            MyOrderFragment fragment2=new MyOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        else if (position == 3) {
            MyOrderFragment fragment3=new MyOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment3.setArguments(bundle);
            return fragment3;
        } else if (position == 4) {
            MyOrderFragment fragment4=new MyOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",position);
            fragment4.setArguments(bundle);
            return fragment4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}