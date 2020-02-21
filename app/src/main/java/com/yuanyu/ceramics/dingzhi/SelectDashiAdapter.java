package com.yuanyu.ceramics.dingzhi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class SelectDashiAdapter extends RecyclerView.Adapter<SelectDashiAdapter.ViewHolder> {
    private Context mContext;
    private List<DashiCellBean> mList;
    private ChooseListener listener;

    SelectDashiAdapter(Context context, List<DashiCellBean> list, ChooseListener listener){
        mContext = context;
        mList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cell_select_dashi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(mContext).load(BASE_URL+mList.get(position).getImage()).placeholder(R.drawable.img_default).into(holder.portrait);
        holder.name.setText(mList.get(position).getName());
        holder.detail.setText(mList.get(position).getDetail());
        holder.choose.setOnClickListener(view -> listener.choose(position));
        holder.itemView.setOnClickListener(v -> listener.choose(position));
//        holder.visit.setOnClickListener(view -> PersonalIndexActivity.actionStart(mContext,mList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.portrait)
        RoundedImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.visit)
        TextView visit;
        @BindView(R.id.choose)
        TextView choose;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface ChooseListener{
        void choose(int position);
    }
}
