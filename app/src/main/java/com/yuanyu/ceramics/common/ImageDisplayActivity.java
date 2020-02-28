package com.yuanyu.ceramics.common;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.photoview.PhotoView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ImageDisplayActivity extends AppCompatActivity {
    private static final int DELETE_IMAGE = 201;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoview)
    PhotoView photoview;
    Intent intent;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("图片预览");
        intent = getIntent();
        String image = intent.getStringExtra("image");
        if (image.startsWith("img/")) {
            GlideApp.with(this)
                    .load(BASE_URL + image)
                    .fitCenter()
                    .placeholder(R.drawable.img_default)
                    .into(photoview);
        } else {
            GlideApp.with(this)
                    .load(new File(image))
                    .fitCenter()
                    .into(photoview);
        }
    }

    @OnClick(R.id.photoview)
    void onClick(View view) {
        ActivityCompat.finishAfterTransition(ImageDisplayActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                setResult(DELETE_IMAGE, intent);
                ActivityCompat.finishAfterTransition(ImageDisplayActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
