package com.yuanyu.ceramics.search;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.db.SearchHistoryBean;
import com.yuanyu.ceramics.utils.L;


import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.home_search)
    LinearLayout homeSearch;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private SearchResultFragmentAdapter adapter;
    private String query;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        query=getIntent().getStringExtra("query");
        adapter = new SearchResultFragmentAdapter(getSupportFragmentManager(),query);
        viewPager.setAdapter(adapter);
        tablelayout.setupWithViewPager(viewPager);
        searchEdit.setText(query);
        sava(query);
        searchEdit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                if (searchEdit.getText().toString().trim().length() == 0) {
                    searchEdit.setText(searchEdit.getHint().toString().trim());
                }
                query=searchEdit.getText().toString().trim();
                adapter.query(query);
                sava(query);
                View view =getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(view!=null&&inputMethodManager!=null){
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
    }
    @OnClick(R.id.cancel)
    public void onViewClicked() {
        finish();
    }

    void sava(String query){//保存历史记录
        L.e("query is:"+query);
        LitePal.deleteAll(SearchHistoryBean.class, "history=? and type=?",query,"0");
        SearchHistoryBean historyBean=new SearchHistoryBean();
        historyBean.setHistory(query);
        historyBean.setType("0");
        historyBean.save();
    }
}
