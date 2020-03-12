package com.yuanyu.ceramics.center_circle.release;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class SelectFriendAdapter extends RecyclerView.Adapter<SelectFriendAdapter.ViewHolder> {
    private Context context;
    private List<FriendBean> list;
    private Listener listener;
    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public SelectFriendAdapter(Context context, List<FriendBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_sefriend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.img_default)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemOnClick.click(list.get(position));
//            }
//        });
        ((ViewHolder) holder).itemCheckbox.setChecked(list.get(position).isChecked());
        holder.itemView.setOnClickListener(view -> listener.onClick(holder, position));
        ((ViewHolder) holder).itemCheckbox.setOnClickListener(view -> listener.onClick(holder, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_checkbox)
        SmoothCheckBox itemCheckbox;
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    interface ItemOnClick {
        void click(FriendBean friendBean);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClick(RecyclerView.ViewHolder holder, int position);
    }
}
