package com.yuanyu.ceramics.search;


import android.content.Context;
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
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cat on 2018/7/26.
 */

public class ShopFragment extends BaseFragment {


    @BindView(R.id.recy_shop)
    RecyclerView recyShop;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private ShopAdapter adapter;
    private List<Shop> shopList;
//    private SearchShopModel model;
    private String keyword;
    private int page = 0;
    private Context context;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void initData() {
        LoadShopData(page,keyword,1);
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable final ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    protected SearchMasterPresenter initPresent() {
        return new SearchMasterPresenter();
    }

    @Override
    public void initEvent(View view) {
        context = this.getContext();
        shopList = new ArrayList<>();
//        model = new SearchShopModel();
        keyword = getArguments().getString("str");
        String i = getArguments().getString("outtype");
        int outsidetype = Integer.parseInt(i);
        L.e("outsidetype  " + outsidetype);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recyShop.setLayoutManager(manager);
        adapter = new ShopAdapter(shopList, getContext());
        recyShop.setAdapter(adapter);
        recyShop.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                        LoadShopData(page,keyword,1);
                    }
                }
            }
        });
        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> {
            page = 0;
            shopList.clear();
            adapter.notifyDataSetChanged();
//            LoadShopData(page,keyword,1);
            ToastUtils.showToast(context, "刷新成功");
            refreshLayout.setRefreshing(false);
        });
    }
    public void search(String str){
        page=0;
        keyword = str;
        LoadShopData(page,keyword,0);
    }

    void LoadShopData(int p, String search, int i) {
        if (i == 0){
            shopList.clear();
            adapter.notifyDataSetChanged();
        }
        Shop sp1=new Shop("img/banner1.jpg","黄建宏工作室","60","7","25","1");
        Shop sp2=new Shop("img/banner1.jpg","王锡良工作室","60","7","25","1");
        shopList.add(sp1);
        shopList.add(sp2);

//        model.getSearchShopResult(p, SpUtils.getInt(context, "useraccountid"), 3, search, 3)
//                .subscribeOn(Schedulers.io())
//                .compose(new HttpServiceInstance.ErrorTransformer<SearchBean>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<SearchBean>() {
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        L.e("error is " + e.status + e.message);
//                        if (e.status == 111){ ToastUtils.showToast(context,"已经到底了哦");}
//                    }
//
//                    @Override
//                    public void onNext(SearchBean bean) {
//                        if (bean.getSearchShopBean().size() > 0) page++;
//                        else ToastUtils.showToast(context, "已经到底了哦");
//                        shopList.addAll(bean.getSearchShopBean());
//                        adapter.notifyItemRangeInserted(shopList.size() - bean.getSearchShopBean().size(), bean.getSearchShopBean().size());
//                    }
//                });
    }
}