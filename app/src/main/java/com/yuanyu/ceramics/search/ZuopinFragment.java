package com.yuanyu.ceramics.search;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.CommonDecoration;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ZuopinFragment extends BaseFragment {
    private List<Zuopin> zuopinList;
    private ZuopinAdapter adapter;
    private SearchZuopinModel model;
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    String keyword;
    int page = 0;

    @Override
    public void initData() {
        LoadZuopinData(page,keyword,1);
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.zuopin_page,container,false);
        mContext = this.getContext();
//        Zuopininit();
        zuopinList = new ArrayList<>();
        model = new SearchZuopinModel();
        keyword = getArguments().getString("str");
        String i =  getArguments().getString("outtype");
        int outsidetype = Integer.parseInt(i);
        L.e("outsidetype  "+ outsidetype);
        L.e("jiushoushuju"+keyword);
        RecyclerView recyclerView = view.findViewById(R.id.zuopin_recy);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ZuopinAdapter(zuopinList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CommonDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8,this.getResources().getDisplayMetrics())));
        swipeRefreshLayout = view.findViewById(R.id.gank_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 0 ;
            zuopinList.clear();
            adapter.notifyDataSetChanged();
            LoadZuopinData(page,keyword,1);
            ToastUtils.showToast(mContext,"刷新");
            swipeRefreshLayout.setRefreshing(false);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
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

                        LoadZuopinData(page,keyword,1);
                    }

                }
            }
        });
        return view;
    }

    @Override
    protected SearchMasterPresenter initPresent() {
        return new SearchMasterPresenter();
    }

    @Override
    public void initEvent(View view) {

    }

    public void search(String str){
        page=0;
        keyword = str;
        LoadZuopinData(page,keyword,0);
    }

    public void LoadZuopinData(int p, String search, int i) {
        if (i == 0){
            zuopinList.clear();
            adapter.notifyDataSetChanged();
        }
        model.getSearchZuopinResult(p, Sp.getString(mContext,"useraccountid"), 1, search, 1)
                .subscribeOn(Schedulers.io())
                .compose(new HttpServiceInstance.ErrorTransformer<SearchBean>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SearchBean>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("error is " + e.status + e.message);
                        if (e.status == 111){
                            ToastUtils.showToast(mContext,"已经到底了哦");
                        }
                    }

                    @Override
                    public void onNext(SearchBean bean) {
                        if (bean.getAdsCellBean().size()>0)page++;
                        else ToastUtils.showToast(mContext,"已经到底了哦");
                        for (int i = 0; i < bean.getAdsCellBean().size(); i++) {
                            zuopinList.add(bean.getAdsCellBean().get(i));
                            L.e(zuopinList.get(i).toString());
                        }

                        adapter.notifyItemRangeInserted(zuopinList.size() - bean.getAdsCellBean().size(), bean.getAdsCellBean().size());
                    }
                });
    }
}