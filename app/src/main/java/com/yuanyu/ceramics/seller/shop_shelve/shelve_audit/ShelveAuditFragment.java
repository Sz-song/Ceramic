package com.yuanyu.ceramics.seller.shop_shelve.shelve_audit;

import android.content.Intent;
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
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.WrapContentLinearLayoutManager;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.shop_shelve.re_shelve.ReShelveActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cat on 2018/8/17.
 */

public class ShelveAuditFragment extends BaseFragment<ShelveAuditPresenter> implements ShelveAuditConstract.IShelveAuditView {


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
    private List<ShelveAuditBean> list;
    private ShelveAuditAdapter adapter;
    private int type = 0;
    private int page = 0;
    private LoadingDialog dialog;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected ShelveAuditPresenter initPresent() {
        return new ShelveAuditPresenter();
    }

    @Override
    protected void initEvent(View view) {
        type = getArguments().getInt("type");
        list=new ArrayList<>();
        dialog=new LoadingDialog(getContext());
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        WrapContentLinearLayoutManager manager=new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        adapter = new ShelveAuditAdapter(list,getContext());
        recyclerview.setAdapter(adapter);
        adapter.setOnDeleteClickListener(position -> {
            dialog.show();
            presenter.deleteShelveAudit(Sp.getString(getContext(), AppConstant.SHOP_ID),list.get(position).getCommodityid(),position);
        });
        adapter.setOnReShelveClickListener(position -> {
            Intent intent=new Intent(getActivity(), ReShelveActivity.class);
            intent.putExtra("id",list.get(position).getCommodityid());
            intent.putExtra("failreason",list.get(position).getFailed_massage());
            startActivityForResult(intent,1001);
        });
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getShelveAuditData(Sp.getString(getContext(),AppConstant.SHOP_ID),type,page);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>7) {
                        presenter.getShelveAuditData(Sp.getString(getContext(),AppConstant.SHOP_ID),type,page);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        dialog.show();
        swipe.setRefreshing(true);
        presenter.getShelveAuditData(Sp.getString(getContext(),AppConstant.SHOP_ID),type,page);
    }

    @OnClick({R.id.nodata_img, R.id.nodata})
    public void onViewClicked(View view) {
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        dialog.show();
        swipe.setRefreshing(true);
        presenter.getShelveAuditData(Sp.getString(getContext(),AppConstant.SHOP_ID),type,page);
    }

    @Override
    public void getShelveAuditDataSuccess(List<ShelveAuditBean> beans) {
        dialog.dismiss();
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
    public void deleteShelveAuditSuccess(int position) {
        if(isAlive) {
            dialog.dismiss();
            list.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, list.size() - position);
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
    public void getShelveAuditDataFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
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

    @Override
    public void deleteShelveAuditFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message+"  "+e.status);
        Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK&&type==2) {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getShelveAuditData(Sp.getString(getContext(),AppConstant.SHOP_ID),type,page);
        }
    }
}
