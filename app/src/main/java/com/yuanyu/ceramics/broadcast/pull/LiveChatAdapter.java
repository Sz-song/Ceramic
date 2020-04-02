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
import com.yuanyu.ceramics.common.DynamicClickableSpan;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveChatAdapter extends RecyclerView.Adapter<LiveChatAdapter.ViewHolder> {
    private Context context;
    private List<LiveChatBean> list;

    public LiveChatAdapter(Context context, List<LiveChatBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_live_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary));
        LiveChatClickableSpan clickableSpan = new LiveChatClickableSpan(context, list.get(position).getUseraccountid());
        SpannableString spannabletxt = new SpannableString(list.get(position).getUickname()+":"+list.get(position).getMessage());
        spannabletxt.setSpan(colorSpan, 0, list.get(position).getUickname().length()+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannabletxt.setSpan(clickableSpan, 0, list.get(position).getUickname().length()+1,  Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.message.setMovementMethod(LinkMovementMethod.getInstance());
        holder.message.setText(spannabletxt);
    }

    @Override
    public int getItemCount() { return list.size();}

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message)
        TextView message;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
