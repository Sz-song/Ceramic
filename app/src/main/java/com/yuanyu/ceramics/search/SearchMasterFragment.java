package com.yuanyu.ceramics.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.WrapContentLinearLayoutManager;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

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
    private int page;
    private String query;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }
    @Override
    protected SearchMasterPresenter initPresent() {return new SearchMasterPresenter();}

    @Override
    public void initEvent(View view) {
        list = new ArrayList<>();
        page = 0;
        query = getArguments().getString("query");
        adapter = new SearchMasterAdapter(list, getContext());
        swipe = view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((WrapContentLinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && lastPosition > 8) {
                        presenter.SearchMasterList(Sp.getString(getContext(), "useraccountid"),page,query);
                    }
                }
            }
        });
        adapter.setFocusClickListener(position -> presenter.Focus(Sp.getString(getContext(), "useraccountid"),list.get(position).getUseraccountid()+"",position));
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.SearchMasterList(Sp.getString(getContext(), "useraccountid"),page,query);
        });

    }

    @Override
    protected void initData() {
        if(query.length()>0){
            presenter.SearchMasterList(Sp.getString(getContext(), "useraccountid"),page,query);
        }
    }

    @Override
    public void SearchMasterSuccess(List<SearchMasterBean> beans) {
        list.addAll(beans);
        if (isAlive) {
            L.e("123");
            adapter.notifyItemRangeInserted(list.size() - beans.size(), beans.size());
            swipe.setRefreshing(false);
            page++;
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void SearchMasterFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        if (isAlive) {
            swipe.setRefreshing(false);
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void focusSuccess(Boolean isfocus, int position) {
        if (isfocus) {
            list.get(position).setIsfollowed(true);
        } else {
            list.get(position).setIsfollowed(false);
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void focusFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Search(String query) {
        this.query=query;
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        presenter.SearchMasterList(Sp.getString(getContext(), "useraccountid"),page,query);
    }
}
