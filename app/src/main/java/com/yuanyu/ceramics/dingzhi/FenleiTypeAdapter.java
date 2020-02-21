package com.yuanyu.ceramics.dingzhi;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.common.OnNoDataListener;

import java.util.List;

public class FenleiTypeAdapter extends RecyclerView.Adapter<FenleiTypeAdapter.ViewHolder> {
    private Context mContext;
    private List<FenleiTypeBean> mList;
    private OnNoDataListener onClickListener;

    public void setOnClickListener(OnNoDataListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FenleiTypeAdapter(Context context, List<FenleiTypeBean> list){
        mContext = context;
        mList = list;
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
        holder.button.setOnClickListener(view -> {
            if (mList.get(position).isChoose()){
                mList.get(position).setChoose(false);
                notifyDataSetChanged();
            }
            else {
                int count = 0;
                for (int i=0;i<mList.size();i++){
                    if (mList.get(i).isChoose())count++;
                }
                if (count>=3) Toast.makeText(mContext,"最多只能选3个",Toast.LENGTH_LONG).show();
                else {
                    mList.get(position).setChoose(!mList.get(position).isChoose());
                    notifyDataSetChanged();
                }
            }
            if(onClickListener!=null){
                onClickListener.setNodata();
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
}

