package com.yuanyu.ceramics.mycoins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.utils.HelpUtils;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExchangeCoinsAdapter extends RecyclerView.Adapter<ExchangeCoinsAdapter.ViewHolder> {
    private Context context;
    private List<ExchangeCoinsBean> list;
    private OnPositionClickListener changeClickListener;

    void setChangeClickListener(OnPositionClickListener changeClickListener) {
        this.changeClickListener = changeClickListener;
    }

    ExchangeCoinsAdapter(Context context, List<ExchangeCoinsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_exchange_coins, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        L.e("position is: "+position);
        holder.coinsNum.setText(list.get(position).getCoins_num()+" = ");
        holder.priceNum.setText(Md5Utils.subZeroAndDot(list.get(position).getPrice())+"元");

        holder.exchange.setOnClickListener(v -> changeClickListener.callback(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.coins_num)
        TextView coinsNum;
        @BindView(R.id.price_num)
        TextView priceNum;
        @BindView(R.id.exchange)
        TextView exchange;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
