package com.yuanyu.ceramics.fenlei;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FenLeiActivity extends BaseActivity<FenLeiPresenter> implements FenLeiConstract.IFenleiView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview_classify)
    RecyclerView recyclerviewClassify;
    @BindView(R.id.recyclerview_cell)
    RecyclerView recyclerviewCell;
    @BindView(R.id.recyclerview_select)
    RecyclerView recyclerviewSelect;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.fenleibannar)
    RoundedImageView fenleibannar;

    private List<ClassifyBean> classify_list;
    private List<FenLeiBean> listfenlei, listzhonglei, listshape;
    private List<FenLeiBean> showList;
    private List<String> stringList;
    private ClassifyAdapter classifyAdapter;
    private FenLeiAdapter fenleCellAdapter;
    private BottomAdapter bottomAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_fen_lei;
    }

    @Override
    protected FenLeiPresenter initPresent() {
        return new FenLeiPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        title.setText("淘瓷神器");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        stringList=new ArrayList<>();
        classify_list=new ArrayList<>();
        listfenlei=new ArrayList<>();
        listzhonglei=new ArrayList<>();
        listshape=new ArrayList<>();
        showList=new ArrayList<>();
        presenter.initList(classify_list,listfenlei,listzhonglei, listshape);
        classifyAdapter=new ClassifyAdapter(classify_list,this);
        recyclerviewClassify.setAdapter(classifyAdapter);
        recyclerviewClassify.setLayoutManager(new LinearLayoutManager(this));
        classifyAdapter.setPositionClickListener(position -> {
            classifyAdapter.notifyDataSetChanged();
            if(position==0){presenter.setShowList(showList,listfenlei);}
            else if(position==1){presenter.setShowList(showList,listzhonglei);}
            else if(position==2){presenter.setShowList(showList, listshape);}
            fenleCellAdapter.notifyDataSetChanged();
        });
        presenter.setShowList(showList, listfenlei);
        fenleCellAdapter = new FenLeiAdapter(this, showList);
        recyclerviewCell.setAdapter(fenleCellAdapter);
        fenleCellAdapter.setOnCellClickListener(() -> {
            presenter.getStringList(stringList, listfenlei, listzhonglei, listshape);
            bottomAdapter.notifyDataSetChanged();
            boolean show = false;
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).length() > 0) {
                    show = true;
                }
            }
            if (show) {
                recyclerviewSelect.setVisibility(View.VISIBLE);
                submit.setText("确定");
                submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                recyclerviewSelect.setVisibility(View.INVISIBLE);
                submit.setText("请先选择一个分类");
                submit.setBackgroundColor(getResources().getColor(R.color.lightGray));
            }
        });
        recyclerviewCell.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerviewSelect.setVisibility(View.INVISIBLE);
        bottomAdapter = new BottomAdapter(stringList);
        recyclerviewSelect.setAdapter(bottomAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewSelect.setLayoutManager(manager);
        bottomAdapter.setOnClearClickListener(position -> {
            if (position == 0) {
                presenter.clearList(listfenlei);
            } else if (position == 1) {
                presenter.clearList(listzhonglei);
            } else if (position == 2) {
                presenter.clearList(listshape);
            }
            fenleCellAdapter.notifyDataSetChanged();
            boolean show = false;
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).length() > 0) {
                    show = true;
                }
            }
            if (show) {
                recyclerviewSelect.setVisibility(View.VISIBLE);
                submit.setText("确定");
                submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                recyclerviewSelect.setVisibility(View.INVISIBLE);
                submit.setText("请先选择一个分类");
                submit.setBackgroundColor(getResources().getColor(R.color.lightGray));
            }
        });
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

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if(submit.getText().toString().equals("确定")) {
            Intent intent = new Intent(FenLeiActivity.this, FenLeiResActivity.class);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < stringList.size(); i++) {
                sb.append(stringList.get(i) + "/");
            }
            intent.putExtra("querycode", sb.toString());
            intent.putExtra("codeone", stringList.get(0));
            intent.putExtra("codetwo", stringList.get(1));
            intent.putExtra("codethree", stringList.get(2));
            startActivity(intent);
        }else{
            Toast.makeText(this, "请至少选择一项", Toast.LENGTH_SHORT).show();
        }
    }
}
