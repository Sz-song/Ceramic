package com.yuanyu.ceramics.home.homepage;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.BannerBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FaxianFragment extends BaseFragment<FaxianPresenter> implements FaxianConstract.IFaxianView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private FaxianAdapter adapter;
    private List<BannerBean> bannerList;
    private List<FaxianItemBean> list;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected FaxianPresenter initPresent() {
        return new FaxianPresenter();
    }

    @Override
    protected void initEvent(View view) {
        bannerList = new ArrayList<>();
        list = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else return 1;
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        WindowManager wm = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, this.getResources().getDisplayMetrics());
        int width = (screenWidth - margin * 6) / 3;
        adapter = new FaxianAdapter(getContext(), bannerList, list, width, margin);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
            bannerList.clear();
            list.clear();
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void initDataSuccess(FaxianBean bean) {
        swipe.setRefreshing(false);
        bannerList.addAll(bean.getBannerList());
        list.addAll(bean.getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        L.e(e.message + " " + e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

}
