package com.yuanyu.ceramics.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class SearchMasterAdapter extends RecyclerView.Adapter<SearchMasterAdapter.ViewHolder> {
    private List<SearchMasterBean> list;
    private Context content;
    private OnPositionClickListener FocusClickListener;

    public void setFocusClickListener(OnPositionClickListener focusClickListener) {
        FocusClickListener = focusClickListener;
    }

    public SearchMasterAdapter(List<SearchMasterBean> list, Context content) {
        this.list = list;
        this.content = content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_master, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SearchMasterBean dashi = list.get(position);
        GlideApp.with(content)
                .load(BASE_URL + dashi.getPortrait())
                .placeholder(R.drawable.img_default)
                .override(100,100)
                .into(holder.touxiang);
        holder.name.setText(dashi.getName());
        holder.job.setText(dashi.getTitle());
        holder.fensi.setText(dashi.getFensi());
        holder.guanzhu.setText(dashi.getGuanzhu());
        holder.dongtai.setText(dashi.getDongtai());
        switch (dashi.getIsFollowed()) {
            case 0: holder.guanzhuBtn.setText("+关注");
                break;
            case 1: holder.guanzhuBtn.setText("已关注");
                break;
        }
        holder.itemView.setOnClickListener(view -> PersonalIndexActivity.actionStart(content, dashi.getId()));
        holder.guanzhuBtn.setOnClickListener(view -> FocusClickListener.callback(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.touxiang)
        ImageView touxiang;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.guanzhu_btn)
        TextView guanzhuBtn;
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.fensi)
        TextView fensi;
        @BindView(R.id.guanzhu)
        TextView guanzhu;
        @BindView(R.id.dongtai)
        TextView dongtai;
        @BindView(R.id.linear)
        LinearLayout linear;
        @BindView(R.id.relat)
        LinearLayout relat;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
