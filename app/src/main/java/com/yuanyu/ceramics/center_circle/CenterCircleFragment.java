package com.yuanyu.ceramics.center_circle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CenterCircleFragment extends Fragment {

    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_center_circle, container, false);
        ButterKnife.bind(this,view);
        CenterCircleFragmentAdapter adapter = new CenterCircleFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
