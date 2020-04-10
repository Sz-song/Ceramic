package com.yuanyu.ceramics.seller.refund;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RefundFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"进行中","全部"};
    RefundFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            RefundFragment fragment1 =new RefundFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",0);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position==1){
            RefundFragment fragment2 =new RefundFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        return new RefundFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}