package com.yuanyu.ceramics.mycoins;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyConinsFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"领取金豆", "兑换金豆"};
    private int type;
    private GetCoinsFragment getCoinsFragment;
    private ExchangeCoinsFragment exchangeCoinsFragment;
    MyConinsFragmentAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type = type;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            getCoinsFragment = new GetCoinsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);//大厅
            getCoinsFragment.setArguments(bundle);
            return getCoinsFragment;
        } else if (position == 1) {
            exchangeCoinsFragment = new ExchangeCoinsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);//申请成功
            exchangeCoinsFragment.setArguments(bundle);
            return exchangeCoinsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    public CharSequence getPageTitle(int position) {return mTitles[position];}

    public void reflash(){
        getCoinsFragment.reflash();
        exchangeCoinsFragment.reflash();
    }
}
