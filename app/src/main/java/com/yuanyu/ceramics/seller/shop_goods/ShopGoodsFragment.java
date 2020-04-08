package com.yuanyu.ceramics.seller.shop_goods;

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

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.WrapContentLinearLayoutManager;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cat on 2018/8/15.
 */

public class ShopGoodsFragment extends BaseFragment<ShopGoodsPresenter> implements ShopGoodsConstract.IShopGoodsView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private boolean isInit = false;
    private int page = 0;
    private int type;
    private ShopGoodsAdapter adapter;
    private List<ShopGoodsBean> list;
    private LoadingDialog dialog;
    private boolean enable;
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected ShopGoodsPresenter initPresent() {
        return new ShopGoodsPresenter();
    }

    @Override
    public void initEvent(View view) {
        type = getArguments().getInt("type");
        list=new ArrayList<>();
        dialog=new LoadingDialog(getContext());
        enable=false;
        swipe.setColorSchemeResources(R.color.colorPrimary);
        GlideApp.with(getActivity())
                .load(R.drawable.nodata_img)
                .override(nodataImg.getWidth(), nodataImg.getHeight())
                .into(nodataImg);
        nodata.setText("暂时没有数据");
        WrapContentLinearLayoutManager manager=new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        adapter = new ShopGoodsAdapter(list,getContext());
        recyclerview.setAdapter(adapter);
        adapter.setOnDeleteClickListener(position -> {
            dialog.show();
            presenter.shopGoodsDelete(Sp.getString(getContext(), AppConstant.SHOP_ID),list.get(position).getId(),position);
        });
        adapter.setOnOffShelvesClickListener(position -> {
            dialog.show();
            presenter.shopGoodsOffShelves(Sp.getString(getContext(), AppConstant.SHOP_ID),list.get(position).getId(),position);
        });
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            dialog.show();
            if(enable) {
                presenter.getShopGoodsList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
            }
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
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>7&&enable) {
                        presenter.getShopGoodsList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        page=0;
        swipe.setRefreshing(true);
        dialog.show();
        presenter.getShopGoodsList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isInit) {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(true);
            presenter.getShopGoodsList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
        }
        isInit = true;
    }

    @Override
    public void getShopGoodsSuccess(List<ShopGoodsBean> beans) {
        L.e("获取成功");
        list.addAll(beans);
        enable=true;
        if(isAlive){
            dialog.dismiss();
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
    public void shopGoodsDeleteSuccess(int position) {
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
    public void shopGoodsOffShelvesSuccess(int position) {
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
    public void getShopGoodsFail(ExceptionHandler.ResponeThrowable e) {
        L.e("获取失败");
        enable=true;
        L.e(e.message+"  "+e.status);
        if(isAlive) {
            dialog.dismiss();
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
    public void shopGoodsDeleteFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        dialog.dismiss();
        Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void shopGoodsOffShelvesFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        dialog.dismiss();
        Toast.makeText(getContext(), "下架失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.nodata_img, R.id.nodata})
    public void onViewClicked(View view) {
        page = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        dialog.show();
        presenter.getShopGoodsList(Sp.getString(getContext(), AppConstant.SHOP_ID), page, type);
    }
}
