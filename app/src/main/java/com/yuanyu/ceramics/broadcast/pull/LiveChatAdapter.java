package com.yuanyu.ceramics.broadcast.pull;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyu.ceramics.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveChatAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<LiveChatBean> list;

    public LiveChatAdapter(Context context, List<LiveChatBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_live_chat, parent, false));
        }else if(viewType==1){
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_live_chat_enter, parent, false));
        }
        return  null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder0){
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary));
            LiveChatClickableSpan clickableSpan = new LiveChatClickableSpan(context, list.get(position).getUseraccountid());
            SpannableString spannabletxt = new SpannableString(list.get(position).getUickname() + ":" + list.get(position).getMessage());
            spannabletxt.setSpan(colorSpan, 0, list.get(position).getUickname().length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannabletxt.setSpan(clickableSpan, 0, list.get(position).getUickname().length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((ViewHolder0) holder).message.setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder0) holder).message.setText(spannabletxt);
        }else if(holder instanceof ViewHolder1){
            ((ViewHolder1) holder).enterMessage.setText("欢迎"+list.get(position).getUickname()+"进入直播间");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.message)
        TextView message;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder{
        @BindView(R.id.enter_message)
        TextView enterMessage;
        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
