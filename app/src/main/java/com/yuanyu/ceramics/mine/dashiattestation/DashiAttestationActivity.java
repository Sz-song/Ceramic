package com.yuanyu.ceramics.mine.dashiattestation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.ImageDisplayActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashiAttestationActivity extends BaseActivity<DashiAttestationPresenter> implements DashiAttestationConstract.IDashiAttestationView  {

    private static final int REQUEST_CODE = 10080;
    private static final int DISPLAY_IMAGE = 200;
    private static final int DELETE_IMAGE = 201;
    private static final int imageSize = 8;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.idcard)
    EditText idcard;
    @BindView(R.id.good_at)
    EditText goodAt;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.submit)
    TextView submit;
    private DashiAttestationAdapter adapter;
    private List<String> list;
    private String pic = "add_pic" + R.drawable.upload_zhengshu;
    private LoadingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_dashi_attestation;
    }

    @Override
    protected DashiAttestationPresenter initPresent() {
        return new  DashiAttestationPresenter();
    }

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
        list = new ArrayList<>();
        dialog = new LoadingDialog(this);
        list.add(pic);
        recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(this,4));
        adapter = new DashiAttestationAdapter(this, list);
        recyclerview.setAdapter(adapter);
        adapter.setOnPositionClickListener(position -> {
            if (list.get(position).contains("add_pic")) {
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(imageSize - adapter.getItemCount() + 1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(REQUEST_CODE);
            } else {
                Intent intent = new Intent(DashiAttestationActivity.this, ImageDisplayActivity.class);
                intent.putExtra("image", list.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, DISPLAY_IMAGE);
            }
        });
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
            } else {
                for (int i = images.size() - 1; i >= 0; i--) {
                    list.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= list.size()) {
                if (list.get(list.size() - 1).contains("add_pic")) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    list.remove(position);
                    list.add(pic);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void uploadImageSuccess(List<String> list) {
        presenter.MasterAttestation(Sp.getString(DashiAttestationActivity.this, "useraccountid"), name.getText().toString(), tel.getText().toString(),idcard.getText().toString(),goodAt.getText().toString(), list);
    }

    @Override
    public void MasterAttestationSuccess(String[] strings) {
        dialog.dismiss();
        submit.setClickable(true);
        Intent intent = new Intent(DashiAttestationActivity.this, SubmitSuccessActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + "  " + e.message);
        Toast.makeText(this, "图片上传失败，请稍后再试", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void MasterAttestationFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + " " + e.message);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (name.getText().toString().trim().length()==0) {
            Toast.makeText(this, "请输入名称", Toast.LENGTH_SHORT).show();
        } else if(tel.getText().toString().trim().length()==0) {
            Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
        }else if(idcard.getText().toString().trim().length()!=18){
            Toast.makeText(this, "请输入正确身份证号", Toast.LENGTH_SHORT).show();
        }else if(goodAt.getText().toString().trim().length()==0){
            Toast.makeText(this, "请输入擅长领域", Toast.LENGTH_SHORT).show();
        }else {
            if (list.size() == 1) {
                Toast.makeText(this, "请上传大师认证图片资料", Toast.LENGTH_SHORT).show();
            } else if (list.size() > 1) {
                if (list.get(list.size() - 1).contains("add_pic")) {
                    list.remove(list.size() - 1);
                    dialog.show();
                    submit.setClickable(false);
                    presenter.compressImages(DashiAttestationActivity.this, list);
                } else {
                    dialog.show();
                    submit.setClickable(false);
                    presenter.compressImages(DashiAttestationActivity.this, list);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
