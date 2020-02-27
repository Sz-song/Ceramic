package com.yuanyu.ceramics.search;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.his_delete)
    TextView hisDelete;
    @BindView(R.id.recyclerView_sousuotuijian)
    RecyclerView recyclerViewSousuotuijian;

    private SearchView.SearchAutoComplete searchEdit;
    private String str;
    private List<String> history = new ArrayList<>();
    private List<String> history_faxian = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void initEvent() {

    }

}
