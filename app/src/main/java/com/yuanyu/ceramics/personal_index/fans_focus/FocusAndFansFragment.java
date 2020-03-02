package com.yuanyu.ceramics.personal_index.fans_focus;

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
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


public class FocusAndFansFragment extends BaseFragment <FocusAndFansPresenter> implements FocusAndFansConstract.IFocusAndFansView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private FocusAndFansAdapter adapter;
    List<FocusAndFansBean> list;
    private int focustype;
    private String userid;
    private int page = 0;
    private LoadingDialog dialog;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected FocusAndFansPresenter initPresent() {
        return new FocusAndFansPresenter();
    }

    @Override
    public void initEvent(View view) {
        list = new ArrayList<>();
        dialog=new LoadingDialog(getContext());
        focustype = getArguments().getInt("focustype");
        userid = getArguments().getString("userid");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(manager);
        adapter = new FocusAndFansAdapter(getContext(), list);
        adapter.setOnFocusClick(position -> {
            dialog.show();
            presenter.focus(Sp.getString(getContext(),"useraccountid"), list.get(position).getId(),position);
        });
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        GlideApp.with(getActivity()).load(R.drawable.nodata_img).override(100,100).into(nodataImg);
        nodata.setText("暂时没有数据");
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getFocusAndFansList(Sp.getString(getActivity(), "useraccountid"), userid, focustype, page);
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
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                        presenter.getFocusAndFansList(Sp.getString(getActivity(), "useraccountid"), userid, focustype, page);
                    }
                }
            }
        });
        nodataImg.setOnClickListener(view1 -> {
            page = 0;
            list.clear();
            swipe.setRefreshing(true);
            adapter.notifyDataSetChanged();
            presenter.getFocusAndFansList(Sp.getString(getActivity(), "useraccountid"), userid, focustype, page);
        });
    }
    @Override
    public void initData() {
        presenter.getFocusAndFansList(Sp.getString(getActivity(), "useraccountid"), userid, focustype, page);
    }
    @Override
    public void getFocusAndFansListSuccess(List<FocusAndFansBean> friendBeanslist) {
        dialog.dismiss();
        list.addAll(friendBeanslist);
        if (isAlive) {
            swipe.setRefreshing(false);
            adapter.notifyItemRangeInserted(list.size() - friendBeanslist.size(), friendBeanslist.size());
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
    public void getFocusAndFansListFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message + e.status);
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
    public void focusSuccess(Boolean b, int position) {
        if (b) {
            Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
            list.get(position).setIsfocus(true);
            list.get(position).setFans_num(list.get(position).getFans_num()+1);
            adapter.notifyDataSetChanged();
        } else{
            Toast.makeText(getContext(), "取关成功", Toast.LENGTH_SHORT).show();
            list.get(position).setIsfocus(false);
            list.get(position).setFans_num(list.get(position).getFans_num()-1);
            adapter.notifyDataSetChanged();
        }
        dialog.dismiss();
    }

    @Override
    public void focusFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + e.message);
        dialog.dismiss();
    }

}
