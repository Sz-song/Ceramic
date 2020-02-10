package com.yuanyu.ceramics.home.homepage;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.ItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomepageFragment extends BaseFragment<HomepagePresenter> implements HomepageConstract.IHomepageView {
    @BindView(R.id.home_fenlei)
    ImageView homeFenlei;
    @BindView(R.id.home_search)
    LinearLayout homeSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private HomepageAdapter adapter;
    private List<String> bannerList;
    private List<ItemBean> list;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected HomepagePresenter initPresent() {
        return new HomepagePresenter();
    }

    @Override
    protected void initEvent(View view) {
        bannerList=new ArrayList<>();
        list=new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0) { return 2; }
                else return 1;
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter=new HomepageAdapter(getContext(),bannerList,list);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @OnClick({R.id.home_fenlei, R.id.home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_fenlei:
                break;
            case R.id.home_search:
                break;
        }
    }

    @Override
    public void initDataSuccess(HomepageBean bean) {
        bannerList.addAll(bean.getBannerList());
        list.addAll(bean.getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+" "+e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }
}
