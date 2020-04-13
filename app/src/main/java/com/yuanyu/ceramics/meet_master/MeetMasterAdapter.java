package com.yuanyu.ceramics.meet_master;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MeetMasterAdapter extends RecyclerView.Adapter<MeetMasterAdapter.ViewHolder> {
    private Context context;
    private List<MeetMasterBean> list;

    MeetMasterAdapter(Context context, List<MeetMasterBean> list) {
        this.context = context;
        this.list= list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_meet_master, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context).load(BASE_URL+list.get(position).getPortrait()).placeholder(R.drawable.img_default).override(200,200).into(holder.avatar);
        holder.fans.setText(list.get(position).getFans_num()+"粉丝");
        holder.focus.setText(list.get(position).getFocus_num()+"关注");
        holder.name.setText(list.get(position).getName());
        if (list.get(position)!=null&&list.get(position).getIntroduce().equals("")) {
            holder.intro.setText("暂无简介");
        }else{
            holder.intro.setText(list.get(position).getIntroduce());
        }
        holder.itemView.setOnClickListener(view -> PersonalIndexActivity.actionStart(context,list.get(position).getUseraccountid()));
        holder.viewMaster.setOnClickListener(view -> PersonalIndexActivity.actionStart(context,list.get(position).getUseraccountid()));
    }

    //加载多少个布局
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        RoundedImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.focus)
        TextView focus;
        @BindView(R.id.fans)
        TextView fans;
        @BindView(R.id.view_master)
        TextView viewMaster;
        @BindView(R.id.intro)
        TextView intro;

        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
