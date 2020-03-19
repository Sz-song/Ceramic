package com.yuanyu.ceramics.center_circle;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.center_circle.release.ReleasePopWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class CenterCircleFragment extends Fragment {

    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.torelease)
    CircleImageView torelease;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center_circle, container, false);
        ButterKnife.bind(this, view);
        CenterCircleFragmentAdapter adapter = new CenterCircleFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @OnClick(R.id.torelease)
    public void onViewClicked() {
        ReleasePopWindow popupWindow = new ReleasePopWindow(getActivity());
        popupWindow.showAtLocation(mainContent, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 1f; // 0.0-1.0
            getActivity().getWindow().setAttributes(lp);
        });
    }
}
