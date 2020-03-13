package com.yuanyu.ceramics.address_manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManageActivity extends BaseActivity<AddressManagePresenter> implements AddressManageConstract {

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
    @BindView(R.id.add_address)
    TextView addAddress;

    private AddressManageAdapter adapter;
    private List<AddressManageBean> list;
    private LoadingDialog loaddialog;
    @Override
    protected int getLayout() {
        return R.layout.activity_address_manage;
    }

    @Override
    protected AddressManagePresenter initPresent() {
        return new AddressManagePresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("收货地址");
        list = new ArrayList<>();
        loaddialog = new LoadingDialog(this);
        GlideApp.with(this)
                .load(R.drawable.nodata_img)
                .override(300, 300)
                .into(nodataImg);
        nodata.setText("暂无数据");
        swipe.setColorSchemeResources(R.color.colorPrimary);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        if(list.size()>0){
            nodata.setVisibility(View.GONE);
            nodataImg.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.VISIBLE);
            nodataImg.setVisibility(View.VISIBLE);
        }
        adapter = new AddressManageAdapter(AddressManageActivity.this, list);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            if(getIntent().getStringExtra("finish")!=null){
                Intent intent=new Intent();
                intent.putExtra("addressbean",list.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        adapter.setOnDefaultClickListener(position -> {
            loaddialog.show();
            presenter.setDefaultAddress(Sp.getString(AddressManageActivity.this,"useraccountid"),list.get(position).getAddressid(),position);
        });
        adapter.setOnDeteleClickListener(position -> {
            loaddialog.show();
            presenter.deleteAddress(Sp.getString(AddressManageActivity.this,"useraccountid"),list.get(position).getAddressid(),position);
        });
        adapter.setOnEditClickListener(position -> {
            Intent intent = new Intent(AddressManageActivity.this, AddOrEditAddressActivity.class);
            intent.putExtra("type","1");
            intent.putExtra("addressbean",list.get(position));
            startActivityForResult(intent, 1001);
        });
        swipe.setOnRefreshListener(() -> {
            list.clear();
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
            loaddialog.show();
            presenter.getAddressData(Sp.getString(this, "useraccountid"));
        });
        loaddialog.show();
        swipe.setRefreshing(false);
        presenter.getAddressData(Sp.getString(this, "useraccountid"));
    }



    //返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick({R.id.nodata_img, R.id.nodata, R.id.add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nodata_img:
                list.clear();
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(true);
                loaddialog.show();
                presenter.getAddressData(Sp.getString(this, "useraccountid"));
                break;
            case R.id.nodata:
                list.clear();
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(true);
                loaddialog.show();
                presenter.getAddressData(Sp.getString(this, "useraccountid"));
                break;
            case R.id.add_address:
                Intent intent = new Intent(AddressManageActivity.this, AddOrEditAddressActivity.class);
                intent.putExtra("type","0");
                startActivityForResult(intent, 1002);
                break;
        }
    }
}
