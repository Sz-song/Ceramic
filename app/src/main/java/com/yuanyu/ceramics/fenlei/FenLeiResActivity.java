package com.yuanyu.ceramics.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FenLeiResActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.result_recycle)
    RecyclerView resultRecycle;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.bottom_recycle)
    RecyclerView bottomRecycle;
    private ResultAdapter adapter;
    private BottomResultAdapter bottomResultAdapter;
    private List<String> bottomList = new ArrayList<>();
    private String[] strings = new String[5];
//    private FenleiModel model = new FenleiModel();
    private List<FenLeiResBean> resultList = new ArrayList<>();
    private int page = 0;
    private int page_size=10;


    @Override
    protected int getLayout() {
        return R.layout.activity_fen_lei_res;
    }

    @Override
    protected BasePresenter initPresent() {
        return new FenLeiPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("搜索结果");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        swiperefresh.setColorSchemeResources(R.color.colorPrimary);
        swiperefresh.setOnRefreshListener(() -> {
            page = 0;
            resultList.clear();
            adapter.notifyDataSetChanged();
            initResultList();
        });
        resultRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    }

                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        initResultList();
                    }
                }
            }
        });
        Intent intent = getIntent();
        String selectStr = intent.getStringExtra("querycode");//获取选择项
        String strone = intent.getStringExtra("codeone");
        strings[0] = strone;
        String strtwo = intent.getStringExtra("codetwo");
        strings[1] = strtwo;
        String strthree = intent.getStringExtra("codethree");
        strings[2] = strthree;
        String[] listStr = selectStr.split("/");
        for (int i = 0; i < listStr.length; i++) {
            if (!listStr[i].equals("")) {
                bottomList.add(listStr[i]);
            }
        }
        initResultList();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        resultRecycle.setLayoutManager(layoutManager);
//        FenLeiResBean rs1=new FenLeiResBean('1','n1','1',2000,'1','1');
        adapter = new ResultAdapter(resultList);
        resultRecycle.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bottomRecycle.setLayoutManager(linearLayoutManager);
        bottomResultAdapter = new BottomResultAdapter(bottomList);
        bottomRecycle.setAdapter(bottomResultAdapter);
    }

    private void initResultList() {

//        model.getFenleiRusult(strings, page, page_size)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<List<ResultBean>>())
//                .subscribe(new BaseObserver<List<ResultBean>>() {
//                    @Override
//                    public void onNext(List<ResultBean> list) {
//                        int count = (resultList.size());
//                        for (int i = 0; i < list.size(); i++) {
//                            resultList.add(list.get(i));
//                        }
//                        if(list.size()==0){
//                            Toast.makeText(FenleiResultActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
//                        }
//                        adapter.notifyItemRangeInserted(count, list.size());
//                        page++;
//                        swiperefresh.setRefreshing(false);
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        swiperefresh.setRefreshing(false);
//                        Toast.makeText(FenleiResultActivity.this, e.message, Toast.LENGTH_SHORT).show();
//                    }
//                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
