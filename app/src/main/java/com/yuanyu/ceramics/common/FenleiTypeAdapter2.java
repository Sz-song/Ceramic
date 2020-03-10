package com.yuanyu.ceramics.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;

import java.util.List;

public class FenleiTypeAdapter2 extends RecyclerView.Adapter<FenleiTypeAdapter2.ViewHolder> {
    private Context mContext;
    private List<FenleiTypeBean> mList;
    private ClickListener clickListener;

    public FenleiTypeAdapter2(Context context, List<FenleiTypeBean> list){
        mContext = context;
        mList = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fenlei_recy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.button.setText(mList.get(position).getType());
        if (mList.get(position).isChoose())holder.button.setActivated(true);
        else holder.button.setActivated(false);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<mList.size();i++)mList.get(i).setChoose(false);
                mList.get(position).setChoose(true);
                notifyDataSetChanged();
                if (clickListener !=null) clickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.recybtn);
        }
    }

    public interface ClickListener{
        void onClick(int position);
    }
}
