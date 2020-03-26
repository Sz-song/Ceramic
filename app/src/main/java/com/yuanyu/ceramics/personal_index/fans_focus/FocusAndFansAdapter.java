package com.yuanyu.ceramics.personal_index.fans_focus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.yuanyu.ceramics.AppConstant.BASE_URL;
public class FocusAndFansAdapter extends RecyclerView.Adapter<FocusAndFansAdapter.ViewHolder> {
    private Context context;
    private List<FocusAndFansBean> list ;
    private OnPositionClickListener onFocusClick;

    public void setOnFocusClick(OnPositionClickListener onFocusClick) {
        this.onFocusClick = onFocusClick;
    }

    FocusAndFansAdapter(Context context, List<FocusAndFansBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_focusandfans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,int position) {
        GlideApp.with(context).load(BASE_URL+list.get(position).getPortrait()).placeholder(R.drawable.logo_default).into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.txt.setText(list.get(position).getTxt());
        holder.fansNum.setText("粉丝:"+list.get(position).getFans_num());
        if(list.get(position).isIsfocus()){
            holder.isfocus.setText("已关注");
            holder.isfocus.setTextColor(context.getResources().getColor(R.color.blackLight));
            holder.isfocus.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
        }else{
            holder.isfocus.setText("+ 关注");
            holder.isfocus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.isfocus.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
        }
        holder.isfocus.setOnClickListener(view -> {onFocusClick.callback(position);});
        holder.item.setOnClickListener(view -> PersonalIndexActivity.actionStart(context,list.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RoundedImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.txt)
        TextView txt;
        @BindView(R.id.isfocus)
        TextView isfocus;
        @BindView(R.id.fans_num)
        TextView fansNum;
        @BindView(R.id.item)
        RelativeLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
