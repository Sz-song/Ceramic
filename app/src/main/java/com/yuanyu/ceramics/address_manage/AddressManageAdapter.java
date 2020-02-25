package com.yuanyu.ceramics.address_manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressManageAdapter extends RecyclerView.Adapter<AddressManageAdapter.ViewHolder> {

    private Context context;
    private List<AddressManageBean> list;
    private OnPositionClickListener onItemClickListener;
    private OnPositionClickListener onEditClickListener;
    private OnPositionClickListener onDefaultClickListener;
    private OnPositionClickListener onDeteleClickListener;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<AddressManageBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnPositionClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnEditClickListener(OnPositionClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    public void setOnDefaultClickListener(OnPositionClickListener onDefaultClickListener) {
        this.onDefaultClickListener = onDefaultClickListener;
    }

    public void setOnDeteleClickListener(OnPositionClickListener onDeteleClickListener) {
        this.onDeteleClickListener = onDeteleClickListener;
    }
    public AddressManageAdapter(Context context, List<AddressManageBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_address, parent, false));
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.address.setText(list.get(position).getProvince()+"  "+list.get(position).getCity()+"  "+list.get(position).getExparea()+"  "+list.get(position).getAddress());
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());
        if (list.get(position).getIsdefault() == 1) {
            holder.checkbox.setChecked(true);
        }else{
            holder.checkbox.setChecked(false);
        }
        holder.checkbox.setOnClickListener(view -> {
            if (list.get(position).getIsdefault() == 1) {
                holder.checkbox.setChecked(true);
            }else{
                onDefaultClickListener.callback(position);
            }
        });
        holder.delete.setOnClickListener(view -> {
            DeleteDialog dialog=new DeleteDialog(context);
            dialog.setTitle("确定删除地址吗？");
            dialog.setNoOnclickListener(() -> {dialog.dismiss();});
            dialog.setYesOnclickListener(() -> {
                onDeteleClickListener.callback(position);
                dialog.dismiss();
            });
            dialog.show();
        });
        holder.edit.setOnClickListener(view -> onEditClickListener.callback(position));
        holder.itemView.setOnClickListener(view -> onItemClickListener.callback(position));

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.image)
            ImageView image;
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.phone)
            TextView phone;
            @BindView(R.id.address)
            TextView address;
            @BindView(R.id.liner)
            LinearLayout liner;
            @BindView(R.id.checkbox)
            SmoothCheckBox checkbox;
            @BindView(R.id.delete)
            LinearLayout delete;
            @BindView(R.id.edit)
            LinearLayout edit;
            @BindView(R.id.relat)
            RelativeLayout relat;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
    }


}
