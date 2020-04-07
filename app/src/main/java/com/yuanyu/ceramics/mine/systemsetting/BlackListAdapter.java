package com.yuanyu.ceramics.mine.systemsetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.Sp;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class BlackListAdapter extends RecyclerView.Adapter <BlackListAdapter.ViewHolder>{
    private List<BlackListBean> list;
    private Context context;
    private SystemSettingModel model;

    BlackListAdapter(List<BlackListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.cell_blacklist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(BASE_URL+list.get(position).getPortrait()).into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.remove.setOnClickListener(view -> {
            model=new SystemSettingModel();
            model.removeBlacklist(Sp.getString(context,"useraccountid"),list.get(position).getBlackuserid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(new HttpServiceInstance.ErrorTransformer<>())
                    .subscribe(new BaseObserver() {
                        @Override
                        public void onError(ExceptionHandler.ResponeThrowable e) {
                            Toast.makeText(context, "解除拉黑失败，稍后再试", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNext(Object o) {
                            Toast.makeText(context, "解除拉黑成功", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.remove)
        TextView remove;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
