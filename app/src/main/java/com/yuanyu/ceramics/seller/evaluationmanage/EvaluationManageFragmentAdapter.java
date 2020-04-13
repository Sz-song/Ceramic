package com.yuanyu.ceramics.seller.evaluationmanage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018-08-22.
 */

public class EvaluationManageFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles=new String[]{"好评","中评","差评"};

    public EvaluationManageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            EvaluationManageFragment fragment1=new EvaluationManageFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            fragment1.setArguments(bundle);
            return fragment1;
        }
        if (position==1){
            EvaluationManageFragment fragment2=new EvaluationManageFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",2);
            fragment2.setArguments(bundle);
            return fragment2;
        }
        if (position==2){
            EvaluationManageFragment fragment3=new EvaluationManageFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",3);
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
