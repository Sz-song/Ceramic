package com.yuanyu.ceramics.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<String> mhistory;
    private setEditClickListener listener;

    class ViewHolder extends RecyclerView.ViewHolder{
        View historyView;
        TextView history;

        public ViewHolder(View itemView) {
            super(itemView);
            historyView = itemView;
            history = itemView.findViewById(R.id.history);
        }
        public void setData(final String his, final int position){
            historyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(position,his);
                }
            });
        }

    }
    public HistoryAdapter(List<String> historyList){
        mhistory = historyList;

    }
    public void setEditClickListener(setEditClickListener listener){
        this.listener = listener ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).
                 inflate(R.layout.history,parent,false);
         final ViewHolder holder = new ViewHolder(view);

         return holder;
     }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.history.setText(mhistory.get(position));
        holder.setData(mhistory.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mhistory.size();
    }


    public interface setEditClickListener{
        void OnItemClick(int position, String his);
    }

}
