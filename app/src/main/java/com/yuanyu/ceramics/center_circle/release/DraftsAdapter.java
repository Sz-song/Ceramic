package com.yuanyu.ceramics.center_circle.release;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.ViewHolder> {
    private Context context;
    private List<DraftsBean> list;
    private OnPositionClickListener ToresListener;
    private OnPositionClickListener TodeleteLis;

    public DraftsAdapter(Context context, List<DraftsBean> list) {
        this.context = context;
        this.list = list;
    }
    public void ToresListener(OnPositionClickListener ToresListener){
        this.ToresListener = ToresListener;
    }
    public void Todelete(OnPositionClickListener TodeleteLis){
        this.TodeleteLis = TodeleteLis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_drafts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (list.get(position).getType()) {
            case 0:
                holder.type.setText("动态");
                break;
            case 1:
                holder.type.setText("文章");
                break;
        }
        holder.time.setText(Md5Utils.getStrSecond(list.get(position).getTime()));
        holder.content.setText(list.get(position).getContent());
        holder.tores.setOnClickListener(view -> {ToresListener.callback(position);});
        holder.delete.setOnClickListener(view -> {
            TodeleteLis.callback(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.tores)
        TextView tores;
        @BindView(R.id.delete)
        TextView delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
