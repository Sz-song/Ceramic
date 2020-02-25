package com.yuanyu.ceramics.personal_index;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonalIndexPresenter extends BasePresenter<PersonalIndexConstract.IPersonalIndexView> implements PersonalIndexConstract.IPersonalIndexPresenter {
    private PersonalIndexConstract.IPersonalIndexModel model;

    PersonalIndexPresenter() {
        model=new PersonalIndexModel();
    }

    @Override
    public void getPersonalIndexData(String useraccountid, String userid) {
        model.getPersonalIndexData(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<PersonalIndexBean>())
                .subscribe(new BaseObserver<PersonalIndexBean>() {
                    @Override
                    public void onNext(PersonalIndexBean bean) {if(view!=null){view.getPersonalIndexDataSuccess(bean);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.getPersonalIndexDataFail(e);}}
                });
    }

    @Override
    public void focus(String useraccountid, String userid) {
        model.focus(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.focusSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.focusFail(e);}}
                });
    }

    @Override
    public void addBlacklist(String useraccountid, String userid) {
        model.addBlacklist(useraccountid,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {if(view!=null){view.addBlacklistSuccess(b);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {if(view!=null){view.addBlacklistFail(e);}}
                });
    }

    @Override
    public void saveScreenshot(Bitmap bitmap, int type) {
        if (bitmap != null) {
            try {// 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + System.currentTimeMillis()+".JPEG";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                Uri uri = Uri.fromFile(file);
                if(view!=null){view.saveScreenshotSuccess(uri,type,filePath);}
            } catch (Exception e) {
                L.e(e.getMessage());
                if(view!=null){view.saveScreenshotFail(type);}
            }
        }else{
            if(view!=null){view.saveScreenshotFail(type);}
        }
    }
}
