package com.yuanyu.ceramics.seller.message_shop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageShopActivity extends BaseActivity<MessageShopPresenter> implements MessageShopConstract.IMessageShopView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    protected int getLayout() {
        return R.layout.common_layout_white;
    }

    @Override
    protected MessageShopPresenter initPresent() {
        return new MessageShopPresenter();
    }

    @Override
    protected void initEvent() {

    }
}
