package com.yuanyu.ceramics.seller.message_shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.message.MessageAdapter;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private List<MessageBean> list;
    private MessageAdapter adapter;
    private boolean isInit=false;

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
        ButterKnife.bind(this);
        title.setText("消息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        swipe.setRefreshing(true);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂无数据");
        adapter = new MessageAdapter(this, list);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> presenter.initData());
        presenter.initData();
    }

    @Override
    public void initDataSuccess(List<MessageBean> beans) {
        if(!(list.size()==beans.size()&&list.containsAll(beans))){
            list.clear();
            list.addAll(beans);
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
        }
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        }else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + "  " + e.message);
        swipe.setRefreshing(false);
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        }else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void receiveMessageSuccess(String msg, String sender) {
        presenter.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isInit){
            presenter.initData();
        }
        isInit=true;
    }
}
