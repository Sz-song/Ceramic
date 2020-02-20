package com.yuanyu.ceramics.bazaar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BazaarFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"商铺","优选"};
    BazaarFragmentAdapter(FragmentManager fm){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            StoreCenterFragment fragment0=new StoreCenterFragment();
            Bundle bundle=new Bundle();
            fragment0.setArguments(bundle);
            return fragment0;
        }
        else if (position==1){
            MasterWorkFragment fragment1=new MasterWorkFragment();
            Bundle bundle=new Bundle();
            fragment1.setArguments(bundle);
            return fragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }

}
