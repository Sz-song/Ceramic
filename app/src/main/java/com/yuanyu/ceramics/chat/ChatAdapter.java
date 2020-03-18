package com.yuanyu.ceramics.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<ChatEntity> list;
    ChatAdapter(Context context, List<ChatEntity> list){
        this.context = context;
        this.list = list;
    }

    public void setList(List<ChatEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context).load(AppConstant.BASE_URL+ list.get(position).getAvatar()).placeholder(R.drawable.img_default).into(holder.avatar);
        holder.name.setText(list.get(position).getSendname());
        holder.message.setText(list.get(position).getMsg());
        holder.avatar.setOnClickListener(v -> PersonalIndexActivity.actionStart(context,list.get(position).getUseraccountid()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.message)
        TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
