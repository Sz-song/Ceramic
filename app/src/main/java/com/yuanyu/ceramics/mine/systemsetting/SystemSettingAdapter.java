package com.yuanyu.ceramics.mine.systemsetting;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SystemSettingAdapter extends RecyclerView.Adapter<SystemSettingAdapter.ViewHolder> {
    private Context context;
    private String[] list ;
    private LoadingDialog dialog;
    SystemSettingAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_systemsetteng_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        holder.text.setText(list[position]);
        holder.cell.setOnClickListener(view -> {
            Intent intent;
            switch (position){
                case 0://清除缓存
                    dialog=new LoadingDialog(context);
                    dialog.show();
                    CountDownTimer countDownTimer=new CountDownTimer(1000,1000) {
                        @Override
                        public void onTick(long l) {
                        }
                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            Toast.makeText(context, "清理完成", Toast.LENGTH_SHORT).show();
                        }
                    };
                    countDownTimer.start();
//                    LitePal.deleteAll(SearchHistoryBean.class);
                    PictureFileUtils.deleteAllCacheDirFile(context);
                    L.e(" CLEAN UP");
                    break;
                case 1://黑名单管理
                    intent=new Intent(context,BlackListActivity.class);
                    context.startActivity(intent);
                    break;
                case 2://"账户安全"
                    intent=new Intent(context,AccountSecurityActivity.class);
                    context.startActivity(intent);
                    break;
                case 3://"意见反馈"
                    intent=new Intent(context,OpinionOfYouActivity.class);
                    context.startActivity(intent);
                    break;
                case 4://关于源玉"
                    intent=new Intent(context,AboutYuanyuActivity.class);
                    context.startActivity(intent);
                    break;
                case 5://联系客服
                    Toast.makeText(context, "系统繁忙", Toast.LENGTH_SHORT).show();
//                    dialog=new LoadingDialog(context);
//                    model.getCustomerService(SpUtils.getInt(context, AppConstant.USER_ACCOUNT_ID))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .compose(new HttpServiceInstance.ErrorTransformer<String>())
//                            .subscribe(new BaseObserver<String>() {
//                                @Override
//                                public void onNext(String s) {
//                                    ChatActivity.navToChat(context,s, TIMConversationType.C2C);
//                                    dialog.dismiss();
//                                }
//                                @Override
//                                public void onError(ExceptionHandler.ResponeThrowable e) {
//                                    Toast.makeText(context, "系统繁忙", Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
//                                    L.e(e.status+"  "+e.message);
//                                }
//                            });
                    break;
            }

        });
    }


    @Override
    public int getItemCount() {
        return list.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.cell)
        RelativeLayout cell;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
