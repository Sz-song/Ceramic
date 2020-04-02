package com.yuanyu.ceramics.search;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.db.SearchHistoryBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchActivityPresenter> implements SearchActivityConstract.ISearchView {


    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.home_search)
    LinearLayout homeSearch;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.delete_history)
    ImageView deleteHistory;
    @BindView(R.id.recyclerview_history)
    RecyclerView recyclerviewHistory;
    @BindView(R.id.recyclerview_hot)
    RecyclerView recyclerviewHot;
    private List<String> historyList;
    private List<String> hotList;
    private SearchActivityAdapter historyAdapter;
    private SearchActivityAdapter hotAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_search2;
    }

    @Override
    protected SearchActivityPresenter initPresent() {
        return new SearchActivityPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        historyList=new ArrayList<>();
        hotList=new ArrayList<>();
        historyAdapter=new SearchActivityAdapter(this,historyList);
        hotAdapter=new SearchActivityAdapter(this,hotList);
        recyclerviewHistory.setLayoutManager(new FlowLayoutManager(this,true));
        recyclerviewHot.setLayoutManager(new FlowLayoutManager(this,true));
        recyclerviewHistory.setAdapter(historyAdapter);
        recyclerviewHot.setAdapter(hotAdapter);
        presenter.getSearchHotList(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),historyList);
        searchEdit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                if (searchEdit.getText().toString().trim().length() == 0) {
                    searchEdit.setText(searchEdit.getHint().toString().trim());
                }
                View view =getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(view!=null&&inputMethodManager!=null){
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                Intent intent=new Intent(this,SearchResultActivity.class);
                intent.putExtra("query",searchEdit.getText().toString().trim());
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    public void getSearchHotListSuccess(List<String> strings) {
        hotList.addAll(strings);
        hotAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSearchHotListFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status+"  "+e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.cancel, R.id.delete_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.delete_history:
                LitePal.deleteAll(SearchHistoryBean.class,"type =?","0");
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        historyList.clear();
        List<SearchHistoryBean> searchHistoryBeans= LitePal.where("type=?","0").limit(10).order("id desc").find(SearchHistoryBean.class);
        for(int i=0;i<searchHistoryBeans.size();i++){
            historyList.add(searchHistoryBeans.get(i).getHistory());
        }
        historyAdapter.notifyDataSetChanged();
    }
}
