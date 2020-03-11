package com.yuanyu.ceramics.seller.shop_shelve.re_shelve;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.FenleiTypeAdapter2;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.ImageDisplayActivity;
import com.yuanyu.ceramics.common.ImageHelper2;
import com.yuanyu.ceramics.common.InputNumScopeFilter;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.NotNullTextWatcher;
import com.yuanyu.ceramics.common.OnItemClickListener;
import com.yuanyu.ceramics.common.PhotoVedioAdapter;
import com.yuanyu.ceramics.common.PhotoVideoBean;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.common.VideoDisplayActivity;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReShelveActivity extends BaseActivity<ReShelvePresenter> implements ReShelveConstract.IReShelveView {
    private static final int SELECT_IMAGE_CODE = 1000;
    private static final int SELECT_VIDEO_CODE = 1001;
    private static final int DISPLAY_IMAGE = 1002;
    private static final int DELETE_IMAGE = 201;
    private static final int DISPLAY_VIDEO = 1004;
    private static final int DELETE_VIDEO = 1005;
    private static final int imageSize = 8;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fail_reason)
    TextView failReason;
    @BindView(R.id.input_title_edit)
    EditText inputTitleEdit;
    @BindView(R.id.title_tag)
    TextView titleTag;
    @BindView(R.id.input_content_edit)
    EditText inputContentEdit;
    @BindView(R.id.content_tag)
    TextView contentTag;
    @BindView(R.id.recy_addimage)
    RecyclerView recyAddimage;
    @BindView(R.id.addimage_num)
    TextView addimageNum;
    @BindView(R.id.artisan)
    EditText artisan;
    @BindView(R.id.relat_jiangren)
    RelativeLayout relatJiangren;
    @BindView(R.id.temp_num)
    TextView tempNum;
    @BindView(R.id.reduce)
    TextView reduce;
    @BindView(R.id.num)
    EditText num;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.price_tag)
    TextView priceTag;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.fenlei_tag)
    TextView fenleiTag;
    @BindView(R.id.fenlei)
    EditText fenlei;
    @BindView(R.id.fenlei_rec)
    RecyclerView fenleiRec;
    @BindView(R.id.zhonglei_tag)
    TextView zhongleiTag;
    @BindView(R.id.zhonglei)
    EditText zhonglei;
    @BindView(R.id.zhonglei_rec)
    RecyclerView zhongleiRec;
    @BindView(R.id.ticai)
    EditText ticai;
    @BindView(R.id.ticai_rec)
    RecyclerView ticaiRec;
    @BindView(R.id.weight_tag)
    TextView weightTag;
    @BindView(R.id.weight)
    EditText weight;
    @BindView(R.id.shape_tag)
    TextView shapeTag;
    @BindView(R.id.length)
    EditText length;
    @BindView(R.id.width)
    EditText width;
    @BindView(R.id.height)
    EditText height;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<FenleiTypeBean> fenleiList, ticaiList, zhongleiList;
    private FenleiTypeAdapter2 fenleiAdapter, ticaiAdapter, zhongleiAdapter;
    private InputMethodManager imm;
    private PhotoVedioAdapter adapter;
    private LoadingDialog dialog;
    private Drawable triangleRight;
    private Drawable triangleDown;
    private ShelvingDetailBean bean;
    private List<PhotoVideoBean> list;
    private String id = "";
    private String dd_id = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_re_shelve;
    }

    @Override
    protected ReShelvePresenter initPresent() {
        return new ReShelvePresenter();
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
        title.setText("上架");
        if (getIntent().getStringExtra("failreason") != null && getIntent().getStringExtra("failreason").length() > 0) {
            failReason.setVisibility(View.VISIBLE);
            failReason.setText("失败原因:" + getIntent().getStringExtra("failreason"));
        } else {
            failReason.setVisibility(View.GONE);
        }
        swipe.setColorSchemeResources(R.color.colorPrimary);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        triangleRight = getResources().getDrawable(R.mipmap.trianglerigth_black);
        triangleDown = getResources().getDrawable(R.mipmap.triangledown_black);
        triangleRight.setBounds(0, 0, triangleRight.getMinimumWidth(), triangleRight.getMinimumHeight());
        triangleDown.setBounds(0, 0, triangleDown.getMinimumWidth(), triangleDown.getMinimumHeight());
        dialog = new LoadingDialog(this);
        dialog.show();
        list = new ArrayList<>();
        bean = new ShelvingDetailBean();
        num.setFilters(new InputFilter[]{new InputNumScopeFilter(1, 999999)});
        adapter = new PhotoVedioAdapter(this, list, true);
        recyAddimage.setLayoutManager(new CantScrollGirdLayoutManager(this, 3));
        recyAddimage.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Intent intent;
            switch (list.get(position).getType()) {
                case 0://选择图片
                    PictureSelector.create(ReShelveActivity.this).openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(imageSize - adapter.getItemCount() + 2)
                            .forResult(SELECT_IMAGE_CODE);
                    break;
                case 1://选择视频
                    PictureSelector.create(ReShelveActivity.this).openGallery(PictureMimeType.ofVideo())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(1)
                            .videoMaxSecond(16)
                            .forResult(SELECT_VIDEO_CODE);
                    break;
                case 2://图片预览
                    intent = new Intent(ReShelveActivity.this, ImageDisplayActivity.class);
                    intent.putExtra("image", list.get(position).getUrl());
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE);
                    break;
                case 3://视频预览
                    intent = new Intent(ReShelveActivity.this, VideoDisplayActivity.class);
                    intent.putExtra("video", list.get(position).getUrl());
                    intent.putExtra("cover", bean.getVideo_cover() + "");
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_VIDEO);
                    break;
            }
        });
        adapter.setOnDeteleClickListener(position -> {
            if (list.get(position).getType() == 2) {
                if (list.get(list.size() - 1).getType() == 0) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    int n = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                            n++;
                        }
                    }
                    addimageNum.setText(n + "/9");
                } else {
                    list.remove(position);
                    list.add(new PhotoVideoBean(R.drawable.add_pic + "", 0));
                    adapter.notifyDataSetChanged();
                    int n = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                            n++;
                        }
                    }
                    addimageNum.setText(n + "/9");
                }
            } else if (list.get(position).getType() == 3) {
                list.remove(0);
                list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
                adapter.notifyDataSetChanged();
                int n = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                        n++;
                    }
                }
                addimageNum.setText(n + "/9");
            }
        });
        ImageHelper2 imageHelper = new ImageHelper2(adapter, list);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(imageHelper);
        itemTouchHelper.attachToRecyclerView(recyAddimage);
        recyAddimage.addOnItemTouchListener(new OnItemClickListener(recyAddimage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (list.get(vh.getLayoutPosition()).getType() == 2) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
        inputTitleEdit.addTextChangedListener(new NotNullTextWatcher(titleTag));
        inputContentEdit.addTextChangedListener(new NotNullTextWatcher(contentTag));
        price.addTextChangedListener(new NotNullTextWatcher(priceTag));
        fenlei.addTextChangedListener(new NotNullTextWatcher(fenleiTag));
        zhonglei.addTextChangedListener(new NotNullTextWatcher(zhongleiTag));
//        chanzhuang.addTextChangedListener(new NotNullTextWatcher(chanzhaungTag));
        weight.addTextChangedListener(new NotNullTextWatcher(weightTag));
        length.addTextChangedListener(new ShapeTextWacher());
        width.addTextChangedListener(new ShapeTextWacher());
        height.addTextChangedListener(new ShapeTextWacher());
        fenleiList = new ArrayList<>();
        ticaiList = new ArrayList<>();
        zhongleiList = new ArrayList<>();
        presenter.initList(fenleiList, ticaiList, zhongleiList);
        fenleiAdapter = new FenleiTypeAdapter2(this, fenleiList);
        fenleiAdapter.setClickListener(position -> {
            fenlei.setText(fenleiList.get(position).getType());
            fenlei.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(fenleiRec, 500, false);
            fenleiRec.setTag("0");
        });
        fenleiRec.setAdapter(fenleiAdapter);
        fenleiRec.setLayoutManager(new CantScrollGirdLayoutManager(this, 4));

        zhongleiAdapter = new FenleiTypeAdapter2(this, zhongleiList);
        zhongleiAdapter.setClickListener(position -> {
            zhonglei.setText(zhongleiList.get(position).getType());
            zhonglei.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(zhongleiRec, 500, false);
            zhongleiRec.setTag("0");
        });
        zhongleiRec.setAdapter(zhongleiAdapter);
        zhongleiRec.setLayoutManager(new CantScrollGirdLayoutManager(this, 4));

        ticaiAdapter = new FenleiTypeAdapter2(this, ticaiList);
        ticaiAdapter.setClickListener(position -> {
            ticai.setText(ticaiList.get(position).getType());
            ticai.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(ticaiRec, 500, false);
            ticaiRec.setTag("0");
        });
        ticaiRec.setAdapter(ticaiAdapter);
        ticaiRec.setLayoutManager(new CantScrollGirdLayoutManager(this, 4));

//        chanzhuangAdapter = new FenleiTypeAdapter2(this, chanzhuangList);
//        chanzhuangAdapter.setClickListener(position -> {
//            chanzhuang.setText(chanzhuangList.get(position).getType());
//            chanzhuang.setCompoundDrawables(null, null, triangleRight, null);
//            performAnime(chanzhuangRec, 500, false);
//            chanzhuangRec.setTag("0");
//        });
//        chanzhuangRec.setAdapter(chanzhuangAdapter);
//        chanzhuangRec.setLayoutManager(new CantScrollGirdLayoutManager(this, 4));

//        piseAdapter = new FenleiTypeAdapter2(this, piseList);
//        piseAdapter.setClickListener(position -> {
//            pise.setText(piseList.get(position).getType());
//            pise.setCompoundDrawables(null, null, triangleRight, null);
//            performAnime(piseRec, 500, false);
//            piseRec.setTag("0");
//        });
//        piseRec.setAdapter(piseAdapter);
//        piseRec.setLayoutManager(new CantScrollGirdLayoutManager(this, 4));
        if (getIntent().getStringExtra("id") != null && getIntent().getStringExtra("id").length() > 0) {
            id = getIntent().getStringExtra("id");
            presenter.getReShelvingData(Sp.getString(this, AppConstant.SHOP_ID), id);
            swipe.setOnRefreshListener(() -> {
                cleanData();
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.getReShelvingData(Sp.getString(this, AppConstant.SHOP_ID), id);
            });
        }
        if (getIntent().getStringExtra("dd_id") != null && getIntent().getStringExtra("dd_id").length() > 0) {
            dd_id = getIntent().getStringExtra("dd_id");
//            presenter.getFenxiaoGoodData(Sp.getString(this, AppConstant.SHOP_ID), dd_id);
            swipe.setOnRefreshListener(() -> {
                cleanData();
                list.clear();
                adapter.notifyDataSetChanged();
//                presenter.getFenxiaoGoodData(Sp.getString(this, AppConstant.SHOP_ID), dd_id);
            });
        }
    }


    @Override
    public void getReShelvingDataSuccess(ShelvingDetailBean shelvingDetailBean) {
        dialog.dismiss();
        bean.setVideo_cover(shelvingDetailBean.getVideo_cover());
//        bean.setDd_price(shelvingDetailBean.getDd_price());
//        if(bean.getDd_price().trim().length()>0){
//            switchBtn.setChecked(true);
//            fenxiaoPrice.setText(bean.getDd_price());
//        }
        inputTitleEdit.setText(shelvingDetailBean.getTitle());
        inputContentEdit.setText(shelvingDetailBean.getDescription());
        swipe.setRefreshing(false);
        int num_image = 0;
        if (shelvingDetailBean.getVideo() != null && shelvingDetailBean.getVideo().length() > 0) {
            list.add(new PhotoVideoBean(shelvingDetailBean.getVideo(), 3));
            num_image++;
        } else {
            list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
        }
        for (int i = 0; i < shelvingDetailBean.getImages().size(); i++) {
            list.add(new PhotoVideoBean(shelvingDetailBean.getImages().get(i), 2));
            num_image++;
        }
        if (list.get(list.size() - 1).getType() != 0 && list.size() < 9) {
            list.add(new PhotoVideoBean(R.drawable.add_pic + "", 0));
        }
        adapter.notifyDataSetChanged();
        addimageNum.setText(num_image + "/9");
        try {
            if (Integer.parseInt(shelvingDetailBean.getAmount()) < 1) {
                num.setText("1");
            } else {
                num.setText(shelvingDetailBean.getAmount());
            }
        } catch (Exception e) {
            num.setText("1");
        }
        artisan.setText(shelvingDetailBean.getArtisan().trim());
        price.setText(shelvingDetailBean.getPrice());
//        etSerialNo.setText(shelvingDetailBean.getSerial_no());
        weight.setText(shelvingDetailBean.getWeight() + "");
        length.setText(shelvingDetailBean.getLength() + "");
        width.setText(shelvingDetailBean.getWidth() + "");
        height.setText(shelvingDetailBean.getHeight() + "");
        fenlei.setText(shelvingDetailBean.getFenlei());
        for (int i = 0; i < fenleiList.size(); i++) {
            if (shelvingDetailBean.getFenlei().equals(fenleiList.get(i).getType())) {
                fenleiList.get(i).setChoose(true);
                fenleiAdapter.notifyDataSetChanged();
                break;
            }
        }
        zhonglei.setText(shelvingDetailBean.getZhonglei());
        for (int i = 0; i < zhongleiList.size(); i++) {
            if (shelvingDetailBean.getZhonglei().equals(zhongleiList.get(i).getType())) {
                zhongleiList.get(i).setChoose(true);
                zhongleiAdapter.notifyDataSetChanged();
                break;
            }
        }
        ticai.setText(shelvingDetailBean.getTicai());
        for (int i = 0; i < ticaiList.size(); i++) {
            if (shelvingDetailBean.getTicai().equals(ticaiList.get(i).getType())) {
                ticaiList.get(i).setChoose(true);
                ticaiAdapter.notifyDataSetChanged();
                break;
            }
        }
//        chanzhuang.setText(shelvingDetailBean.getChanzhuang());
//        for (int i = 0; i < chanzhuangList.size(); i++) {
//            if (shelvingDetailBean.getChanzhuang().equals(chanzhuangList.get(i).getType())) {
//                chanzhuangList.get(i).setChoose(true);
//                chanzhuangAdapter.notifyDataSetChanged();
//                break;
//            }
//        }
//        pise.setText(shelvingDetailBean.getPise());
//        for (int i = 0; i < piseList.size(); i++) {
//            if (shelvingDetailBean.getPise().equals(piseList.get(i).getType())) {
//                piseList.get(i).setChoose(true);
//                piseAdapter.notifyDataSetChanged();
//                break;
//            }
//        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    list.add(list.size() - 1, new PhotoVideoBean(selectList.get(i).getPath(), 2));
                }
                if (list.size() > 9) {
                    list.remove(9);
                }
            }
            int n = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                    n++;
                }
            }
            addimageNum.setText(n + "/9");
            adapter.notifyDataSetChanged();
        } else if (requestCode == SELECT_VIDEO_CODE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                list.set(0, new PhotoVideoBean(selectList.get(0).getPath(), 3));
            }
            int n = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                    n++;
                }
            }
            addimageNum.setText(n + "/9");
            adapter.notifyDataSetChanged();
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= list.size()) {
                if (list.get(list.size() - 1).getType() == 0) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    int n = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                            n++;
                        }
                    }
                    addimageNum.setText(n + "/9");
                } else {
                    list.remove(position);
                    list.add(new PhotoVideoBean(R.drawable.add_pic + "", 0));
                    adapter.notifyDataSetChanged();
                    int n = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                            n++;
                        }
                    }
                    addimageNum.setText(n + "/9");
                }
            }
        } else if (requestCode == DISPLAY_VIDEO && resultCode == DELETE_VIDEO) {
            list.remove(0);
            list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
            int n = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType() == 2 || list.get(i).getType() == 3) {
                    n++;
                }
            }
            addimageNum.setText(n + "/9");
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.submit, R.id.add, R.id.reduce, R.id.fenlei, R.id.zhonglei, R.id.ticai})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fenlei:
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (fenleiRec.getTag().equals("0")) {
                    fenlei.setCompoundDrawables(null, null, triangleDown, null);
                    performAnime(fenleiRec, 500, true);
                    fenleiRec.setTag("1");
                } else {
                    fenlei.setCompoundDrawables(null, null, triangleRight, null);
                    performAnime(fenleiRec, 500, false);
                    fenleiRec.setTag("0");
                }
                break;
            case R.id.zhonglei:
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (zhongleiRec.getTag().equals("0")) {
                    zhonglei.setCompoundDrawables(null, null, triangleDown, null);
                    performAnime(zhongleiRec, 500, true);
                    zhongleiRec.setTag("1");
                } else {
                    zhonglei.setCompoundDrawables(null, null, triangleRight, null);
                    performAnime(zhongleiRec, 500, false);
                    zhongleiRec.setTag("0");
                }
                break;
