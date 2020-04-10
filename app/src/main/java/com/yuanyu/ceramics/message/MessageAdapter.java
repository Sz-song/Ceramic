package com.yuanyu.ceramics.message;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.chat.ChatActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<MessageBean> list;

    public MessageAdapter(Context context, List<MessageBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nickname.setText(list.get(position).getNickname());
        holder.time.setText(TimeUtils.CountTime(list.get(position).getTime()));
        holder.lastMsg.setText(list.get(position).getLastMsg());
        GlideApp.with(context)
                .load(BASE_URL+list.get(position).getIcon())
                .placeholder(R.drawable.logo_default)
                .into(holder.conversationIcon);
        if(list.get(position).getUnreadnum()>0){
            holder.count.setVisibility(View.VISIBLE);
            if(list.get(position).getUnreadnum()>99){
                holder.count.setText("99+");
            }else{
                holder.count.setText(""+list.get(position).getUnreadnum());
            }
        }else{
            holder.count.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> ChatActivity.navToChat(context,list.get(position).getUseraccountid()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.conversation_icon)
        RoundedImageView conversationIcon;
        @BindView(R.id.nickname)
        TextView nickname;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.last_msg)
        TextView lastMsg;
        @BindView(R.id.count)
        TextView count;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
