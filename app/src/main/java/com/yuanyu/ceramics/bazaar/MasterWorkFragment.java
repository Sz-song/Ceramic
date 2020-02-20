package com.yuanyu.ceramics.bazaar;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.CommonDecoration;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MasterWorkFragment extends BaseFragment<MasterWorkPresenter> implements MasterWorkConstract.IMasterWorkView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private int page;
    private List<MasterWorkBean> list;
    private MasterWorkAdapter adapter;
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected MasterWorkPresenter initPresent() {
        return new MasterWorkPresenter();
    }

    @Override
    public void initEvent(View view) {
        page=0;
        list=new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        recyclerview.setBackgroundColor(getActivity().getResources().getColor(R.color.background_gray));
        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        recyclerview.setLayoutManager(manager);
        adapter = new MasterWorkAdapter(getContext(),list);
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new CommonDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8,this.getResources().getDisplayMetrics())));
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getMasterWorkList(page);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case 2:
                        if(isAlive) {
                            Glide.with(getContext()).pauseRequests();}
                        break;
                    case 1:
                    case 0:
                        if(isAlive) {
                            Glide.with(getContext()).resumeRequests();}
                        break;
                }
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>8) {
                        presenter.getMasterWorkList(page);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        presenter.getMasterWorkList(page);
    }

    @OnClick(R.id.nodata_img)
    public void onViewClicked() {
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        presenter.getMasterWorkList(page);
    }

    @Override
    public void getMasterWorkSuccess(List<MasterWorkBean> beans) {
        list.addAll(beans);
        if(isAlive){
            adapter.notifyItemRangeInserted(list.size() - beans.size(),beans.size());
            swipe.setRefreshing(false);
            page++;
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            }else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getMasterWorkFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        if(isAlive) {
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
}