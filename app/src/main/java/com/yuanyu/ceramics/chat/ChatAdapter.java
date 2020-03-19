package com.yuanyu.ceramics.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ChatEntity> list;

    ChatAdapter(Context context, List<ChatEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isMysend()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setList(List<ChatEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat, parent, false));
        } else if (viewType == 1) {
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat1, parent, false));
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder0){
            GlideApp.with(context).load(AppConstant.BASE_URL + list.get(position).getAvatar()).placeholder(R.drawable.logo_default).into(((ViewHolder0) holder).avatar);
            ((ViewHolder0) holder).message.setText(list.get(position).getMsg());
            ((ViewHolder0) holder).time.setVisibility(View.GONE);
            ((ViewHolder0) holder).avatar.setOnClickListener(v -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));
        }else if(holder instanceof ViewHolder1){
            GlideApp.with(context).load(AppConstant.BASE_URL + list.get(position).getAvatar()).placeholder(R.drawable.logo_default).into(((ViewHolder1) holder).avatar);
            ((ViewHolder1) holder).message.setText(list.get(position).getMsg());
            ((ViewHolder1) holder).time.setVisibility(View.GONE);
            ((ViewHolder1) holder).avatar.setOnClickListener(v -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static public class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.message)
        TextView message;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static public class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.message)
        TextView message;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
