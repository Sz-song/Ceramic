package com.yuanyu.ceramics.seller.shop_shelve.shelving;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
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
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cat on 2018/8/17.
 */

public class ShelvingFragment extends BaseFragment<ShelvingPrestenter> implements ShelvingConstract.IShelvingView {
    private static final int SELECT_IMAGE_CODE = 1000;
    private static final int SELECT_VIDEO_CODE = 1001;
    private static final int DISPLAY_IMAGE = 1002;
    private static final int DELETE_IMAGE = 201;
    private static final int DISPLAY_VIDEO = 1004;
    private static final int DELETE_VIDEO = 1005;
    private static final int imageSize = 8;
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
    @BindView(R.id.et_serial_no)
    EditText etSerialNo;
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
    @BindView(R.id.switch_btn)
    Switch switchBtn;
    @BindView(R.id.switch_relat)
    RelativeLayout switchRelat;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.fenxiao_price_tag)
    TextView fenxiaoPriceTag;
    @BindView(R.id.fenxiao_price)
    EditText fenxiaoPrice;
    @BindView(R.id.fenxiao_liner)
    LinearLayout fenxiaoLiner;
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
    @BindView(R.id.chanzhaung_tag)
    TextView chanzhaungTag;
    @BindView(R.id.chanzhuang)
    EditText chanzhuang;
    @BindView(R.id.chanzhuang_rec)
    RecyclerView chanzhuangRec;
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
    @BindView(R.id.pise)
    EditText pise;
    @BindView(R.id.pise_rec)
    RecyclerView piseRec;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.toonsale)
    LinearLayout toonsale;
    private List<FenleiTypeBean> fenleiList, chanzhuangList, ticaiList, piseList, zhongleiList;
    private FenleiTypeAdapter2 fenleiAdapter, chanzhuangAdapter, ticaiAdapter, piseAdapter, zhongleiAdapter;
    private InputMethodManager imm;
    private PhotoVedioAdapter adapter;
    private LoadingDialog dialog;
    private Drawable triangleRight;
    private Drawable triangleDown;
    private ShelvingDetailBean bean;
    private List<PhotoVideoBean> list;
    private ItemTouchHelper itemTouchHelper;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_applyshelving, container, false);
    }

    @Override
    protected ShelvingPrestenter initPresent() {
        return new ShelvingPrestenter();
    }

    @Override
    protected void initEvent(View view) {
        imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        triangleRight = getContext().getResources().getDrawable(R.mipmap.trianglerigth_black);
        triangleDown = getContext().getResources().getDrawable(R.mipmap.triangledown_black);
        triangleRight.setBounds(0, 0, triangleRight.getMinimumWidth(), triangleRight.getMinimumHeight());
        triangleDown.setBounds(0, 0, triangleDown.getMinimumWidth(), triangleDown.getMinimumHeight());
        dialog = new LoadingDialog(getContext());
        list = new ArrayList<>();
        bean = new ShelvingDetailBean();
//        if (Sp.getInt(getContext(), AppConstant.DISTRIBUTOR) == 1) {
//            fenxiaoliner.setVisibility(View.VISIBLE);
//            switchBtn.setChecked(false);
//            fenxiaoliner.setVisibility(View.GONE);
//        } else {
//            switchRelat.setVisibility(View.GONE);
//            fenxiaoliner.setVisibility(View.GONE);
//        }
//        switchBtn.setOnCheckedChangeListener((compoundButton, b) -> {
//            if (b) {
//                fenxiaoliner.setVisibility(View.VISIBLE);
//            } else {
//                fenxiaoliner.setVisibility(View.GONE);
//            }
//        });
        num.setFilters(new InputFilter[]{new InputNumScopeFilter(1, 999999)});
        list.add(0, new PhotoVideoBean(R.drawable.add_pic + "", 0));
        list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
        adapter = new PhotoVedioAdapter(getContext(), list, true);
        recyAddimage.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 3));
        recyAddimage.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Intent intent;
            switch (list.get(position).getType()) {
                case 0://选择图片
                    PictureSelector.create(ShelvingFragment.this).openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(imageSize - adapter.getItemCount() + 2)
                            .forResult(SELECT_IMAGE_CODE);
                    break;
                case 1://选择视频
                    PictureSelector.create(ShelvingFragment.this).openGallery(PictureMimeType.ofVideo())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(1)
                            .videoMaxSecond(16)
                            .forResult(SELECT_VIDEO_CODE);
                    break;
                case 2://图片预览
                    intent = new Intent(getActivity(), ImageDisplayActivity.class);
                    intent.putExtra("image", list.get(position).getUrl());
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE);
                    break;
                case 3:
                    intent = new Intent(getActivity(), VideoDisplayActivity.class);
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
        itemTouchHelper = new ItemTouchHelper(imageHelper);
        itemTouchHelper.attachToRecyclerView(recyAddimage);
        recyAddimage.addOnItemTouchListener(new OnItemClickListener(recyAddimage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {}
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (list.get(vh.getLayoutPosition()).getType()==2) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        inputTitleEdit.addTextChangedListener(new NotNullTextWatcher(titleTag));
        inputContentEdit.addTextChangedListener(new NotNullTextWatcher(contentTag));
        price.addTextChangedListener(new NotNullTextWatcher(priceTag));
        fenlei.addTextChangedListener(new NotNullTextWatcher(fenleiTag));
        zhonglei.addTextChangedListener(new NotNullTextWatcher(zhongleiTag));

        chanzhuang.addTextChangedListener(new NotNullTextWatcher(chanzhaungTag));

        weight.addTextChangedListener(new NotNullTextWatcher(weightTag));
        length.addTextChangedListener(new ShapeTextWeacher());
        width.addTextChangedListener(new ShapeTextWeacher());
        height.addTextChangedListener(new ShapeTextWeacher());
        fenleiList = new ArrayList<>();
        chanzhuangList = new ArrayList<>();
        ticaiList = new ArrayList<>();
        piseList = new ArrayList<>();
        zhongleiList = new ArrayList<>();
        presenter.initList(fenleiList, chanzhuangList, ticaiList, piseList, zhongleiList);
        fenleiAdapter = new FenleiTypeAdapter2(getContext(), fenleiList);
        fenleiAdapter.setClickListener(position -> {
            fenlei.setText(fenleiList.get(position).getType());
            fenlei.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(fenleiRec, 500, false);
            fenleiRec.setTag("0");
        });
        fenleiRec.setAdapter(fenleiAdapter);
        fenleiRec.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 4));

        zhongleiAdapter = new FenleiTypeAdapter2(getContext(), zhongleiList);
        zhongleiAdapter.setClickListener(position -> {
            zhonglei.setText(zhongleiList.get(position).getType());
            zhonglei.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(zhongleiRec, 500, false);
            zhongleiRec.setTag("0");
        });
        zhongleiRec.setAdapter(zhongleiAdapter);
        zhongleiRec.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 4));

        ticaiAdapter = new FenleiTypeAdapter2(getContext(), ticaiList);
        ticaiAdapter.setClickListener(position -> {
            ticai.setText(ticaiList.get(position).getType());
            ticai.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(ticaiRec, 500, false);
            ticaiRec.setTag("0");
        });
        ticaiRec.setAdapter(ticaiAdapter);
        ticaiRec.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 4));

        chanzhuangAdapter = new FenleiTypeAdapter2(getContext(), chanzhuangList);
        chanzhuangAdapter.setClickListener(position -> {
            chanzhuang.setText(chanzhuangList.get(position).getType());
            chanzhuang.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(chanzhuangRec, 500, false);
            chanzhuangRec.setTag("0");
        });
        chanzhuangRec.setAdapter(chanzhuangAdapter);
        chanzhuangRec.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 4));

        piseAdapter = new FenleiTypeAdapter2(getContext(), piseList);
        piseAdapter.setClickListener(position -> {
            pise.setText(piseList.get(position).getType());
            pise.setCompoundDrawables(null, null, triangleRight, null);
            performAnime(piseRec, 500, false);
            piseRec.setTag("0");
        });
        piseRec.setAdapter(piseAdapter);
        piseRec.setLayoutManager(new CantScrollGirdLayoutManager(getContext(), 4));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.submit, R.id.add, R.id.reduce, R.id.fenlei, R.id.zhonglei, R.id.ticai, R.id.pise, R.id.chanzhuang})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fenlei:
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
            case R.id.pise:
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                if (piseRec.getTag().equals("0")) {
                    pise.setCompoundDrawables(null, null, triangleDown, null);
                    performAnime(piseRec, 350, true);
                    piseRec.setTag("1");
                } else {
                    pise.setCompoundDrawables(null, null, triangleRight, null);
                    performAnime(piseRec, 350, false);
                    piseRec.setTag("0");
                }
                break;
            case R.id.ticai:
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
            case R.id.chanzhuang:
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                if (chanzhuangRec.getTag().equals("0")) {
                    chanzhuang.setCompoundDrawables(null, null, triangleDown, null);
                    performAnime(chanzhuangRec, 350, true);
                    chanzhuangRec.setTag("1");
                } else {
                    chanzhuang.setCompoundDrawables(null, null, triangleRight, null);
                    performAnime(chanzhuangRec, 350, false);
                    chanzhuangRec.setTag("0");
                }
                break;
            case R.id.submit:
                if (!isEmpty()) {
                    submit.setEnabled(false);
                    dialog.show();
                    L.e("before init list images");
                    List<String> imageList = new ArrayList<>();
                    if (list.get(list.size() - 1).getType() == 2) {
                        for (int i = 1; i < list.size(); i++) {
                            imageList.add(list.get(i).getUrl());
                        }
                    } else if (list.get(list.size() - 1).getType() == 0) {
                        for (int i = 1; i < list.size() - 1; i++) {
                            imageList.add(list.get(i).getUrl());
                        }
                    }
                    L.e("before compress images" + imageList.size());
                    presenter.compressImages(getContext(), imageList);
                }
                break;
            case R.id.reduce:
                try {
                    if((Integer.parseInt(num.getText().toString()) - 1)<1){
                        num.setText("1");
                    }else {
                        num.setText((Integer.parseInt(num.getText().toString()) - 1) + "");
                    }
                }catch (Exception e){
                    num.setText("1");
                }
                break;
            case R.id.add:
                try {
                    num.setText((Integer.parseInt(num.getText().toString()) + 1) + "");
                }catch (Exception e){
                    num.setText("1");
                }
                break;
        }
    }

    private boolean isEmpty() {
        if (!(inputTitleEdit.getText().toString().trim().length() > 0)) {
            Toast.makeText(getContext(), "请填写商品名称", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(inputContentEdit.getText().toString().trim().length() > 0)) {
            Toast.makeText(getContext(), "请填写商品描述", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(list.size() > 2)) {
            Toast.makeText(getContext(), "请上传图片", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(num.getText().toString().trim().length() > 0)) {
            Toast.makeText(getContext(), "请选择库存信息", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!(price.getText().toString().trim().length() > 0)) {
            Toast.makeText(getContext(), "请填写价格", Toast.LENGTH_SHORT).show();
            return true;
        } else if (switchRelat.getVisibility() == View.VISIBLE && switchBtn.isChecked() && !(fenxiaoPrice.getText().toString().trim().length() > 0)) {
            Toast.makeText(getContext(), "请填写分销价格", Toast.LENGTH_SHORT).show();
            return true;
        } else if (chanzhuang.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "请选择产状", Toast.LENGTH_SHORT).show();
            return true;
        } else if (fenlei.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "请选择分类", Toast.LENGTH_SHORT).show();
            return true;
        } else if (zhonglei.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "请选择种类", Toast.LENGTH_SHORT).show();
            return true;
        } else if (Double.parseDouble(price.getText().toString()) < 0.01) {
            Toast.makeText(getContext(), "价格不能为0", Toast.LENGTH_SHORT).show();
            return true;
        } else if(length.getText().toString().trim().length()==0){
            Toast.makeText(getContext(), "请输入长度", Toast.LENGTH_SHORT).show();
            return true;
        }else if(width.getText().toString().trim().length()==0){
            Toast.makeText(getContext(), "请输入宽度", Toast.LENGTH_SHORT).show();
            return true;
        }else if(height.getText().toString().trim().length()==0){
            Toast.makeText(getContext(), "请输入高度", Toast.LENGTH_SHORT).show();
            return true;
        }else if(weight.getText().toString().trim().length()==0){
            Toast.makeText(getContext(), "请输入重量", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            bean.setTitle(inputTitleEdit.getText().toString());
            bean.setDescription(inputContentEdit.getText().toString());
            bean.setArtisan(artisan.getText().toString().trim());
            bean.setPrice(price.getText().toString());
            bean.setAmount(num.getText().toString());
            bean.setDd_price(fenxiaoPrice.getText().toString());
            bean.setFenlei(fenlei.getText().toString());
            bean.setZhonglei(zhonglei.getText().toString());
            bean.setChanzhuang(chanzhuang.getText().toString());
            bean.setPise(pise.getText().toString());
            bean.setTicai(ticai.getText().toString());
            bean.setWeight(Double.parseDouble(weight.getText().toString()));
            bean.setWidth(Integer.parseInt(width.getText().toString()));
            bean.setLength(Integer.parseInt(length.getText().toString()));
            bean.setHeight(Integer.parseInt(height.getText().toString()));
            bean.setSerial_no(etSerialNo.getText().toString());
            return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    list.add(list.size()-1,new PhotoVideoBean(selectList.get(i).getPath(), 2));
                }
                if (list.size() > 9) {
                    list.remove(9);
                }
            }
            int n=0;
            for(int i=0;i<list.size();i++){
                if(list.get(i).getType()==2||list.get(i).getType()==3){
                    n++;
                }
            }
            addimageNum.setText(n+"/9");
            adapter.notifyDataSetChanged();
        } else if (requestCode == SELECT_VIDEO_CODE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

            if (selectList.size() > 0) {
                list.set(0, new PhotoVideoBean(selectList.get(0).getPath(), 3));
            }
            int n=0;
            for(int i=0;i<list.size();i++){
                if(list.get(i).getType()==2||list.get(i).getType()==3){
                    n++;
                }
            }
            addimageNum.setText(n+"/9");
            adapter.notifyDataSetChanged();
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= list.size()) {
                if (list.get(list.size() - 1).getType() == 0) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    int n=0;
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getType()==2||list.get(i).getType()==3){
                            n++;
                        }
                    }
                    addimageNum.setText(n+"/9");
                } else {
                    list.remove(position);
                    list.add(new PhotoVideoBean(R.drawable.add_pic + "", 0));
                    adapter.notifyDataSetChanged();
                    int n=0;
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getType()==2||list.get(i).getType()==3){
                            n++;
                        }
                    }
                    addimageNum.setText(n+"/9");
                }
            }
        } else if (requestCode == DISPLAY_VIDEO && resultCode == DELETE_VIDEO) {
            list.remove(0);
            list.add(0, new PhotoVideoBean(R.drawable.add_video + "", 1));
            int n=0;
            for(int i=0;i<list.size();i++){
                if(list.get(i).getType()==2||list.get(i).getType()==3){
                    n++;
                }
            }
            addimageNum.setText(n+"/9");
            adapter.notifyDataSetChanged();

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
        pise.setText("");
        chanzhuang.setText("");
        ticai.setText("");
        etSerialNo.setText("");
        length.setText("");
        width.setText("");
        height.setText("");
        weight.setText("");
        for (FenleiTypeBean bean : fenleiList) bean.setChoose(false);
        for (FenleiTypeBean bean : zhongleiList) bean.setChoose(false);
        for (FenleiTypeBean bean : ticaiList) bean.setChoose(false);
        for (FenleiTypeBean bean : piseList) bean.setChoose(false);
        for (FenleiTypeBean bean : chanzhuangList) bean.setChoose(false);
        fenleiAdapter.notifyDataSetChanged();
        zhongleiAdapter.notifyDataSetChanged();
        ticaiAdapter.notifyDataSetChanged();
        chanzhuangAdapter.notifyDataSetChanged();
        piseAdapter.notifyDataSetChanged();
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
    public void compressImagesSuccess(List<File> images) {
        L.e("压缩图片成功");
        presenter.uploadImage(images);
    }

    @Override
    public void uploadImageSuccess(List<String> listimage) {
        bean.setImages(listimage);
        L.e("上传图片成功");
        L.e(list.get(0).getUrl());
        if (list.get(0).getType() == 3) {
            presenter.compressVideo(getContext(), list.get(0).getUrl());
        } else if (list.get(0).getType() == 1) {
            bean.setVideo("");
            bean.setVideo_cover("");
            presenter.Shelving(Sp.getString(getContext(), AppConstant.SHOP_ID), bean);
        }
    }

    @Override
    public void compressVideoSuccess(String path) {
        L.e("compress path is" + path);
        bean.setVideo(path);
        L.e("压缩视频成功");
        L.e("cover path is:" + bean.getVideo_cover());
        presenter.uploadVideo(bean.getVideo());
    }

    @Override
    public void uploadVideoSuccess(VideoBean videoBean) {
        bean.setVideo(videoBean.getVideo());
        bean.setVideo_cover(videoBean.getImg());
        L.e("上传视频成功");
        presenter.Shelving(Sp.getString(getContext(), AppConstant.SHOP_ID), bean);
    }

    @Override
    public void ShelvingSuccess() {
        Toast.makeText(getContext(), "发布成功，请等待审核", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        submit.setEnabled(true);
        cleanData();
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
        Toast.makeText(getContext(), "视频压缩失败", Toast.LENGTH_SHORT).show();
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
        dialog.dismiss();
        submit.setEnabled(true);
    }


    class ShapeTextWeacher implements TextWatcher {
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
            }else {
                shapeTag.setVisibility(View.VISIBLE);
            }
        }
    }
}
