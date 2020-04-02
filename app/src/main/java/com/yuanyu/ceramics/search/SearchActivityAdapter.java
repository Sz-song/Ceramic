package com.yuanyu.ceramics.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivityAdapter extends RecyclerView.Adapter<SearchActivityAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    SearchActivityAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cell.setText(list.get(position));
        holder.cell.setOnClickListener(v -> {
            Intent intent = new Intent(context, SearchResultActivity.class);
            intent.putExtra("query", list.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell)
        TextView cell;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
