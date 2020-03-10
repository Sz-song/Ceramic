package com.yuanyu.ceramics.seller.shop_shelve.re_shelve;

import android.content.Context;
import android.os.Environment;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ReShelvePresenter extends BasePresenter<ReShelveConstract.IReShelveView> implements ReShelveConstract.IReShelvePresenter {
    private ReShelveConstract.IReShelveModel model;

    public ReShelvePresenter() {
        model=new ReShelveModel();
    }

    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list)
                .observeOn(Schedulers.io())
                .map(list1 -> Luban.with(context).load(list1).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    if(view!=null){
                        view.compressImagesSuccess(files);
                    }
                });
    }
    @Override
    public void uploadImage(List<File> images) {
        model.uploadImage(images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {if(view!=null){view.uploadImageSuccess(list);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.uploadImageFail(e);}}
                });
    }

    @Override
    public void compressVideo(Context context, String path) {
        if(path!=null&&path.startsWith("video/")){
            view.compressVideoSuccess(path);
        }else {
            final File compressFile;
            try {
                File compressCachePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "yuanyu");
                compressCachePath.mkdir();
                compressFile = File.createTempFile("compress", ".mp4", compressCachePath);
            } catch (IOException e) {
                L.e("Failed to create temporary file");
                return;
            }
//            VideoCompressor.Listener listener = new VideoCompressor.Listener() {
//                @Override
//                public void onTranscodeProgress(double progress) {
//                }
//                @Override
//                public void onTranscodeCompleted() {
//                    if (view != null) {
//                        view.compressVideoSuccess(compressFile.getAbsolutePath());
//                    }
//                }
//                @Override
//                public void onTranscodeCanceled() {
//                    L.e("video compress is canceled");
//                }
//                @Override
//                public void onTranscodeFailed(Exception exception) {
//                    if (view != null) {
//                        view.compressVideoFail();
//                    }
//                }
//            };
//            try {
//                VideoCompressor.with().asyncTranscodeVideo(path, compressFile.getAbsolutePath(),
//                        MediaFormatStrategyPresets.createAndroid480pFormatStrategy(), listener);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void uploadVideo(String video) {
        model.uploadVideo(new File(video))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<VideoBean>())
                .subscribe(new BaseObserver<VideoBean>() {
                    @Override
                    public void onNext(VideoBean videoBean) { if(view!=null){view.uploadVideoSuccess(videoBean);} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.uploadVideoFail(e);} }
                });
    }

    @Override
    public void Shelving(String shopid, ShelvingDetailBean bean){
        model.Shelving(shopid,bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if(view!=null){view.ShelvingSuccess();} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.ShelvingFail(e);}}
                });
    }

    @Override
    public void initList(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> chanzhuangList, List<FenleiTypeBean> ticaiList, List<FenleiTypeBean> piseList, List<FenleiTypeBean> zhongleiList) {
        fenleiList.add(new FenleiTypeBean("原石"));
        fenleiList.add(new FenleiTypeBean("挂件"));
        fenleiList.add(new FenleiTypeBean("吊坠"));
        fenleiList.add(new FenleiTypeBean("把件"));
        fenleiList.add(new FenleiTypeBean("摆件"));
        fenleiList.add(new FenleiTypeBean("器皿"));
        fenleiList.add(new FenleiTypeBean("手镯"));
        fenleiList.add(new FenleiTypeBean("手串（链）"));
        fenleiList.add(new FenleiTypeBean("项链"));
        fenleiList.add(new FenleiTypeBean("饰品"));
        fenleiList.add(new FenleiTypeBean("杂项"));

        chanzhuangList.add(new FenleiTypeBean("山料"));
        chanzhuangList.add(new FenleiTypeBean("籽料"));

        ticaiList.add(new FenleiTypeBean("神佛"));
        ticaiList.add(new FenleiTypeBean("瑞兽"));
        ticaiList.add(new FenleiTypeBean("仿古"));
        ticaiList.add(new FenleiTypeBean("山水"));
        ticaiList.add(new FenleiTypeBean("人物"));
        ticaiList.add(new FenleiTypeBean("花鸟"));
        ticaiList.add(new FenleiTypeBean("动物"));
        ticaiList.add(new FenleiTypeBean("其它"));

        piseList.add(new FenleiTypeBean("黄皮"));
        piseList.add(new FenleiTypeBean("红皮"));
        piseList.add(new FenleiTypeBean("黑皮"));
        piseList.add(new FenleiTypeBean("秋梨皮"));
        piseList.add(new FenleiTypeBean("虎皮"));
        piseList.add(new FenleiTypeBean("其它"));

        zhongleiList.add(new FenleiTypeBean("白玉"));
        zhongleiList.add(new FenleiTypeBean("碧玉"));
        zhongleiList.add(new FenleiTypeBean("墨玉"));
        zhongleiList.add(new FenleiTypeBean("青玉"));
        zhongleiList.add(new FenleiTypeBean("青花"));
        zhongleiList.add(new FenleiTypeBean("糖玉"));
        zhongleiList.add(new FenleiTypeBean("黄口料"));
        zhongleiList.add(new FenleiTypeBean("黄沁"));
        zhongleiList.add(new FenleiTypeBean("其它"));
    }

    @Override
    public void getReShelvingData(String shop_id, String id) {
        model.getReShelvingData(shop_id,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<ShelvingDetailBean>())
                .subscribe(new BaseObserver<ShelvingDetailBean>() {
                    @Override
                    public void onNext(ShelvingDetailBean shelvingDetailBean) {
                        if(view!=null){view.getReShelvingDataSuccess(shelvingDetailBean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if (view!=null){view.getReShelvingDataFail(e);}
                    }
                });
    }

//    @Override
//    public void getFenxiaoGoodData(String shop_id, String dd_id) {
//        model.getFenxiaoGoodData(shop_id,dd_id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<ShelvingDetailBean>())
//                .subscribe(new BaseObserver<FenxiaoGoodsDetailBean>() {
//                    @Override
//                    public void onNext(FenxiaoGoodsDetailBean bean) {
//                        if(view!=null){view.getFenxiaoGoodDataSuccess(bean);}
//                    }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) {
//                        if (view!=null){view.getReShelvingDataFail(e);}
//                    }
//                });
//    }
}
