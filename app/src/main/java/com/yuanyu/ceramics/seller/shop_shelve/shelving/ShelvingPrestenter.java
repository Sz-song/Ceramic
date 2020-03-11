package com.yuanyu.ceramics.seller.shop_shelve.shelving;

import android.content.Context;
import android.os.Environment;

import com.guoxiaoxing.phoenix.compress.video.VideoCompressor;
import com.guoxiaoxing.phoenix.compress.video.format.MediaFormatStrategyPresets;
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

public class ShelvingPrestenter extends BasePresenter<ShelvingConstract.IShelvingView> implements ShelvingConstract.IShelvingPresenter {
    private ShelvingConstract.IShelvingModel model;
    public ShelvingPrestenter() { model=new ShelvingModel();}
    @Override
    public void Shelving(String shopid, ShelvingDetailBean bean) {
        model.Shelving(shopid,bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String []>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if(view!=null){view.ShelvingSuccess();} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.ShelvingFail(e);}}
                });
    }

    @Override
    public void compressImages(Context context, List<String> list) {
        Flowable.just(list).observeOn(Schedulers.io())
                .map(list1 -> {
                    //Luban压缩，返回List<File>
                    return Luban.with(context).load(list1).get();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    if(view!=null){
                        view.compressImagesSuccess(files);
                    }
                });
    }

    @Override
    public void compressVideo(Context context, String path) {
        final File compressFile;
        try {
            File compressCachePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "yuanyu");
            compressCachePath.mkdir();
            compressFile = File.createTempFile("compress", ".mp4", compressCachePath);
        } catch (IOException e) {
            L.e("Failed to create temporary file");
            return;
        }
        VideoCompressor.Listener listener = new VideoCompressor.Listener() {
            @Override
            public void onTranscodeProgress(double progress) {}
            @Override
            public void onTranscodeCompleted() {
                if(view!=null){view.compressVideoSuccess(compressFile.getAbsolutePath());}
            }
            @Override
            public void onTranscodeCanceled() {L.e("video compress is canceled");}
            @Override
            public void onTranscodeFailed(Exception exception) {
                view.compressVideoFail();
            }
        };
        try {
            VideoCompressor.with().asyncTranscodeVideo(path, compressFile.getAbsolutePath(),
                    MediaFormatStrategyPresets.createAndroid480pFormatStrategy(), listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void initList(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> ticaiList,List<FenleiTypeBean> zhongleiList) {
        fenleiList.add(new FenleiTypeBean("花瓶"));
        fenleiList.add(new FenleiTypeBean("雕塑品"));
        fenleiList.add(new FenleiTypeBean("园林陶艺"));
        fenleiList.add(new FenleiTypeBean("器皿"));
        fenleiList.add(new FenleiTypeBean("相框"));
        fenleiList.add(new FenleiTypeBean("壁画"));
        fenleiList.add(new FenleiTypeBean("陈设品"));
        fenleiList.add(new FenleiTypeBean("其它"));


        ticaiList.add(new FenleiTypeBean("神佛"));
        ticaiList.add(new FenleiTypeBean("瑞兽"));
        ticaiList.add(new FenleiTypeBean("仿古"));
        ticaiList.add(new FenleiTypeBean("山水"));
        ticaiList.add(new FenleiTypeBean("人物"));
        ticaiList.add(new FenleiTypeBean("花鸟"));
        ticaiList.add(new FenleiTypeBean("动物"));
        ticaiList.add(new FenleiTypeBean("其它"));


        zhongleiList.add(new FenleiTypeBean("素瓷"));
        zhongleiList.add(new FenleiTypeBean("青瓷"));
        zhongleiList.add(new FenleiTypeBean("黑瓷"));
        zhongleiList.add(new FenleiTypeBean("白瓷"));
        zhongleiList.add(new FenleiTypeBean("青白瓷"));
        zhongleiList.add(new FenleiTypeBean("其它"));
    }
}
