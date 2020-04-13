package com.yuanyu.ceramics.common;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
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

public class ReportActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.report_content)
    EditText content;
    @BindView(R.id.report_contact)
    EditText contact;
    @BindView(R.id.report_submit)
    Button submit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private static final int REQUEST_CODE = 0x00000011;
    private static final int DISPLAY_IMAGE = 200;
    private static final int DELETE_IMAGE = 201;
    private static final int imageSize = 3;
    private CommonModel model;
    private Intent intent;
    private String id;
    private int type;
    private ArrayList<String> mList;
    private UploadPhotoAdapter adapter;
    private List<String> listimages=new ArrayList<>();
    private String addPic = "add_pic" + R.drawable.add_pic;
    LoadingDialog dialog;
    List<File> file=new ArrayList<>();
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void initEvent() {

    }
    public static void actionStart(Context context, String id, int type) {
        Intent intent = new Intent(context, ReportActivity.class);//这里的Target.class是启动目标Activity
        intent.putExtra("id", id);//被举报事物的id
        intent.putExtra("type", type);
        /** 举报类型* -1其他，0动态，1帖子，2作品，3文章，4评论,5人，6群，7商品，8拍品，9直播，10店铺（工作室）11.店铺恶评**/
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
        initRecycle();
    }

    private void initView() {
        intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getIntExtra("type",-1);
        model = new CommonModel();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back1_gray);
        dialog=new LoadingDialog(this);

    }
    private void initRecycle() {
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mList = new ArrayList<>();
        mList.add(addPic);
        adapter = new UploadPhotoAdapter(ReportActivity.this, mList);
        adapter.setCancelListener(position -> {
            mList.remove(position);
            if (!mList.get(mList.size() - 1).contains("add_pic")) mList.add(addPic);
            adapter.notifyDataSetChanged();
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
                    Intent intent = new Intent(ReportActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE, ActivityOptions.makeSceneTransitionAnimation(ReportActivity.this, view, "displayImage").toBundle());
                } else {
                    Intent intent = new Intent(ReportActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE);
                }
            }
        });
        recyclerview.setAdapter(adapter);

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

    @OnClick(R.id.report_submit)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.report_submit:
                if (content.getText().toString().trim().length() < 1) {
                    Toast.makeText(this, "内容描述不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (mList.size() == 1) {
                        mList.remove(0);
                        dialog.show();
                        submit();
                    } else if (mList.size() > 1 && mList.size() < 3) {
                        mList.remove(mList.size() - 1);
                        dialog.show();
                        compressImage(mList);
                    } else {
                        dialog.show();
                        compressImage(mList);
                    }
                    break;
                }
        }
    }
    @SuppressLint("CheckResult")
    public void compressImage(final List<String> image) {
        Flowable.just(image).observeOn(Schedulers.io())
                .map(list -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(ReportActivity.this).load(list).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    file.clear();
                    file.addAll(files);
                    uploadImage(file);
                });

    }
    public void uploadImage(List<File> images) {
        model.uploadImage(images).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("error is " + e.status + e.message);
                        dialog.dismiss();
                        Toast.makeText(ReportActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(List<String> listimg) {
                        for(int i=0;i<listimg.size();i++) {
                            listimages.add(listimg.get(i));
                        }
                        submit();
                    }
                });
    }
    private void submit() {
        model.submitReport(Sp.getInt(this, "useraccountid"), id, type, content.getText().toString(), contact.getText().toString(), listimages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("error is " + e.message + e.status);
                        Toast.makeText(ReportActivity.this, "提交失败,请稍候重试", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(String[] list) {
                        Toast.makeText(ReportActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        finish();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
