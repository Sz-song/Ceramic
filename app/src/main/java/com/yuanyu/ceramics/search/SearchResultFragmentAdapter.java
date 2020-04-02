package com.yuanyu.ceramics.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchResultFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"作品","大师","店铺"};
    private String query;
    private SearchItemFragment fragment_item = new SearchItemFragment() ;
    private SearchMasterFragment fragment_dashi = new SearchMasterFragment();
    private SearchShopFragment fragment_shop = new SearchShopFragment();
    private Bundle bundlemaster = new Bundle();
    SearchResultFragmentAdapter(FragmentManager fm, String query){
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.query=query;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            Bundle bundle = new Bundle();
            bundle.putString("query" ,query);
            fragment_item.setArguments(bundle);
            return fragment_item;
        }else if (position==1){
            Bundle bundle = new Bundle();
            bundle.putString("query" ,query);
            fragment_dashi.setArguments(bundle);
            return fragment_dashi;
        }else if(position == 2){
            bundlemaster.putString("query" ,query);
            fragment_shop.setArguments(bundlemaster);
            return fragment_shop;
        }
        return new SearchItemFragment();
    }

    @Override
    public int getCount() {return mTitles.length;}

    public CharSequence getPageTitle(int position){return mTitles[position];}

    public void query(String str){
        bundlemaster.putString("query" ,str);
        fragment_shop.setArguments(bundlemaster);
        fragment_item.Search(str);
        fragment_dashi.Search(str);
        fragment_shop.Search(str);
    }
}
