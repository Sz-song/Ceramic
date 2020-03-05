package com.yuanyu.ceramics.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewImageFragmentAdapter extends FragmentPagerAdapter {
    private List<String> list;
    public ViewImageFragmentAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        ViewImageFragment fragment=new ViewImageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",list.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
