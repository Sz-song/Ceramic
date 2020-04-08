package com.yuanyu.ceramics.shop_index;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.FenleiBean;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopIndexFilterAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<List<FenleiBean>> list = new ArrayList<>();
    private String[] strings = new String[]{"分类", "种类", "外观"};
    private String[] fenlei_str = new String[]{"花瓶", "雕塑品", "园林陶艺", "器皿", "相框", "壁画", "陈设品" ,"其它"};
    private String[] zhonglei_str = new String[]{"素瓷", "青瓷", "黑瓷", "白瓷", "青白瓷", "其它"};
    private String[] ticai_str = new String[]{"神佛","瑞兽", "仿古",  "山水", "人物", "花鸟", "动物","其它"};
    private List<FenleiBean> fenlei_list = new ArrayList<>();
    private List<FenleiBean> zhonglei_list = new ArrayList<>();
    private List<FenleiBean> ticai_list = new ArrayList<>();
    private String priceLow = "";
    private String priceHight = "";
    private String weightLow = "";
    private String weightHight = "";

    public ShopIndexFilterAdapter(Context context) {
        this.context = context;
        initList();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 2;
        } else {
            return 1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_shop_filter0, parent, false);
            return new ViewHolder0(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_shop_filter1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_shop_filter2, parent, false);
            return new ViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder0) {
            ((ViewHolder0) holder).priceStart.setText(priceLow);
            ((ViewHolder0) holder).priceEnd.setText(priceHight);
            ((ViewHolder0) holder).price0.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder0) holder).price1.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder0) holder).price2.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder0) holder).priceStart.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    priceLow=((ViewHolder0) holder).priceStart.getText().toString();
                }
            });
            ((ViewHolder0) holder).priceEnd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    priceHight=((ViewHolder0) holder).priceEnd.getText().toString();
                }
            });
            ((ViewHolder0) holder).price0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceLow = "0";
                    priceHight = "10000";
                    ((ViewHolder0) holder).priceStart.setText(priceLow);
                    ((ViewHolder0) holder).priceEnd.setText(priceHight);
                    ((ViewHolder0) holder).price0.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewHolder0) holder).price1.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder0) holder).price2.setTextColor(context.getResources().getColor(R.color.blackLight));
                }
            });
            ((ViewHolder0) holder).price1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceLow = "10000";
                    priceHight = "80000";
                    ((ViewHolder0) holder).priceStart.setText(priceLow);
                    ((ViewHolder0) holder).priceEnd.setText(priceHight);
                    ((ViewHolder0) holder).price0.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder0) holder).price1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewHolder0) holder).price2.setTextColor(context.getResources().getColor(R.color.blackLight));
                }
            });
            ((ViewHolder0) holder).price2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceLow = "80000";
                    priceHight = "";
                    ((ViewHolder0) holder).priceStart.setText(priceLow);
                    ((ViewHolder0) holder).priceEnd.setText(priceHight);
                    ((ViewHolder0) holder).price0.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder0) holder).price1.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder0) holder).price2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            });
        } else if (holder instanceof ViewHolder2) {
            ((ViewHolder2) holder).weightStart.setText(weightLow);
            ((ViewHolder2) holder).weightEnd.setText(weightHight);
            ((ViewHolder2) holder).weight0.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder2) holder).weight1.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder2) holder).weight2.setTextColor(context.getResources().getColor(R.color.blackLight));
            ((ViewHolder2) holder).weightStart.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    weightLow=((ViewHolder2) holder).weightStart.getText().toString();
                }
            });
            ((ViewHolder2) holder).weightEnd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    weightHight=((ViewHolder2) holder).weightEnd.getText().toString();
                }
            });
            ((ViewHolder2) holder).weight0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    weightLow = "0";
                    weightHight = "50";
                    ((ViewHolder2) holder).weightStart.setText(weightLow);
                    ((ViewHolder2) holder).weightEnd.setText(weightHight);
                    ((ViewHolder2) holder).weight0.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewHolder2) holder).weight1.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder2) holder).weight2.setTextColor(context.getResources().getColor(R.color.blackLight));
                }
            });
            ((ViewHolder2) holder).weight1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    weightLow = "50";
                    weightHight = "200";
                    ((ViewHolder2) holder).weightStart.setText(weightLow);
                    ((ViewHolder2) holder).weightEnd.setText(weightHight);
                    ((ViewHolder2) holder).weight0.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder2) holder).weight1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewHolder2) holder).weight2.setTextColor(context.getResources().getColor(R.color.blackLight));
                }
            });
            ((ViewHolder2) holder).weight2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    weightLow = "200";
                    weightHight = "";
                    ((ViewHolder2) holder).weightStart.setText(weightLow);
                    ((ViewHolder2) holder).weightEnd.setText(weightHight);
                    ((ViewHolder2) holder).weight0.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder2) holder).weight1.setTextColor(context.getResources().getColor(R.color.blackLight));
                    ((ViewHolder2) holder).weight2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            });
        } else if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).title.setText(strings[position - 2]);
            ((ViewHolder1) holder).recyclerview.setLayoutManager(new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            final GridLayoutAdapter adapter = new GridLayoutAdapter(context);
            ((ViewHolder1) holder).recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged(false, list.get(position - 2));
            ((ViewHolder1) holder).all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapter.getExpand()) {
                        ((ViewHolder1) holder).triangle.setImageResource(R.drawable.sort_common_down1);
                        adapter.setExpand();
                        adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                    } else {
                        ((ViewHolder1) holder).triangle.setImageResource(R.drawable.sort_common_up1);
                        adapter.setExpand();
                        adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                    }
                }
            });
            ((ViewHolder1) holder).triangle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapter.getExpand()) {
                        ((ViewHolder1) holder).triangle.setImageResource(R.drawable.sort_common_down1);
                        adapter.setExpand();
                        adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                    } else {
                        ((ViewHolder1) holder).triangle.setImageResource(R.drawable.sort_common_up1);
                        adapter.setExpand();
                        adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                    }
                }
            });
            adapter.setOnItemClickListener(new GridLayoutAdapter.OnItemClickListener() {
                @Override
                public void onClick(int itemPosition) {
                    int count = 0;
                    for (int i = 0; i < list.get(position - 2).size(); i++) {
                        if (list.get(position - 2).get(i).isCheck()) {
                            count++;
                        }
                    }
                    if (count > 2) {
                        if (list.get(position - 2).get(itemPosition).isCheck()) {
                            list.get(position - 2).get(itemPosition).setCheck(!list.get(position - 2).get(itemPosition).isCheck());
                            adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                        } else {
                            Toast.makeText(context, "单项不能选择超过3个", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        list.get(position - 2).get(itemPosition).setCheck(!list.get(position - 2).get(itemPosition).isCheck());
                        adapter.notifyDataSetChanged(adapter.getExpand(), list.get(position - 2));
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return strings.length + 2;
    }
    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.price0)
        TextView price0;
        @BindView(R.id.price1)
        TextView price1;
        @BindView(R.id.price2)
        TextView price2;
        @BindView(R.id.price_start)
        EditText priceStart;
        @BindView(R.id.price_end)
        EditText priceEnd;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.triangle)
        ImageView triangle;
        @BindView(R.id.all)
        TextView all;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.weight0)
        TextView weight0;
        @BindView(R.id.weight1)
        TextView weight1;
        @BindView(R.id.weight2)
        TextView weight2;
        @BindView(R.id.weight_start)
        EditText weightStart;
        @BindView(R.id.weight_end)
        EditText weightEnd;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void initList() {
        for (int i = 0; i < fenlei_str.length; i++) {
            fenlei_list.add(new FenleiBean(fenlei_str[i], false));
        }
        for (int i = 0; i < zhonglei_str.length; i++) {
            zhonglei_list.add(new FenleiBean(zhonglei_str[i], false));
        }
        for (int i = 0; i < ticai_str.length; i++) {
            ticai_list.add(new FenleiBean(ticai_str[i], false));
        }
        list.add(fenlei_list);
        list.add(zhonglei_list);
        list.add(ticai_list);
    }

    public void reset() {
        fenlei_list.clear();
        zhonglei_list.clear();
        ticai_list.clear();
        list.clear();
        initList();
        priceLow = "";
        priceHight = "";
        weightLow = "";
        weightHight = "";
        notifyDataSetChanged();
    }
    public void search(String shop_id) {
        if(priceLow.length()>0&&priceHight.length()>0&& Integer.parseInt(priceLow)>= Integer.parseInt(priceHight)){
            Toast.makeText(context, "最低价高于最高价", Toast.LENGTH_SHORT).show();
        }else if(weightLow.length()>0&&weightHight.length()>0&& Integer.parseInt(weightLow)>= Integer.parseInt(weightHight)){
            Toast.makeText(context, "最低重量高于最高重量", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(context, ShopIndexSearchActivity.class);
            L.e("mip is" + priceLow);
            intent.putExtra("min_price", priceLow);
            intent.putExtra("max_price", priceHight);
            intent.putExtra("min_weight", weightLow);
            intent.putExtra("max_weight", weightHight);
            intent.putExtra("feilei", getString(list.get(0)));
            intent.putExtra("zhonglei", getString(list.get(1)));
            intent.putExtra("ticai", getString(list.get(2)));
            intent.putExtra("shop_id", shop_id);
            context.startActivity(intent);
        }
    }

    private String getString(List<FenleiBean> fenleiBeans) {
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<fenleiBeans.size();i++){
            if(fenleiBeans.get(i).isCheck()){
                stringBuffer.append(fenleiBeans.get(i).getName()+"、");
            }
        }
        if(stringBuffer.toString().length()>0){
             return stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
        }else{
            return "";
        }
    }
}