//            case R.id.pise:
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                if (piseRec.getTag().equals("0")) {
//                    pise.setCompoundDrawables(null, null, triangleDown, null);
//                    performAnime(piseRec, 350, true);
//                    piseRec.setTag("1");
//                } else {
//                    pise.setCompoundDrawables(null, null, triangleRight, null);
//                    performAnime(piseRec, 350, false);
//                    piseRec.setTag("0");
//                }
//                break;
            case R.id.ticai:
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (ticaiRec.getTag().equals("0")) {
                    ticai.setCompoundDrawables(null, null, triangleDown, null);
                    performAnime(ticaiRec, 500, true);
                    ticaiRec.setTag("1");
                } else {
                    ticai.setCompoundDrawables(null, null, triangleRight, null);
                    performAnime(ticaiRec, 500, false);
                    ticaiRec.setTag("0");
                }
                break;
//            case R.id.chanzhuang:
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                if (chanzhuangRec.getTag().equals("0")) {
//                    chanzhuang.setCompoundDrawables(null, null, triangleDown, null);
//                    performAnime(chanzhuangRec, 350, true);
//                    chanzhuangRec.setTag("1");
//                } else {
//                    chanzhuang.setCompoundDrawables(null, null, triangleRight, null);
//                    performAnime(chanzhuangRec, 350, false);
//                    chanzhuangRec.setTag("0");
//                }
//                break;
            case R.id.submit:
                if (!isEmpty()) {
                    submit.setEnabled(false);
                    dialog.show();
                    L.e("before init list images");
                    List<String> imageList = new ArrayList<>();
                    if (list.get(list.size() - 1).getType() == 2) {
                        for (int i = 1; i < list.size(); i++) {
                            if (list.get(i).getUrl().startsWith("img/")) {
                                bean.getImages().add(list.get(i).getUrl());
                            } else {
                                imageList.add(list.get(i).getUrl());
                            }
                        }
                    } else if (list.get(list.size() - 1).getType() == 0) {
                        for (int i = 1; i < list.size() - 1; i++) {
                            if (list.get(i).getUrl().startsWith("img/")) {
                                bean.getImages().add(list.get(i).getUrl());
                            } else {
                                imageList.add(list.get(i).getUrl());
                            }
                        }
                    }
                    L.e("before compress images" + imageList.size());
                    presenter.compressImages(ReShelveActivity.this, imageList);
                }
                break;
            case R.id.reduce:
                try {
                    if ((Integer.parseInt(num.getText().toString()) - 1) < 1) {
                        num.setText("1");
                    } else {
                        num.setText((Integer.parseInt(num.getText().toString()) - 1) + "");
                    }
                } catch (Exception e) {
                    num.setText("1");
                }
                break;
            case R.id.add:
                try {
                    num.setText((Integer.parseInt(num.getText().toString()) + 1) + "");
                } catch (Exception e) {
                    num.setText("1");
                }
                break;
        }
    }

    private void cleanData() {
        bean.setNoData();
        inputTitleEdit.setText("");
        inputContentEdit.setText("");
        list.clear();
        list.add(0, new PhotoVideoBean(R.drawable.add_pic + "", 0));
        list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
        adapter.notifyDataSetChanged();
        artisan.setText("");
        price.setText("");
        num.setText("1");
        addimageNum.setText("0/9");
        fenlei.setText("");
        zhonglei.setText("");
//        pise.setText("");
//        chanzhuang.setText("");
        ticai.setText("");
//        etSerialNo.setText("");
        length.setText("");
        width.setText("");
        height.setText("");
        for (FenleiTypeBean bean : fenleiList) bean.setChoose(false);
        for (FenleiTypeBean bean : zhongleiList) bean.setChoose(false);
        for (FenleiTypeBean bean : ticaiList) bean.setChoose(false);
        fenleiAdapter.notifyDataSetChanged();
        zhongleiAdapter.notifyDataSetChanged();
        ticaiAdapter.notifyDataSetChanged();
    }

    private boolean isEmpty() {
        if (!(inputTitleEdit.getText().toString().trim().length() > 0)) {
            Toast.makeText(this, "请填写商品名称", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(inputContentEdit.getText().toString().trim().length() > 0)) {
            Toast.makeText(this, "请填写商品描述", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(list.size() > 2)) {
            Toast.makeText(this, "请上传图片", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(num.getText().toString().trim().length() > 0)) {
            Toast.makeText(this, "请选择库存信息", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(price.getText().toString().trim().length() > 0)) {
            Toast.makeText(this, "请填写价格", Toast.LENGTH_SHORT).show();
            return true;
        }
//        else if (switchRelat.getVisibility() == View.VISIBLE && switchBtn.isChecked() && !(fenxiaoPrice.getText().toString().trim().length() > 0)) {
//            Toast.makeText(this, "请填写分销价格", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else if (chanzhuang.getText().toString().trim().length() == 0) {
//            Toast.makeText(this, "请选择产状", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        else if (fenlei.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show();
            return true;
        } else if (zhonglei.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请选择种类", Toast.LENGTH_SHORT).show();
            return true;
        } else if (Double.parseDouble(price.getText().toString()) < 0.01) {
            Toast.makeText(this, "价格不能为0", Toast.LENGTH_SHORT).show();
            return true;
        } else if (length.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入长度", Toast.LENGTH_SHORT).show();
            return true;
        } else if (width.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入宽度", Toast.LENGTH_SHORT).show();
            return true;
        } else if (height.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入高度", Toast.LENGTH_SHORT).show();
            return true;
        } else if (weight.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入重量", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            bean.setTitle(inputTitleEdit.getText().toString());
            bean.setDescription(inputContentEdit.getText().toString());
            bean.setArtisan(artisan.getText().toString().trim());
            bean.setPrice(price.getText().toString());
            bean.setAmount(num.getText().toString());
//            bean.setDd_price(fenxiaoPrice.getText().toString());
            bean.setFenlei(fenlei.getText().toString());
            bean.setZhonglei(zhonglei.getText().toString());
//            bean.setChanzhuang(chanzhuang.getText().toString());
//            bean.setPise(pise.getText().toString());
            bean.setTicai(ticai.getText().toString());
            bean.setWeight(Double.parseDouble(weight.getText().toString()));
            bean.setWidth(Integer.parseInt(width.getText().toString()));
            bean.setLength(Integer.parseInt(length.getText().toString()));
            bean.setHeight(Integer.parseInt(height.getText().toString()));
//            bean.setSerial_no(etSerialNo.getText().toString());
            bean.setId(id);
            bean.setDd_id(dd_id);
            return false;
        }
    }


    @Override
    public void compressImagesSuccess(List<File> images) {
        L.e("压缩图片成功");
        L.e("image size is:" + images.size() + "");
        presenter.uploadImage(images);
    }

    @Override
    public void uploadImageSuccess(List<String> listimage) {
        for (int i = 0; i < listimage.size(); i++) {
            bean.getImages().add(listimage.get(i));
        }
        L.e("上传图片成功");
        L.e(list.get(0).getUrl());
        if (list.get(0).getType() == 3) {
            presenter.compressVideo(this, list.get(0).getUrl());
        } else if (list.get(0).getType() == 1) {
            bean.setVideo("");
            bean.setVideo_cover("");
            presenter.Shelving(Sp.getString(this, AppConstant.SHOP_ID), bean);
        }
    }

    @Override
    public void compressVideoSuccess(String path) {
        if (path != null && path.startsWith("video/")) {
            bean.setVideo(path);
            presenter.Shelving(Sp.getString(this, AppConstant.SHOP_ID), bean);
        } else {
            L.e("compress path is" + path);
            bean.setVideo(path);
            L.e("压缩视频成功");
            L.e("cover path is:" + bean.getVideo_cover());
            presenter.uploadVideo(bean.getVideo());
        }
    }

    @Override
    public void uploadVideoSuccess(VideoBean videoBean) {
        bean.setVideo(videoBean.getVideo());
        bean.setVideo_cover(videoBean.getImg());
        L.e("上传视频成功");
        presenter.Shelving(Sp.getString(this, AppConstant.SHOP_ID), bean);
    }

    @Override
    public void ShelvingSuccess() {
        Toast.makeText(this, "发布成功，请等待审核", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        submit.setEnabled(true);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    @Override
    public void getReShelvingDataFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        dialog.dismiss();
        Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void compressImagesFail() {
        L.e("图片压缩失败");
        submit.setEnabled(true);
        dialog.dismiss();
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        L.e("upload error is:" + e.status + e.message);
        dialog.dismiss();
    }

    @Override
    public void compressVideoFail() {
        L.e("视频压缩失败");
        submit.setEnabled(true);
        dialog.dismiss();
    }

    @Override
    public void uploadVideoFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        submit.setEnabled(true);
        dialog.dismiss();
    }

    @Override
    public void ShelvingFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        submit.setEnabled(true);
    }

    private void performAnime(final View view, int height, boolean show) {
        ValueAnimator va;
        if (show) va = ValueAnimator.ofInt(0, height);
        else va = ValueAnimator.ofInt(height, 0);
        va.addUpdateListener(valueAnimator -> {
            //获取当前的height值
            int h = (Integer) valueAnimator.getAnimatedValue();
            //动态更新view的高度
            view.getLayoutParams().height = h;
            view.requestLayout();
        });
        va.setDuration(500);
        //开始动画
        va.start();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class ShapeTextWacher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {
                shapeTag.setVisibility(View.GONE);
            } else {
                shapeTag.setVisibility(View.VISIBLE);
            }
        }
    }


}