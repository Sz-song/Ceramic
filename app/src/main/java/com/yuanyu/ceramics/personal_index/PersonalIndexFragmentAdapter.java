package com.yuanyu.ceramics.personal_index;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018-08-02.
 */

public class PersonalIndexFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles={"动态","文章"};
    private String userid;
    PersonalIndexFragmentAdapter(FragmentManager fm, String userid){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.userid=userid;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            if (position==0){
                PersonalFragment fragment=new PersonalFragment();
                Bundle bundle=new Bundle();
                bundle.putString("userid",userid);
                bundle.putInt("type",0);
                fragment.setArguments(bundle);
                return fragment;
            }
            else if (position==1){
                PersonalFragment fragment=new PersonalFragment();
                Bundle bundle=new Bundle();
                bundle.putString("userid",userid);
                bundle.putInt("type",1);
                fragment.setArguments(bundle);
                return fragment;
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
