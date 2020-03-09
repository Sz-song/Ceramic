package com.yuanyu.ceramics.seller.order;

/**
 * Created by cat on 2018/8/15.
 */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShopOrderFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"全部","未支付","未发货","已发货","已收货","已结算","异常订单"};
    ShopOrderFragmentAdapter(FragmentManager fm){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            ShopOrderFragment fragment0=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",0);
            fragment0.setArguments(bundle);
            return fragment0;
        }else if (position==1){
            ShopOrderFragment fragment1=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment1.setArguments(bundle);
            return fragment1;
        }else if (position==2){
            ShopOrderFragment fragment2=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",2);
            fragment2.setArguments(bundle);
            return fragment2;
        }else if (position==3){
            ShopOrderFragment fragment3=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",3);
            fragment3.setArguments(bundle);
            return fragment3;
        }else if (position==4){
            ShopOrderFragment fragment4=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",4);
            fragment4.setArguments(bundle);
            return fragment4;
        }else if (position==5){
            ShopOrderFragment fragment5=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",9);
            fragment5.setArguments(bundle);
            return fragment5;
        }else if (position==6){
            ShopOrderFragment fragment6=new ShopOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",7);
            fragment6.setArguments(bundle);
            return fragment6;
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
