package com.yuanyu.ceramics.fenlei;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;

import java.util.List;


public class BottomResultAdapter extends RecyclerView.Adapter<BottomResultAdapter.ViewHolder> {
    private List<String> list;
    BottomResultAdapter(List<String> list){
        this.list=list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            button= itemView.findViewById(R.id.recybtn);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fenlei_recy_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.button.setText(list.get(position));
        holder.button.setTextSize(15);
        holder.button.setActivated(true);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}


