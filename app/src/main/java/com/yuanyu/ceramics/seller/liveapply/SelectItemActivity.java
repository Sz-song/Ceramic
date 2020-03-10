package com.yuanyu.ceramics.seller.liveapply;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.seller.liveapply.bean.SelectItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class SelectItemActivity extends BaseActivity<SelectItemPresenter> implements SelectItemConstract.ISelectItemView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_amount)
    TextView amount;
    @BindView(R.id.shop_image)
    RoundedImageView shopImage;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.divider)
    View divider;

    private final int RESULT_CODE = 202;
    private List<ItemBean> mData;
    private SelectItemAdapter adapter;
    private List<ItemBean> mList;
    private int page = 0;
    private boolean enable;
    @Override
    protected int getLayout() {
        return R.layout.activity_select_item;
    }

    @Override
    protected SelectItemPresenter initPresent() {
        return new SelectItemPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("店铺商品");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mData = (List<ItemBean>) bundle.getSerializable("data");
        mList = new ArrayList<>();
        mList.addAll(mData);
        adapter = new SelectItemAdapter(this, mList, 1, false);
        adapter.setListener((holder, position) -> {
            if (holder instanceof SelectItemAdapter.ViewHolder1) {
                mList.get(position).setChecked(!mList.get(position).isChecked());
                ((SelectItemAdapter.ViewHolder1) holder).checkbox.setChecked(mList.get(position).isChecked());
                adapter.notifyItemChanged(position);
            }
        });
        enable = false;
        recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(lm);
        presenter.getSelectItem(Sp.getString(this, AppConstant.SHOP_ID), mData, page);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 7 && enable) {
                        enable = false;
                        presenter.getSelectItem(Sp.getString(SelectItemActivity.this, AppConstant.SHOP_ID), mData, page);
                    }
                }
            }
        });
    }

    @OnClick(R.id.submit)
    void onClick(View view) {
        Intent intent = new Intent(this, LiveApplyActivity.class);
        Bundle bundle = new Bundle();
        List<ItemBean> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChecked()) {
                list.add(mList.get(i));
            }
        }
        bundle.putSerializable("data", (Serializable) list);
        intent.putExtras(bundle);
        setResult(RESULT_CODE, intent);
        finish();
    }
    @Override
    public void getSelectItemSuccess(SelectItemBean selectItemBean) {
        shopName.setText(selectItemBean.getShop());
        GlideApp.with(SelectItemActivity.this)
                .load(BASE_URL + selectItemBean.getImage())
                .into(shopImage);
        mList.addAll(selectItemBean.getList());
        adapter.notifyDataSetChanged();
        amount.setText("共" + selectItemBean.getItem_num() + "件商品");
        page++;
        enable=true;
    }

    @Override
    public void getSelectItemFail(ExceptionHandler.ResponeThrowable e) {
        enable=true;
        L.e(e.message+""+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
