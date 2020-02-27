package com.yuanyu.ceramics.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private Context context;
    private List<MyOrderFragmentBean> mList;
    private DeleteListener deleteListener;
    private CancelListener cancelListener;

    private ConfirmListener confirmListener;
    private FahuoListener fahuoListener;
    private boolean canRefund ;

    MyOrderAdapter(Context context, List<MyOrderFragmentBean> list, boolean canRefund) {
        this.context = context;
        mList = list;
        this.canRefund = canRefund;
    }

    void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    void setConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    void setFahuoListener(FahuoListener fahuoListener) {
        this.fahuoListener = fahuoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_my_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(context)
                .load(BASE_URL+mList.get(position).getShop_portrait())
                .placeholder(R.drawable.img_default)
                .into(holder.protrait);
        holder.shopname.setText(mList.get(position).getShop_name());
        holder.shopname.setOnClickListener(v -> {
            Intent intent=new Intent(context, ShopIndexActivity.class);
            intent.putExtra("shopid",mList.get(position).getShopid());
        });
        holder.protrait.setOnClickListener(v -> {
            Intent intent=new Intent(context,ShopIndexActivity.class);
            intent.putExtra("shopid",mList.get(position).getShopid());
        });
        holder.totalQuantity.setText("共"+mList.get(position).getItem_list().size()+"件商品");
        holder.view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        double price = 0.00;
        for (int i=0;i<mList.get(position).getItem_list().size();i++){
            try {
                price += Double.parseDouble(mList.get(position).getItem_list().get(i).getPrice());
            }catch (Exception e){
                L.e(e.getMessage());
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String priceStr=df.format(price);
        holder.totalPrice.setText("￥"+priceStr);//保留两位小数；
        switch (mList.get(position).getStatus()){
            case 1:
                long remainingTime = Long.valueOf(mList.get(position).getTime())+30*60 - Long.valueOf(mList.get(position).getCurrenttime());
                if (remainingTime > 0) {
                    holder.orderStatus.setText("等待您付款");
                    holder.daifukuan.setVisibility(View.VISIBLE);
                    holder.daishouhuo.setVisibility(View.GONE);
                    holder.daipingjia.setVisibility(View.GONE);
                    holder.yiwancheng.setVisibility(View.GONE);
                    holder.aletFahuo.setVisibility(View.GONE);
                    holder.time.setText(TimeUtils.getRemainingTime(mList.get(position).getTime(), mList.get(position).getCurrenttime()) + "分");
                    holder.cancel.setOnClickListener(view -> cancelListener.cancel(position));
                    holder.makePayment.setOnClickListener(view -> {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("ordernum", mList.get(position).getOrdernum());
                        context.startActivity(intent);
                    });
                    break;
                } else {
                    holder.orderStatus.setText("订单已超时");
                    holder.daifukuan.setVisibility(View.GONE);
                    holder.daishouhuo.setVisibility(View.GONE);
                    holder.daipingjia.setVisibility(View.GONE);
                    holder.yiwancheng.setVisibility(View.GONE);
                    holder.aletFahuo.setVisibility(View.GONE);
                    break;
                }
            case 2:
                holder.orderStatus.setText("买家已付款");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.GONE);
                holder.daipingjia.setVisibility(View.GONE);
                holder.yiwancheng.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.VISIBLE);
                holder.fahuo.setOnClickListener(view -> fahuoListener.fahuo(position));
                break;
            case 3:
                holder.orderStatus.setText("卖家已发货");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.VISIBLE);
                holder.daipingjia.setVisibility(View.GONE);
                holder.yiwancheng.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.GONE);
                holder.delivery.setOnClickListener(view -> {
//                    Intent intent = new Intent(context,LogisticsTracingActivity.class);
//                    intent.putExtra("logistics",mList.get(position).getLogisticsnum());
//                    intent.putExtra("image", mList.get(position).getItem_list().get(0).getImage());
//                    intent.putExtra("logistics_id", mList.get(position).getLogisticscompany());
//                    context.startActivity(intent);
                });
                holder.confirm.setOnClickListener(view -> confirmListener.confirm(position));
                break;
            case 4:
                holder.orderStatus.setText("等待您评价");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.GONE);
                holder.daipingjia.setVisibility(View.VISIBLE);
                holder.yiwancheng.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.GONE);
                holder.delivery2.setOnClickListener(view -> {
//                    Intent intent = new Intent(context,LogisticsTracingActivity.class);
//                    intent.putExtra("logistics",mList.get(position).getLogisticsnum());
//                    intent.putExtra("image", mList.get(position).getItem_list().get(0).getImage());
//                    intent.putExtra("logistics_id", mList.get(position).getLogisticscompany());
//                    context.startActivity(intent);
                });
                holder.evaluate.setOnClickListener(view -> {
//                    Intent intent = new Intent(context,EvaluationActivity.class);
//                    intent.putExtra("order_num",mList.get(position).getOrdernum());
//                    intent.putExtra("commodity_id",mList.get(position).getItem_list().get(0).getId());
//                    intent.putExtra("shop_id",mList.get(position).getShopid());
//                    context.startActivity(intent);
                });
                break;
            case AppConstant.YIPINGJIA:
                holder.orderStatus.setText("交易成功");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.GONE);
                holder.daipingjia.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.GONE);
                holder.yiwancheng.setVisibility(View.VISIBLE);
                holder.delete2.setOnClickListener(view -> deleteListener.delete(position));
                break;
            case 6:
                holder.orderStatus.setText("已取消订单");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.GONE);
                holder.daipingjia.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.GONE);
                holder.yiwancheng.setVisibility(View.VISIBLE);
                holder.delete2.setOnClickListener(view -> deleteListener.delete(position));
                break;
            default:
                holder.orderStatus.setText("异常订单");
                holder.daifukuan.setVisibility(View.GONE);
                holder.daishouhuo.setVisibility(View.GONE);
                holder.daipingjia.setVisibility(View.GONE);
                holder.aletFahuo.setVisibility(View.GONE);
                holder.yiwancheng.setVisibility(View.VISIBLE);
                holder.delete2.setOnClickListener(view -> deleteListener.delete(position));
                break;
        }
        ItemAdapter adapter = new ItemAdapter(context, mList.get(position).getItem_list(), canRefund);
        adapter.setClickListener(() -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("ordernum", mList.get(position).getOrdernum());
            context.startActivity(intent);
        });
        adapter.setRefundListener(itemPostition -> {

        });
        holder.recyclerview.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerview.setLayoutManager(lm);
        if(mList.get(position).getRefund_status()!=4&&mList.get(position).getRefund_status()!=2) {
            holder.orderStatus.setText("退款售后订单");
            holder.daifukuan.setVisibility(View.GONE);
            holder.daishouhuo.setVisibility(View.GONE);
            holder.daipingjia.setVisibility(View.GONE);
            holder.aletFahuo.setVisibility(View.GONE);
            holder.yiwancheng.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("ordernum", mList.get(position).getOrdernum());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.protrait)
        RoundedImageView protrait;
        @BindView(R.id.shopname)
        TextView shopname;
        @BindView(R.id.order_status)
        TextView orderStatus;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.total_quantity)
        TextView totalQuantity;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.cancel)
        TextView cancel;
        @BindView(R.id.make_payment)
        TextView makePayment;
        @BindView(R.id.daifukuan)
        LinearLayout daifukuan;
        @BindView(R.id.delivery)
        TextView delivery;
        @BindView(R.id.confirm)
        TextView confirm;
        @BindView(R.id.daishouhuo)
        LinearLayout daishouhuo;
        @BindView(R.id.delivery2)
        TextView delivery2;
        @BindView(R.id.evaluate)
        TextView evaluate;
        @BindView(R.id.daipingjia)
        LinearLayout daipingjia;
        @BindView(R.id.daifahuo)
        LinearLayout daifahuo;
        @BindView(R.id.yiwancheng)
        LinearLayout yiwancheng;
        @BindView(R.id.delete2)
        TextView delete2;
        @BindView(R.id.fahuo)
        TextView fahuo;
        @BindView(R.id.alert_fahuo)
        LinearLayout aletFahuo;
        @BindView(R.id.view)
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface DeleteListener{
        void delete(int position);
    }

    public interface CancelListener{
        void cancel(int position);
    }

    public interface ConfirmListener{
        void confirm(int position);
    }
    public interface FahuoListener{
        void fahuo(int position);
    }
}
