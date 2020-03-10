package com.yuanyu.ceramics.seller.shop_shelve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yuanyu.ceramics.seller.shop_shelve.shelve_audit.ShelveAuditFragment;
import com.yuanyu.ceramics.seller.shop_shelve.shelving.ShelvingFragment;

/**
 * Created by cat on 2018/8/17.
 */

public class ShelveFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"上架申请","审核中","审核成功","审核失败"};
    public ShelveFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new ShelvingFragment();
        }
        else if (position==1){
            ShelveAuditFragment fragment1=new ShelveAuditFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",0);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        else if (position==2){
            ShelveAuditFragment fragment2=new ShelveAuditFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        else if (position==3){
            ShelveAuditFragment fragment3=new ShelveAuditFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",2);
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