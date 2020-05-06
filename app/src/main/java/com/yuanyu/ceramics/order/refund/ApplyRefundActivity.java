package com.yuanyu.ceramics.order.refund;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.ImageDisplayActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.UploadPhotoAdapter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.order.OrderDetailBean;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ApplyRefundActivity extends BaseActivity<ApplyRefundPresenter> implements ApplyRefundConstract.IApplyRefundView {

    private static final int REQUEST_CODE = 0x00000011;
    private static final int DISPLAY_IMAGE = 200;
    private static final int DELETE_IMAGE = 201;
    private static final int imageSize = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shop_portrait)
    CircleImageView shopPortrait;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.contact_shop)
    TextView contactShop;
    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.item_num)
    TextView itemNum;
    @BindView(R.id.item)
    RelativeLayout item;
    @BindView(R.id.refund_type)
    TextView refundType;
    @BindView(R.id.refund_reason)
    TextView refundReason;
    @BindView(R.id.refund_money)
    TextView refundMoney;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pic_num)
    TextView picNum;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.note)
    EditText note;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private String orderNum;
    private String shop_id;
    private String shop_userid;
    private String item_id;
    private boolean isinit=false;
    private UploadPhotoAdapter adapter;
    private ArrayList<String> list;
    private String addPic = "add_pic" + R.drawable.add_photo;
    private String system_time;
    private String finish_time;
    private int reason_position;
    private boolean popupwindow_type;
    private String price;
    private LoadingDialog dialog;
    private List<String> listimages = new ArrayList<>();
    @Override
    protected int getLayout() {return R.layout.activity_refund;}
    @Override
    protected ApplyRefundPresenter initPresent() {return new ApplyRefundPresenter();}
    @SuppressLint("RestrictedApi")
    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        dialog=new LoadingDialog(this);
        dialog.show();
        Intent intent = getIntent();
        orderNum = intent.getStringExtra("order_num");
        CantScrollGirdLayoutManager gm = new CantScrollGirdLayoutManager(this, 3);
        recyclerview.setLayoutManager(gm);
        list = new ArrayList<>();
        list.add(addPic);
        adapter = new UploadPhotoAdapter(this, list);
        adapter.setCancelListener(position -> {
            list.remove(position);
            if (!list.get(list.size() - 1).contains("add_pic")) list.add(addPic);
            adapter.notifyDataSetChanged();
            picNum.setText((list.size()-1)+"/3");
        });
        adapter.setOnItemClickListener((position, view) -> {
            if (adapter.getImages().get(position).contains("add_pic")) {
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(imageSize - adapter.getItemCount() + 1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(REQUEST_CODE);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent1 = new Intent(ApplyRefundActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent1.putExtra("image", adapter.getImages().get(position));
                    intent1.putExtra("position", position);
                    startActivityForResult(intent1, DISPLAY_IMAGE, ActivityOptions.makeSceneTransitionAnimation(ApplyRefundActivity.this, view, "displayImage").toBundle());
                } else {
                    Intent intent1 = new Intent(ApplyRefundActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent1.putExtra("image", adapter.getImages().get(position));
                    intent1.putExtra("position", position);
                    startActivityForResult(intent1, DISPLAY_IMAGE);
                }
            }
        });
        recyclerview.setAdapter(adapter);
        presenter.getOrderDetail(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), orderNum);
    }
    @Override
    public void getOrderDetailSuccess(OrderDetailBean bean) {
        shop_id=bean.getShop_id();
        shop_userid=bean.getShop_userid();
        price=bean.getPrice_pay();
        item_id=bean.getItem_list().get(0).getId();
        system_time=bean.getSystem_time();
        if(null!=bean.getFinish_time()&&bean.getFinish_time().length()>5){
            finish_time=bean.getFinish_time();
        }else{
            finish_time=bean.getSystem_time();
        }
        try{ if(Long.parseLong(system_time)- Long.parseLong(finish_time)>(7*24*60*60)) {
            popupwindow_type=false;
            reason_position=1;
            refundReason.setText("产品质量有问题");
            }else{
            popupwindow_type=true;
            reason_position=0;
            refundReason.setText("7天无理由");
            }
        }catch (Exception e){
            popupwindow_type=true;
            reason_position=0;
            refundReason.setText("7天无理由");
        }
        shopName.setText(bean.getShop_name());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getShop_portrait())
                .override(50, 50)
                .placeholder(R.drawable.img_default)
                .into(shopPortrait);
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getItem_list().get(0).getImage())
                .override(50, 50)
                .placeholder(R.drawable.img_default)
                .into(itemImage);
        itemName.setText(bean.getItem_list().get(0).getName());
        itemPrice.setText("¥" + bean.getItem_list().get(0).getPrice());
        itemNum.setText("x" + bean.getItem_list().get(0).getNum());
        if(bean.getDelivery_time()!=null&&bean.getDelivery_time().length()>6){
            refundType.setText("退货退款");
        }else{
            refundType.setText("仅退款");
        }
        refundMoney.setText("¥"+bean.getPrice_pay());
        isinit=true;
        dialog.dismiss();
    }

    @Override
    public void getOrderDetailFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+" "+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void compressImagesSuccess(List<File> list) {
        presenter.uploadImage(list);
    }

    @Override
    public void compressImagesFail() {
        listimages.clear();
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
        L.e("上传图片失败");
        dialog.dismiss();
    }

    @Override
    public void uploadImageSuccess(List<String> list) {
        RefundBean bean = new RefundBean(Sp.getString(ApplyRefundActivity.this, AppConstant.USER_ACCOUNT_ID), orderNum, item_id, refundType.getText().toString(), refundReason.getText().toString(),price, note.getText().toString(), list);
        presenter.submitRefund(bean);
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        listimages.clear();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
        L.e(e.message+" "+e.status);
        dialog.dismiss();
    }

    @Override
    public void submitRefundSuccess() {
        Toast.makeText(ApplyRefundActivity.this, "提交成功", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void submitRefundFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+ e.status);
        Toast.makeText(ApplyRefundActivity.this, e.message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            if ((images.size() + adapter.getItemCount()) == imageSize + 1) {
                list.remove(adapter.getItemCount() - 1);
                for (int i = images.size() - 1; i >= 0; i--) {
                    list.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
                picNum.setText((list.size())+"/3");
            } else {
                for (int i = images.size() - 1; i >= 0; i--) {
                    list.add(0, images.get(i).getPath());
                }
                picNum.setText((list.size()-1)+"/3");
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= list.size()) {
                if (list.get(list.size() - 1).contains("add_pic")) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    picNum.setText((list.size()-1)+"/3");
                } else {
                    list.remove(position);
                    list.add(addPic);
                    adapter.notifyDataSetChanged();
                    picNum.setText((list.size()-1)+"/3");
                }
            }
        }
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

    @OnClick({R.id.shop_portrait, R.id.shop_name, R.id.contact_shop, R.id.item, R.id.refund_reason, R.id.submit})
    public void onViewClicked(View view) {
        Intent intent;
        if(isinit) {
            switch (view.getId()) {
                case R.id.shop_portrait:
                case R.id.shop_name:
                    intent = new Intent(this, ShopIndexActivity.class);
                    intent.putExtra(AppConstant.SHOP_ID, shop_id);
                    startActivity(intent);
                    break;
                case R.id.contact_shop:
//                    ChatActivity.navToChat(this, shop_userid, TIMConversationType.C2C);
                    break;
                case R.id.item:
//                    ItemDetailActivity.actionStart(this, item_id);
                    break;
                case R.id.refund_reason:
                    ApplyRefundReasonPopupWindow popupWindow = new ApplyRefundReasonPopupWindow(this,popupwindow_type,reason_position);
                    popupWindow.showAtLocation(root, Gravity.BOTTOM,0,0);
                    popupWindow.setOnStringCallback(position -> {
                        reason_position=position;
                        if(position==0){refundReason.setText("7天无理由");}
                        else if(position==1){refundReason.setText("产品质量有问题");}
                        else if(position==2){refundReason.setText("虚假发货");}
                        else if(position==3){refundReason.setText("产品与描述不符");}
                    });
                    break;
                case R.id.submit:
                    int count;
                    if (list.get(list.size() - 1).contains("add_pic")) count = list.size() - 1;
                    else count = list.size();
                    for (int i = 0; i < count; i++) {
                        listimages.add(list.get(i));
                    }
                    dialog.show();
                    presenter.compressImages(this,listimages);
                    break;
            }
        }
    }

}
