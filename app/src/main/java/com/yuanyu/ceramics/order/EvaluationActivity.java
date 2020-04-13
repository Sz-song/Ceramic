package com.yuanyu.ceramics.order;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.ImageDisplayActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.UploadPhotoAdapter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class EvaluationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.haoping)
    TextView haoping;
    @BindView(R.id.zhongping)
    TextView zhongping;
    @BindView(R.id.chaping)
    TextView chaping;
    @BindView(R.id.publish)
    TextView publish;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int DISPLAY_IMAGE = 200;
    private static final int DELETE_IMAGE = 201;
    private static final int imageSize = 3;
    private Intent intent;
    private String order_num;
    private String commodityId;
    private String shopId;
    private int rank = 1; //1：好评；2：中评；3：差评
    private MyOrderFragmentModel model = new MyOrderFragmentModel();
    private ArrayList<String> mList = new ArrayList<>();
    private String addPic = "add_pic" + R.drawable.add_pic;
    private UploadPhotoAdapter adapter;
    private LoadingDialog dialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("RestrictedApi")
    public void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back1_gray);
        intent = getIntent();
        order_num = intent.getStringExtra("order_num");
        commodityId = intent.getStringExtra("commodity_id");
        shopId = intent.getStringExtra("shop_id");
        L.e("shop id is:"+shopId);
        dialog=new LoadingDialog(this);
        publish.setOnClickListener(view -> {
            if (comment.getText().toString().trim().equals("")) Toast.makeText(EvaluationActivity.this,"评价不能为空", Toast.LENGTH_SHORT).show();
            else {
                List<String> images = new ArrayList<>();
                int count;
                if (mList.get(mList.size() - 1).contains("add_pic")) count = mList.size() - 1;
                else count = mList.size();
                for (int i = 0; i < count; i++) {
                    images.add(mList.get(i));
                    L.e("data is " + mList.get(i));
                }
                dialog.show();
                compressImage(images);
            }
        });

        recyclerview.setLayoutManager(new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mList.add(addPic);
        adapter = new UploadPhotoAdapter(EvaluationActivity.this, mList);
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
                    Intent intent = new Intent(EvaluationActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE, ActivityOptions.makeSceneTransitionAnimation(EvaluationActivity.this, view, "displayImage").toBundle());
                } else {
                    Intent intent = new Intent(EvaluationActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE);
                }
            }
        });
        recyclerview.setAdapter(adapter);


    }

    @OnClick({R.id.haoping, R.id.zhongping, R.id.chaping})
    void onClick(View view) {
        haoping.setBackgroundResource(R.drawable.button_style5);
        zhongping.setBackgroundResource(R.drawable.button_style5);
        chaping.setBackgroundResource(R.drawable.button_style5);
        view.setBackgroundResource(R.drawable.r10_stpri_sowhite);
        switch (view.getId()) {
            case R.id.haoping:
                rank = 1;
                break;
            case R.id.zhongping:
                rank = 2;
                break;
            case R.id.chaping:
                rank = 3;
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //压缩图片
    @SuppressLint("CheckResult")
    public void compressImage(List<String> image) {
        Flowable.just(image).observeOn(Schedulers.io())
                .map(list -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(EvaluationActivity.this).load(list).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> uploadImage(files));
    }


    //上传图片
    public void uploadImage(List<File> images) {
        model.uploadImage(images).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("error is "+e.status+e.message);
                        Toast.makeText(EvaluationActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    @Override
                    public void onNext(List<String> list) {
                        submit(list);
                    }
                });
    }



    public void submit(List<String> images){
        L.e("shop id is:"+shopId);
        model.submitEvaluation(1,"",comment.getText().toString().trim(),images,shopId,rank,commodityId,
                1, Sp.getString(this, AppConstant.USER_ACCOUNT_ID),order_num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        dialog.dismiss();
                        Toast.makeText(EvaluationActivity.this,"评价失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Object o) {
                        dialog.dismiss();
                        Toast.makeText(EvaluationActivity.this,"评价成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            if ((images.size() + adapter.getItemCount()) == imageSize + 1) {
                mList.remove(adapter.getItemCount() - 1);
                for (int i = images.size() - 1; i >= 0; i--) {
                    mList.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
            } else {
                for (int i = images.size() - 1; i >= 0; i--) {
                    mList.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= mList.size()) {
                if (mList.get(mList.size() - 1).contains("add_pic")) {
                    mList.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    mList.remove(position);
                    mList.add(addPic);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
