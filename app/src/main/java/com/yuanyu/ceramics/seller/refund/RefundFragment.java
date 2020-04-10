package com.yuanyu.ceramics.seller.refund;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by cat on 2018/8/24.
 */

public class RefundFragment extends BaseFragment<RefundPresenter> implements RefundConstract.IRefundView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.frame)
    FrameLayout frame;
    private RefundAdapter adapter;
    private List<RefundBean> list;
    private int page;
    private int type;
    private boolean isInit = false;
    boolean recycleState;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected RefundPresenter initPresent() {
        return new RefundPresenter();
    }

    @Override
    public void initEvent(View view) {
        list = new ArrayList<>();
        page = 0;
        type = getArguments().getInt("type");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new RefundAdapter(list, getContext());
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        frame.setBackgroundColor(getResources().getColor(R.color.background_gray));
        GlideApp.with(getActivity()).load(R.drawable.nodata_img).override(nodataImg.getWidth(), nodataImg.getHeight()).into(nodataImg);
        nodata.setText("暂时没有数据");
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getRefundList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
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
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1 && recycleState) {
                        presenter.getRefundList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recycleState = dy > 0;
            }
        });
    }

    @Override
    public void initData() {
        swipe.setRefreshing(true);
        presenter.getRefundList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
    }

    @Override
    public void getRefundListSuccess(List<RefundBean> listrefundbean) {
        for (int i = 0; i < listrefundbean.size(); i++) {
            list.add(listrefundbean.get(i));
        }
        if (isAlive) {
            adapter.notifyItemRangeInserted(list.size() - listrefundbean.size(), listrefundbean.size());
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
    public void getRefundListFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        if (isAlive) {
            Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        if (isInit) {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(true);
            presenter.getRefundList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
        }
        isInit = true;
    }

}
