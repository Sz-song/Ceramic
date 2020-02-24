package com.yuanyu.ceramics.dingzhi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class MyDingzhiFragment extends BaseFragment<MyDingzhiPresenter> implements MyDingzhiConstract.IMyDingzhiView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.frame)
    FrameLayout frame;
    private int status;
    private int page;
    private MyDingzhiAdapter adapter;
    private List<MyDingzhiBean> list;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    public void initData() {
        presenter.getMyDingzhi(page, status, Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    protected MyDingzhiPresenter initPresent() {
        return new MyDingzhiPresenter();
    }

    @Override
    public void initEvent(View view) {
        if (getArguments() != null) {
            status = getArguments().getInt("type");
        }
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        list = new ArrayList<>();
        page = 0;
        frame.setBackgroundColor(getResources().getColor(R.color.background_gray));
        adapter = new MyDingzhiAdapter(getContext(), list,0);
        recyclerview.setAdapter(adapter);
        swipe.setRefreshing(false);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getMyDingzhi(page, status, Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
        });
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(lm);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>7) {
                        presenter.getMyDingzhi(page, status, Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
                    }
                }
            }
        });
    }


    @Override
    public void getMyDingzhiSuccess(List<MyDingzhiBean> beans) {
        if (isAlive) {
            page++;
            list.addAll(beans);
            adapter.notifyItemRangeInserted(list.size() - beans.size(), list.size());
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
    public void getMyDingzhiFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }
}
