package com.yuanyu.ceramics.shop_index;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShopIndexFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"新品","全部","评论","动态"};
    private String shopid;

    ShopIndexFragmentAdapter(FragmentManager fm, String shopid){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.shopid = shopid;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            ShopGoodsFragment fragment = new ShopGoodsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("shopid",shopid);
            bundle.putInt("type",0);
            fragment.setArguments(bundle);
            return fragment;
        }
        else if (position==1){
            ShopGoodsFragment fragment = new ShopGoodsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("shopid",shopid);
            bundle.putInt("type",1);
            fragment.setArguments(bundle);
            return fragment;
        }else if(position==2){
            ShopPinglunFragment fragment = new ShopPinglunFragment();
            Bundle bundle = new Bundle();
            bundle.putString("shopid",shopid);
            fragment.setArguments(bundle);
            return fragment;
        }
        else if (position==3){
            ShopDynamicFragment fragment = new ShopDynamicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("shopid",shopid);
            fragment.setArguments(bundle);
            return fragment;
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
