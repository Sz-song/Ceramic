package com.yuanyu.ceramics.home;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.broadcast.BroadcastFragment;
import com.yuanyu.ceramics.home.homepage.HomepageFragment;
import com.yuanyu.ceramics.master.MasterFragment;
import com.yuanyu.ceramics.message.MessageFragment;
import com.yuanyu.ceramics.mine.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.pic_homepage)
    ImageView picHomepage;
    @BindView(R.id.navigation_homepage)
    RelativeLayout navigationHomepage;
    @BindView(R.id.pic_broadcast)
    ImageView picBroadcast;
    @BindView(R.id.navigation_broadcast)
    RelativeLayout navigationBroadcast;
    @BindView(R.id.pic_yuba)
    ImageView picYuba;
    @BindView(R.id.navigation_yuba)
    RelativeLayout navigationYuba;
    @BindView(R.id.pic_message)
    ImageView picMessage;
    @BindView(R.id.navigation_message)
    RelativeLayout navigationMessage;
    @BindView(R.id.pic_wujia)
    ImageView picWujia;
    @BindView(R.id.navigation_wujia)
    RelativeLayout navigationWujia;
    private HomepageFragment homepageFragment;
    private BroadcastFragment broadcastFragment;
    private MasterFragment masterFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homepageFragment = new HomepageFragment();
        fragmentTransaction.add(R.id.fragment_container, homepageFragment).commit();
        setPicDark();
        picHomepage.setImageResource(R.drawable.navigation_homepage_focus);
    }

    private void setPicDark() {
        picHomepage.setImageResource(R.drawable.navigation_homepage);
        picBroadcast.setImageResource(R.drawable.navigation_zhibo);
        picYuba.setImageResource(R.drawable.navigation_yuba);
        picMessage.setImageResource(R.drawable.navigation_message);
        picWujia.setImageResource(R.drawable.navigation_wujia);
    }

    private void changeFragment(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (homepageFragment != null && homepageFragment.isVisible()) {
            fragmentTransaction.hide(homepageFragment);
        }
        if (broadcastFragment != null && broadcastFragment.isVisible()) {
            fragmentTransaction.hide(broadcastFragment);
        }
        if (masterFragment != null && masterFragment.isVisible()) {
            fragmentTransaction.hide(masterFragment);
        }
        if (messageFragment != null && messageFragment.isVisible()) {
            fragmentTransaction.hide(messageFragment);
        }
        if (mineFragment != null && mineFragment.isVisible()) {
            fragmentTransaction.hide(mineFragment);
        }

        switch (view.getId()) {
            case R.id.navigation_homepage:
                setPicDark();
                picHomepage.setImageResource(R.drawable.navigation_homepage_focus);
                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                    fragmentTransaction.add(R.id.fragment_container, homepageFragment);
                } else fragmentTransaction.show(homepageFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_broadcast:
                setPicDark();
                picBroadcast.setImageResource(R.drawable.navigation_zhibo_focus);
                if (broadcastFragment == null) {
                    broadcastFragment = new BroadcastFragment();
                    fragmentTransaction.add(R.id.fragment_container, broadcastFragment);
                } else fragmentTransaction.show(broadcastFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_yuba:
                setPicDark();
                picYuba.setImageResource(R.drawable.navigation_yuba_focus);
                if (masterFragment == null) {
                    masterFragment = new MasterFragment();
                    fragmentTransaction.add(R.id.fragment_container, masterFragment);
                } else fragmentTransaction.show(masterFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_message:
                setPicDark();
                picMessage.setImageResource(R.drawable.navigation_message_focus);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.fragment_container, messageFragment);
                } else fragmentTransaction.show(messageFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_wujia:
                setPicDark();
                picWujia.setImageResource(R.drawable.navigation_wujia_focus);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fragment_container, mineFragment);
                } else fragmentTransaction.show(mineFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @OnClick({R.id.navigation_homepage, R.id.navigation_broadcast, R.id.navigation_yuba, R.id.navigation_message, R.id.navigation_wujia})
    public void onViewClicked(View view) {
        changeFragment(view);
    }
}

