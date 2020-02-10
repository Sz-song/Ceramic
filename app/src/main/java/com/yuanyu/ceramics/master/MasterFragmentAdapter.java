package com.yuanyu.ceramics.master;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterFragmentAdapter extends RecyclerView.Adapter<MasterFragmentAdapter.ViewHolder> {
    private Context context;
    private List<MasterItemBean> list;

    MasterFragmentAdapter(Context context, List<MasterItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_master_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.image_default)
                .override(300, 300)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName());
        holder.introduce.setText(list.get(position).getIntroduce().trim());
        FlowLayoutManager manager = new FlowLayoutManager(context, true);
        DashiTagAdapter adapter = new DashiTagAdapter(context, list.get(position).getTag());
        holder.recyclerview.setLayoutManager(manager);
        holder.recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return list.size();}

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        ImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.introduce)
        TextView introduce;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
