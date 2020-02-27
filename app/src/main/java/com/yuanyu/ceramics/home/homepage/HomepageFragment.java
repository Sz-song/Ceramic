package com.yuanyu.ceramics.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.fenlei.FenLeiActivity;
import com.yuanyu.ceramics.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomepageFragment extends Fragment {


    @BindView(R.id.home_fenlei)
    ImageView homeFenlei;
    @BindView(R.id.home_search)
    LinearLayout homeSearch;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        HomepageFragmentAdapter adapter = new HomepageFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @OnClick({R.id.home_fenlei, R.id.home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_fenlei:
                Intent intent1 = new Intent(getActivity(), FenLeiActivity.class);
                startActivity(intent1);
                break;
            case R.id.home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
