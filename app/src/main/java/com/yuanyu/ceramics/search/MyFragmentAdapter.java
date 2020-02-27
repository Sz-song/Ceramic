package com.yuanyu.ceramics.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yuanyu.ceramics.utils.L;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"作品","店铺","大师"};
    private String string;
    private String outsidetype;
    ZuopinFragment fragment = new ZuopinFragment() ;
    SearchMasterFragment fragment_dashi = new SearchMasterFragment();
    ShopFragment fragment_shop = new ShopFragment();

    public MyFragmentAdapter(FragmentManager fm, String s, String i){
        super(fm);
        this.string = s ;
        this.outsidetype = i ;
    }
    public void setFragment(int i, String str){
        string = str;
        switch (i){
            case 0:
                fragment.search(str);
                break;
            case 1:
                fragment_dashi.search(str);
                break;
            case 2:
                fragment_shop.search(str);
                break;
        }
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            Bundle bundle = new Bundle();
            bundle.putString("str" ,string);
            L.e("str is "+string);
            bundle.putString("outtype",outsidetype);
            fragment.setArguments(bundle);
            return fragment;
        }
        else if (position==1){
            Bundle bundle = new Bundle();
            bundle.putString("str",string);
            bundle.putString("outtype",outsidetype);
            fragment_dashi.setArguments(bundle);
            return fragment_dashi;
        }
        else if(position == 2){
            Bundle bundle = new Bundle();
            bundle.putString("str",string);
            bundle.putString("outtype",outsidetype);
            fragment_shop.setArguments(bundle);
            return fragment_shop;
        }


        return new ZuopinFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}
