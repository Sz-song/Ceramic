package com.yuanyu.ceramics.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class SearchMasterAdapter extends RecyclerView.Adapter<SearchMasterAdapter.ViewHolder> {
    private List<SearchMasterBean> list;
    private Context content;
    private OnPositionClickListener FocusClickListener;

    void setFocusClickListener(OnPositionClickListener focusClickListener) {
        FocusClickListener = focusClickListener;
    }

    SearchMasterAdapter(List<SearchMasterBean> list, Context content) {
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
        GlideApp.with(content)
                .load(BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.logo_default)
                .override(100, 100)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName());
        if (list.get(position).isIsfollowed()) {
            holder.focus.setText("已关注");
        } else {
            holder.focus.setText("+关注");
        }
        holder.fanNum.setText(list.get(position).getFans_num() + " 粉丝");
        holder.focusNum.setText(list.get(position).getFocus_num() + " 关注");
        holder.introduce.setText(list.get(position).getIntroduce());
        holder.itemView.setOnClickListener(view -> PersonalIndexActivity.actionStart(content, ""+list.get(position).getUseraccountid()));
        holder.focus.setOnClickListener(view -> FocusClickListener.callback(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.focus)
        TextView focus;
        @BindView(R.id.focus_num)
        TextView focusNum;
        @BindView(R.id.fan_num)
        TextView fanNum;
        @BindView(R.id.introduce)
        TextView introduce;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
