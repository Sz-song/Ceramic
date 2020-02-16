package com.yuanyu.ceramics.home.homepage;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.yuanyu.ceramics.broadcast.BroadcastFragment;

public class HomepageFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"发现","直播"};
    public HomepageFragmentAdapter(FragmentManager fm){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0)return new FaxianFragment();
        else if (position==1)return new BroadcastFragment();
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
