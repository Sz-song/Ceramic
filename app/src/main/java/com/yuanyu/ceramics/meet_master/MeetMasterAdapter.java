package com.yuanyu.ceramics.meet_master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MeetMasterAdapter extends RecyclerView.Adapter<MeetMasterAdapter.ViewHolder> {
    @BindView(R.id.avatar)
    RoundedImageView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.focus)
    TextView focus;
    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.consult)
    TextView consult;
    @BindView(R.id.intro)
    TextView intro;
    private Context context;
    private List<MeetMasterBean> list;

    public MeetMasterAdapter(Context context, List<MeetMasterBean> list) {
        this.context = context;
        this.list= list;
    }

    //    加载布局文件
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_meet_master, parent, false));
    }

    //每个布局长啥样
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context).load(BASE_URL+list.get(position).getShop_avatar()).override(200,200).into(holder.avatar);
        holder.fans.setText(list.get(position).getFansnum());
        holder.fans.setText(list.get(position).getFocusnum());
        holder.name.setText(list.get(position).getShop_name());
        if (!list.get(position).getShop_slogan().equals("")) holder.intro.setText(list.get(position).getShop_slogan());
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
        @BindView(R.id.consult)
        TextView consult;
        @BindView(R.id.intro)
        TextView intro;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
