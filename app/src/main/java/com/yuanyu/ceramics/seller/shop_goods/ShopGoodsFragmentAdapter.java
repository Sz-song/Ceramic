package com.yuanyu.ceramics.seller.shop_goods;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by cat on 2018/8/15.
 */

public class ShopGoodsFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"已上架","已下架","已售磬"};
    public ShopGoodsFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            ShopGoodsFragment fragment0=new ShopGoodsFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment0.setArguments(bundle);
            return fragment0;
        }else if (position==1){
            ShopGoodsFragment fragment0=new ShopGoodsFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",2);
            fragment0.setArguments(bundle);
            return fragment0;
        }else if (position==2){
            ShopGoodsFragment fragment0=new ShopGoodsFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",3);
            fragment0.setArguments(bundle);
            return fragment0;
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

