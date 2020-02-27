package com.yuanyu.ceramics.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SearchMasterFragment extends BaseFragment<SearchMasterPresenter> implements SearchMasterConstract.ISearchMasterView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<SearchMasterBean> list;
    private SearchMasterAdapter adapter;
    private String keyword;
    private int page;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {return inflater.inflate(R.layout.common_fragment, container, false); }
    @Override
    protected SearchMasterPresenter initPresent() {return new SearchMasterPresenter();}

    @Override
    public void initEvent(View view) {
        list = new ArrayList<>();
        page = 0;
        keyword = getArguments().getString("str");
        swipe = view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        SearchMasterBean sb1=new SearchMasterBean("img/banner1.jpg","黄建宏","1","3","2","2",0);
        SearchMasterBean sb2=new SearchMasterBean("img/banner1.jpg","王锡良","1","3","2","2",0);
        list.add(sb1);
        list.add(sb2);
        adapter = new SearchMasterAdapter(list, getContext());
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        presenter.SearchMasterList(page, Sp.getInt(getContext(), "useraccountid"),2,keyword,2);
                    }
                }
            }
        });
        adapter.setFocusClickListener(position -> presenter.Focus(Sp.getInt(getContext(), "useraccountid"),list.get(position).getId(),position));
        swipe.setOnRefreshListener(() -> {
//            page = 0;
//            list.clear();
//            adapter.notifyDataSetChanged();
//            presenter.SearchMasterList(page,Sp.getInt(getContext(), "useraccountid"),2,keyword,2);
        });

    }

    @Override
    public void initData() {
//        presenter.SearchMasterList(page,Sp.getInt(getContext(), "useraccountid"),2,keyword,2);
    }

    public void search(String str) {
        keyword = str;
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
//        presenter.SearchMasterList(page,Sp.getInt(getContext(), "useraccountid"),2,keyword,2);
    }

//    @Override
//    public void SearchMasterSuccess(SearchBean bean) {
//        list.addAll(bean.getGuanzhuDashiBean());
//        if (isAlive) {
//            adapter.notifyItemRangeInserted(list.size() - bean.getGuanzhuDashiBean().size(), bean.getGuanzhuDashiBean().size());
//            swipe.setRefreshing(false);
//            page++;
//            if (list.size() == 0) {
//                nodataImg.setVisibility(View.VISIBLE);
//                nodata.setVisibility(View.VISIBLE);
//            } else {
//                nodataImg.setVisibility(View.GONE);
//                nodata.setVisibility(View.GONE);
//            }
//        }
//    }

//    @Override
//    public void SearchMasterFail(ExceptionHandler.ResponeThrowable e) {
//        L.e(e.message + "  " + e.status);
//        if (isAlive) {
//            swipe.setRefreshing(false);
//            if (list.size() == 0) {
//                nodataImg.setVisibility(View.VISIBLE);
//                nodata.setVisibility(View.VISIBLE);
//            } else {
//                nodataImg.setVisibility(View.GONE);
//                nodata.setVisibility(View.GONE);
//            }
//        }
//    }

//    @Override
//    public void focusSuccess(Boolean isfocus, int position) {
//        if (isfocus) {
//            list.get(position).setFollowed(1);
//        } else {
//            list.get(position).setFollowed(0);
//        }
//        adapter.notifyItemChanged(position);
//    }
//
//    @Override
//    public void focusFail(ExceptionHandler.ResponeThrowable e) {
//        L.e("error is " + e.status + e.message);
//        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
//    }
}
