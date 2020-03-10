package com.yuanyu.ceramics.seller.shop_goods;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.PhotoVedioAdapter;
import com.yuanyu.ceramics.common.PhotoVideoBean;
import com.yuanyu.ceramics.common.VideoDisplayActivity;
import com.yuanyu.ceramics.common.ViewImageActivity;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewShopGoodsActivity extends BaseActivity<ViewShopGoodsPresenter> implements ViewShopGoodsConstract.IViewShopGoodsView {
    private static final int DISPLAY_VIDEO = 1004;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.et_serial_no)
    TextView etSerialNo;
    @BindView(R.id.artisan)
    TextView artisan;
    @BindView(R.id.relat_jiangren)
    RelativeLayout relatJiangren;
    @BindView(R.id.temp_num)
    TextView tempNum;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.fenxiao_price)
    TextView fenxiaoPrice;
    @BindView(R.id.fenxiao_liner)
    LinearLayout fenxiaoLiner;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.length)
    TextView length;
    @BindView(R.id.width)
    TextView width;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.fenlei)
    TextView fenlei;
    @BindView(R.id.zhonglei)
    TextView zhonglei;
    @BindView(R.id.ticai)
    TextView ticai;
    @BindView(R.id.chanzhuang)
    TextView chanzhuang;
    @BindView(R.id.pise)
    TextView pise;
    @BindView(R.id.freight)
    TextView freight;
    private PhotoVedioAdapter adapter;
    private LoadingDialog dialog;
    private List<PhotoVideoBean> list;
    private ShelvingDetailBean bean;

    @Override
    protected int getLayout() {
        return R.layout.activity_view_shop_goods;
    }

    @Override
    protected ViewShopGoodsPresenter initPresent() {
        return new ViewShopGoodsPresenter();
    }
    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_shop);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        String id = getIntent().getStringExtra("id");
        dialog = new LoadingDialog(this);
        list = new ArrayList<>();
        adapter = new PhotoVedioAdapter(this, list,false);
        recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(this, 3));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Intent intent;
            switch (list.get(position).getType()) {
                case 2://图片预览
                    List<String> listimage=new ArrayList<>();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getType()==2){
                            listimage.add(list.get(i).getUrl());
                        }
                    }
                    if(list.get(0).getType()==3){
                        ViewImageActivity.actionStart(ViewShopGoodsActivity.this,position-1, (ArrayList) listimage);
                    }else {
                        ViewImageActivity.actionStart(ViewShopGoodsActivity.this,position, (ArrayList) listimage);
                    }
                    break;
                case 3:
                    intent = new Intent(ViewShopGoodsActivity.this, VideoDisplayActivity.class);
                    intent.putExtra("video", list.get(position).getUrl());
                    intent.putExtra("cover",bean.getVideo_cover());
                    intent.putExtra("delete",0);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_VIDEO);
                    break;
            }
        });
        dialog.show();
        presenter.getShopGoodsDetail(Sp.getString(this, AppConstant.SHOP_ID), id);
    }


    @Override
    public void getShopGoodsDetailSuccess(ShelvingDetailBean shelvingDetailBean) {
        dialog.dismiss();
        bean=shelvingDetailBean;
        title.setText(shelvingDetailBean.getTitle());
        content.setText(shelvingDetailBean.getDescription());
        if(shelvingDetailBean.getVideo()!=null&&shelvingDetailBean.getVideo().length()>0){
            list.add(0,new PhotoVideoBean(shelvingDetailBean.getVideo(),3));
        }
        if(shelvingDetailBean.getImages()!=null){
            for(int i=0;i<shelvingDetailBean.getImages().size();i++){
                list.add(new PhotoVideoBean(shelvingDetailBean.getImages().get(i),2));
            }
        }
        adapter.notifyDataSetChanged();
        etSerialNo.setText(shelvingDetailBean.getSerial_no());
        artisan.setText(shelvingDetailBean.getArtisan());
        num.setText(shelvingDetailBean.getAmount());
        price.setText(shelvingDetailBean.getPrice());
        if(bean.getDd_price()!=null&&bean.getDd_price().length()>0) {
            fenxiaoPrice.setText(shelvingDetailBean.getDd_price());
        }else {
            fenxiaoLiner.setVisibility(View.GONE);
        }
        weight.setText(shelvingDetailBean.getWeight()+"");
        length.setText(shelvingDetailBean.getLength()+"");
        width.setText(shelvingDetailBean.getWidth()+"");
        height.setText(shelvingDetailBean.getHeight()+"");
        fenlei.setText(shelvingDetailBean.getFenlei());
        zhonglei.setText(shelvingDetailBean.getZhonglei());
        chanzhuang.setText(shelvingDetailBean.getChanzhuang());
        ticai.setText(shelvingDetailBean.getTicai());
        pise.setText(shelvingDetailBean.getPise());
    }
    @Override
    public void getShopGoodsDetailFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.status+ "  "+e.message);
        Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }
}
