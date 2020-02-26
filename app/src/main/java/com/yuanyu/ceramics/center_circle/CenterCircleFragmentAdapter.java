package com.yuanyu.ceramics.center_circle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CenterCircleFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles={"关注","推荐"};
    CenterCircleFragmentAdapter(FragmentManager fm){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            SquareDynamicArticleFragment fragment2=new SquareDynamicArticleFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",0);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        else if (position==1){
            SquareDynamicArticleFragment fragment3=new SquareDynamicArticleFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment3.setArguments(bundle);
            return fragment3;
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
