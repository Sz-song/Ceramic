package com.yuanyu.ceramics.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.xtablayout.XTabLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.his_delete)
    TextView hisDelete;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    private SearchView.SearchAutoComplete searchEdit;
    private String str;
    private List<String> history = new ArrayList<>();
    private List<String> history_faxian = new ArrayList<>();
    private HistoryAdapter adapter;
    private HistoryAdapter adapter1;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_sousuotuijian;
    private StringBuffer sb;
    private FlowLayoutManager flowLayoutManager;
    private FlowLayoutManager flowLayoutManager1;
    private ListView lv;
    private ImageView mCloseButton;
    private List<String> source = new ArrayList<>();
    private List<String> search = new ArrayList<>();
    private int Outsidetype = 1;


    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchMasterPresenter initPresent() {
        return new SearchMasterPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        tablelayout.addTab(tablelayout.newTab().setText("作品"));
        tablelayout.addTab(tablelayout.newTab().setText("店铺"));
        tablelayout.addTab(tablelayout.newTab().setText("大师"));
        source.add("青花瓶子");
        source.add("青花器皿");
//        source.add("白玉");
//        source.add("黄玉");
//        source.add("青白玉");
//        source.add("墨玉");
//        source.add("山料");
//        source.add("籽料");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        flowLayoutManager = new FlowLayoutManager(this, true);
        recyclerView.setLayoutManager(flowLayoutManager);
        recyclerView_sousuotuijian = findViewById(R.id.recyclerView_sousuotuijian);
        flowLayoutManager1 = new FlowLayoutManager(this, true);
        recyclerView_sousuotuijian.setLayoutManager(flowLayoutManager1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
        }

        String str1 = Sp.getString(this, "History", null);//.split(",");
        if (str1 != null) {
            String[] str2 = str1.split(",");
            for (String s : str2) {
                for (int i1 = 0; i1 < 1; i1++) {
                    if (history.size() < 10) {
                        history.add(s);
                        history_faxian.add(s);
                    }
                }
            }
        }
        adapter = new HistoryAdapter(history);
        recyclerView.setAdapter(adapter);
        adapter.setEditClickListener((position, his) -> {
            String text = history.get(position);
            searchEdit.setText(text);
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("ic_search", searchEdit.getText().toString());
            intent.putExtra("outsidetype", Outsidetype + "");
            startActivity(intent);
        });
        adapter1 = new HistoryAdapter(history_faxian);
        recyclerView_sousuotuijian.setAdapter(adapter1);
        lv = findViewById(R.id.lv);
        ArrayAdapter<String> adapter_lv = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, search);
        lv.setAdapter(adapter_lv);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener((adapterView, view, position, id) -> {
            String text = search.get(position);
            searchEdit.setText(text);
            Intent intent = new Intent(adapterView.getContext(), SearchResultActivity.class);
            intent.putExtra("ic_search", searchEdit.getText().toString());
            intent.putExtra("outsidetype", Outsidetype + "");
            startActivity(intent);
        });
        tablelayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                if (tablelayout.getSelectedTabPosition()==0) {
                    Outsidetype = 1;
                } else if (tablelayout.getSelectedTabPosition()==1) {
                    Outsidetype = 2;
                } else if (tablelayout.getSelectedTabPosition()==2) {
                    Outsidetype = 3;
                }
            }
            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {}
            @Override
            public void onTabReselected(XTabLayout.Tab tab) {}
        });
    }

    private void initHistory() {
        str = searchEdit.getText().toString().trim();
        if (str.length() > 0) {
            String history1 = str;
            history.add(history1);
            history_faxian.add(history1);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showToast(this, "请输入要查询的内容");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result_menu, menu);
        searchview = findViewById(R.id.searchview);
        searchEdit = searchview.findViewById(R.id.search_src_text);
        searchEdit.setTextColor(getResources().getColor(R.color.blackLight));
        searchEdit.setHint("青花瓷");
        searchEdit.setHintTextColor(getResources().getColor(R.color.gray));
        searchEdit.setTextSize(13);
        searchEdit.setBackground(null);
        mCloseButton = searchview.findViewById(R.id.search_close_btn);
        mCloseButton.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,this.getResources().getDisplayMetrics() ),(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6,this.getResources().getDisplayMetrics() ),(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 0,this.getResources().getDisplayMetrics() ),(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6,this.getResources().getDisplayMetrics() ));
        mCloseButton.setImageDrawable(getResources().getDrawable(R.drawable.delete_search));
        searchview.setIconified(false);             //搜索（放大镜）图标隐藏
        searchview.onActionViewExpanded();          //初始时默认展开
        searchview.setSubmitButtonEnabled(false);   //提交按钮隐藏
        searchview.clearFocus();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                saveHis();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv.setVisibility(View.GONE);
                    L.e("gone");
                    return false;
                } else {
                    lv.setVisibility(View.VISIBLE);
                    search.clear();
                    for (int i = 0; i < source.size(); i++) {
                        if (source.get(i).contains(newText)) {
                            search.add(source.get(i));
                        }
                    }
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_search:
                saveHis();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    public void saveHis() {
        str = searchEdit.getText().toString().trim();
        boolean add = true;
        String temp = Sp.getString(this, "History", null);
        if (temp != null) {
            String[] temp1 = temp.split(",");
            for (String s : temp1) {
                if (str.equals(s)) {
                    add = false;
                    break;
                }
            }
        }
        //如果history长度大于10，把最开始加入的搜索记录删除
        if (str.length() > 0) {
            if (add) {
                initHistory();
                if (history.size() > 10) {
                    history.remove(0);
                }
                String sp1 = Sp.getString(this, "History");
                sb = new StringBuffer();
                sb.append(str).append(",").append(sp1);
                Sp.putString(this, "History", sb.toString());
            }
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("ic_search", searchEdit.getText().toString());
            intent.putExtra("outsidetype", Outsidetype + "");
            startActivity(intent);
        } else {
            ToastUtils.showToast(this, "请输入要查询的内容");
        }
    }

    @OnClick({R.id.his_delete})
    public void OnClick(View view) {
        if (view.getId() == R.id.his_delete) {
            history.clear();
            Sp.putString(this, "History", null);
            adapter.notifyDataSetChanged();
            ToastUtils.showToast(this, "清除历史记录成功");
        }
    }

}
