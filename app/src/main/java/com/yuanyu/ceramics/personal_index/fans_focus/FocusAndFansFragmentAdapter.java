package com.yuanyu.ceramics.personal_index.fans_focus;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FocusAndFansFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private String userid;
    FocusAndFansFragmentAdapter(FragmentManager fm, String[] mTitles, String userid){
        super(fm);
        this.mTitles=mTitles;
        this.userid=userid;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            FocusAndFansFragment fragment1=new FocusAndFansFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("focustype",0);
            bundle.putString("userid",userid);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position==1){
            FocusAndFansFragment fragment2=new FocusAndFansFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("focustype",1);
            bundle.putString("userid",userid);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        else if (position==2){
            FocusAndFansFragment fragment3=new FocusAndFansFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("focustype",2);
            bundle.putString("userid",userid);
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