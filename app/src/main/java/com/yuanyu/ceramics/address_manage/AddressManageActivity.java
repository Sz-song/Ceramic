package com.yuanyu.ceramics.address_manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManageActivity extends BaseActivity<AddressManagePresenter> implements AddressManageConstract.IAddressManageView{

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

    @Override
    public void getAddressDataSuccess(List<AddressManageBean> beans) {
        loaddialog.dismiss();
        swipe.setRefreshing(false);
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getIsdefault() == 1) {
                list.add(0,beans.get(i));
            } else {
                list.add(beans.get(i));
            }
        }
        adapter.notifyItemRangeChanged(list.size() -beans.size(), beans.size());
        if(list.size()>0){
            nodata.setVisibility(View.GONE);
            nodataImg.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.VISIBLE);
            nodataImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getAddressDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        loaddialog.dismiss();
        swipe.setRefreshing(false);
        if(list.size()>0){
            nodata.setVisibility(View.GONE);
            nodataImg.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.VISIBLE);
            nodataImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteAddressSuccess(int position) {
        loaddialog.dismiss();
        if(list.get(position).getIsdefault()==1){
            list.remove(position);
            if(list.size()>0){
                list.get(0).setIsdefault(1);
            }
        }else{
            list.remove(position);
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        if(list.size()>0){
            nodata.setVisibility(View.GONE);
            nodataImg.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.VISIBLE);
            nodataImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteAddressFail(ExceptionHandler.ResponeThrowable e) {
        loaddialog.dismiss();
        Toast.makeText(this, "删除地址失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDefaultAddressSuccess(int position) {
        for(int i=0;i<list.size();i++){
            list.get(i).setIsdefault(0);
        }
        list.get(position).setIsdefault(1);
        loaddialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDefaultAddressFail(ExceptionHandler.ResponeThrowable e) {
        loaddialog.dismiss();
        Toast.makeText(this, "设置默认地址失败", Toast.LENGTH_SHORT).show();
        L.e(e.message+"  "+e.status);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                loaddialog.show();
                swipe.setRefreshing(true);
                list.clear();
                presenter.getAddressData(Sp.getString(this, "useraccountid"));
                adapter.notifyDataSetChanged();
            }
    }
}
