package com.yuanyu.ceramics.personal_index;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.DynamicBean;
import com.yuanyu.ceramics.common.DynamicPopupWindow;
import com.yuanyu.ceramics.common.YubaHotRecyclerViewAdapter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class PersonalFragment extends BaseFragment<PersonalFragmentPersenter> implements PersonalFragmentConstract.IPersonalFragmentView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    private List<DynamicBean> list;
    private YubaHotRecyclerViewAdapter adapter;
    private String userid;
    private int type;
    private int page;
    private boolean isenable=true;
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected PersonalFragmentPersenter initPresent() {
        return new PersonalFragmentPersenter();
    }

    @Override
    public void initEvent(View view) {
        Bundle bundle = getArguments();
        userid = bundle.getString("userid");
        type = bundle.getInt("type");
        list = new ArrayList<>();
        page = 0;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(manager);
        adapter = new YubaHotRecyclerViewAdapter(getContext(), list);
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setRefreshing(true);
        GlideApp.with(getActivity()).load(R.drawable.nodata_img).override(nodataImg.getWidth(), nodataImg.getHeight()).into(nodataImg);
        nodata.setText("暂时没有数据");
        swipe.setOnRefreshListener(() -> {
            page = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.initList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),userid,page,type);
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
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
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 3) {
                        if(isenable) {
                            isenable=false;
                            presenter.initList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID), userid, page, type);
                        }
                    }
                }
            }
        });
        nodataImg.setOnClickListener(view1 -> {
            page = 0;
            list.clear();
            swipe.setRefreshing(true);
            adapter.notifyDataSetChanged();
            presenter.initList(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),userid,page,type);
        });
        adapter.setOnDianzanListenner(position -> {
            L.e(isenable+"");
            if(isenable){
                isenable=false;
                int dianzan_type=list.get(position).getType()==0?0:2;
                presenter.dianZan(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),dianzan_type,list.get(position).getId(),list.get(position).getUseraccountid(),position);
            }
        });
        adapter.setOnMoreListenner(position -> {
            DynamicPopupWindow popupWindow;
            if((list.get(position).getUseraccountid()).equals(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID))) {
                popupWindow = new DynamicPopupWindow(getContext(), 0, list.get(position).getType(), position, list.get(position).getId(),true);
            }else{
                popupWindow = new DynamicPopupWindow(getContext(), 1, list.get(position).getType(), position, list.get(position).getId(),true);
            }
            popupWindow.showAtLocation(swipe, Gravity.BOTTOM,0,0);
            popupWindow.setBlacklistListener(position1 -> {
                presenter.blackList(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getUseraccountid());
                popupWindow.dismiss();
            });
            popupWindow.setYubaDeleteListener(position1 -> {
                if(list.get(position).getType()==0){
                    presenter.deleteDynamic(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getId(),position);
                }else if(list.get(position).getType()==1){
                    presenter.deleteArticle(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getId(),position);
                }
                popupWindow.dismiss();
            });
            popupWindow.setYubaShieldingListener(position1 ->{
                presenter.shield(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getType(),list.get(position).getId(),position);
                popupWindow.dismiss();
            });
        });
        adapter.setOnFocusListenner(position -> {
            if(isenable){
                isenable=false;
                presenter.focus(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getUseraccountid());
            }
        });
    }
    @Override
    public void initData() {
        presenter.initList(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),userid,page,type);
    }
    @Override
    public void initListSuccess(List<DynamicBean> beans) {
        if (beans.size() > 0) {
            list.addAll(beans);
        }
        if(isAlive) {
            swipe.setRefreshing(false);
            adapter.notifyItemRangeInserted(list.size() - beans.size(), beans.size());
            if (list.size() == 0) {
                nodataImg.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            } else {
                nodataImg.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }
        isenable=true;
        page++;
    }
    @Override
    public void initListFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status+""+e.message);
        isenable=true;
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
    public void focusSuccess(Boolean b, String userid) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUseraccountid().equals(userid)){
                list.get(i).setIsfocus(b);
            }
        }
        if(b){
            Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        isenable=true;
    }

    @Override
    public void focusFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + e.message);
        isenable=true;
    }

    @Override
    public void dianZanSuccess(int position) {
        try {
            if (list.get(position).isIsdianzan()) {
                list.get(position).setIsdianzan(!list.get(position).isIsdianzan());
                list.get(position).setDianzan_num((Integer.parseInt(list.get(position).getDianzan_num()) - 1) + "");
            } else {
                list.get(position).setIsdianzan(!list.get(position).isIsdianzan());
                list.get(position).setDianzan_num((Integer.parseInt(list.get(position).getDianzan_num()) + 1) + "");
            }
        }catch (Exception e){
            L.e(e.getMessage());
        }
        adapter.notifyItemChanged(position);
        isenable=true;
    }

    @Override
    public void dianZanFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        Toast.makeText(getContext(), "点赞失败", Toast.LENGTH_SHORT).show();
        isenable=true;
    }


    @Override
    public void shieldSuccess(int position) {//屏蔽成功
        if(isAlive) {
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
    public void shieldFail(ExceptionHandler.ResponeThrowable e) {//屏蔽失败
        L.e(e.status + " " + e.message);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void blackListSuccess(Boolean b) {//拉黑成功
        if (b) {
            Toast.makeText(getContext(), "成功拉黑", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "该用户已被拉黑", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void blackListFail(ExceptionHandler.ResponeThrowable e) {//拉黑失败
        L.e(e.status + " " + e.message);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess(int position) {//删除动态文章成功
        if(isAlive) {
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
    public void deleteFail(ExceptionHandler.ResponeThrowable e) { //删除动态文章失败
        L.e(e.status + " " + e.message);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }
}
