package com.yuanyu.ceramics.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MyFragmentAdapter adapter;
    private SearchView.SearchAutoComplete et;
    private ImageView mCloseButton;
    String getString;
    String outsidetype;
    private String search;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected SearchMasterPresenter initPresent() {
        return new SearchMasterPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
        }
        Intent intent = getIntent();
        getString = intent.getStringExtra("ic_search");
        outsidetype = intent.getStringExtra("outsidetype");
        L.e("getstring " + getString);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), getString, outsidetype);
        viewPager.setAdapter(adapter);
        tablelayout.setupWithViewPager(viewPager);
        int i = Integer.parseInt(outsidetype);
        switch (i) {
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result_menu, menu);
        searchview.setIconified(false);
        searchview.onActionViewExpanded();
        et = searchview.findViewById(R.id.search_src_text);
        et.setTextColor(getResources().getColor(R.color.blackLight));
        et.setHint("请输入搜索内容");
        et.setHintTextColor(getResources().getColor(R.color.gray));
        et.setTextSize(14);
        et.setText(getString);
        et.setBackground(null);
        mCloseButton = searchview.findViewById(R.id.search_close_btn);
        mCloseButton.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,this.getResources().getDisplayMetrics() ),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6,this.getResources().getDisplayMetrics() ),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 0,this.getResources().getDisplayMetrics() ),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6,this.getResources().getDisplayMetrics() ));
        mCloseButton.setImageDrawable(getResources().getDrawable(R.drawable.delete_search));
        searchview.setSubmitButtonEnabled(false);
        searchview.clearFocus();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(SearchResultActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    search = query;
                    Search(search);
                }
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_search:
                if (et.getText().toString().trim().length() > 0) {
                    search = et.getText().toString().trim();
                    Search(search);
                } else {
                    Toast.makeText(SearchResultActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    public void Search(String search) {
        saveHis(search);
        int i = viewPager.getCurrentItem();
        adapter.setFragment(i, search);
    }

    public void saveHis(String search) {
        boolean add = true;
        String temp = Sp.getString(this, "History", null);
        if (temp != null) {
            String temp1[] = temp.split(",");
            for (int i = 0; i < temp1.length; i++) {
                if (search.equals(temp1[i])) {
                    add = false;
                    break;
                }
            }
        }
        if (add) {
            StringBuffer sb = new StringBuffer();
            sb.append(search).append(",").append(temp);
            Sp.putString(this, "History", sb.toString());
        }
    }
}
