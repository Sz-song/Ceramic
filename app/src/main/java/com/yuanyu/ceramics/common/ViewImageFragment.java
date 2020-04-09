package com.yuanyu.ceramics.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.luck.picture.lib.photoview.PhotoView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class ViewImageFragment extends BaseFragment {

    @BindView(R.id.photoview)
    PhotoView photoview;
    private String url;
    private Bitmap bitmap;
    @Override
    public void initData() {

    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_view_image, container, false);
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    public void initEvent(View view) {
        assert getArguments() != null;
        url=getArguments().getString("url");
        assert url != null;
        if(url.equals("shop_indexdefault")) {
            GlideApp.with(getContext())
                    .load(R.drawable.img_default)
                    .placeholder(R.drawable.img_default)
                    .fitCenter()
                    .into(photoview);
        }else{
            GlideApp.with(getContext())
                    .load(BASE_URL + url)
                    .placeholder(R.drawable.img_default)
                    .fitCenter()
                    .into(photoview);
        }
        photoview.setOnLongClickListener(view1 -> {
            if(url.startsWith("back/")) {
                final CommonDialog2 dialog2 = new CommonDialog2(getContext(), "保存图片", "确定", "取消");
                dialog2.show();
                dialog2.setNoOnclick(() -> dialog2.dismiss());
                dialog2.setYesOnclick(() -> {
                    dialog2.dismiss();
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        new Task().execute(BASE_URL + url);
                    }
                });
            }
            return false;
        });
    }
    @OnClick(R.id.photoview)
    public void onViewClicked() {
        getActivity().finish();
    }
    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            L.e(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        L.e(bitmap.toString());
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap, String fileName){
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Yuanyu";
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            L.e("储存失败");
            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            File file = new File(dir + fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
            L.e("保存成功");
        } catch (Exception e) {
            L.e("保存失败 "+e.getMessage());
            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123){
                saveBitmap(bitmap, System.currentTimeMillis()+".png");
            }
        }
    };
    /**
     * 异步线程下载图片
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            bitmap=GetImageInputStream(params[0]);
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message=new Message();
            message.what=0x123;
            handler.sendMessage(message);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    new Task().execute(BASE_URL + url);
                }else{
                    Toast.makeText(getActivity(), "权限被禁止", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
