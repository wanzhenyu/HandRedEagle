package unicom.hand.redeagle.zhfy.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.lzy.imagepicker.bean.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import unicom.hand.redeagle.zhfy.Common;

/**
 * Created by linana on 2018-05-24.
 */

public class UploadUtils {

    private Activity activity;
    private MyCallBack callBack;
    public UploadUtils(Activity activity, MyCallBack callBack){
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 上传图片到服务器
     */
    public void upload(String imageItem) {
        if(!NetworkUtils.isNetworkConnected(UIUtils.getContext())){
            Toast.makeText(activity, "网络连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
//        Log.e("aaa", "保存的媒体资源路径：要上传" + imageItem.path);
        String fileName = "";
        File tempFile = new File(imageItem);
        File rootdir = new File(Environment.getExternalStorageDirectory()+"/zshy");
        if(!rootdir.exists()){
            rootdir.mkdirs();
        }
        File savefile = new File(Environment.getExternalStorageDirectory()+"/zshy/"+System.currentTimeMillis()+".png");
        long fileSize = getFileSize(tempFile);
//        Logger.e("aaa","图片大小："+fileSize);
        if(fileSize > 1572864){//大于1.5m,需要压缩
//            Logger.e("aaa","图片太大需要压缩");
            Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
            PhotoUtils.compressBitmapToFile(bitmap,savefile);
            fileName = savefile.getName();
        }else{//小图片不需要压缩
//            Logger.e("aaa","图片太小不需要压缩");
            fileName = tempFile.getName();
            savefile = tempFile;
        }
//        Logger.e("aaa","图片文件名称："+fileName);

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        RequestBody requestBody1 = null;
//        Log.e("aaa", "上传的是图片,保存的媒体资源路径：要上传" + savefile.getAbsolutePath());
        requestBody1 = RequestBody.create(MediaType.parse("image/jpeg"), savefile);
        multipartBodyBuilder.addFormDataPart("filename", "")
                .addFormDataPart("fieldNameHere", fileName, requestBody1);
        RequestBody requestBody = multipartBodyBuilder.build();

        Request request = new Request.Builder()
                .url(Common.UPLOADFILE)
                .post(requestBody)
                .build();
        //上面url中的内容请改成自己php文件的所在地址
        OkhttpUtils.getInstance().newCall(request).enqueue(callback_upload);
    }


    /**
     * 获取指定文件大小
     *1048576 是1m
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) {
        long size = 0;
        try {
            size = 0;
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("aaa","获取文件大小不存在!");
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return size;
    }
    public void upload1(String path) {
        if(!NetworkUtils.isNetworkConnected(UIUtils.getContext())){
            Toast.makeText(activity, "网络连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("aaa", "保存的媒体资源路径：要上传" + path);
        String fileName = "";
        File tempFile = new File(path);

        File rootdir = new File(Environment.getExternalStorageDirectory()+"xxfmq");
        if(!rootdir.exists()){
            rootdir.mkdirs();
        }
        File savefile = new File(Environment.getExternalStorageDirectory()+"xxfmq/"+System.currentTimeMillis()+".png");
        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
        PhotoUtils.compressBitmapToFile(bitmap,savefile);

        fileName = savefile.getName();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        RequestBody requestBody1 = null;
//        Log.e("aaa", "上传的是图片,保存的媒体资源路径：要上传" + path);
        requestBody1 = RequestBody.create(MediaType.parse("image/jpeg"), savefile);
        multipartBodyBuilder.addFormDataPart("filename", "")
                .addFormDataPart("fieldNameHere", fileName, requestBody1);
        RequestBody requestBody = multipartBodyBuilder.build();

        Request request = new Request.Builder()
                .url(Common.UPLOADFILE)
                .post(requestBody)
                .build();
        //上面url中的内容请改成自己php文件的所在地址
        OkhttpUtils.getInstance().newCall(request).enqueue(callback_upload);
    }
    public static void compressBitmapToFile(Bitmap bmp, File file){
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 4;
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileName(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf("");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }

    }
    //上传请求后的回调方法
    private Callback callback_upload = new Callback() {
        @Override
        public void onFailure(okhttp3.Call call, IOException e) {
            setResult(e.getMessage(), false);
        }

        @Override
        public void onResponse(okhttp3.Call call, Response response) throws IOException {
            setResult(response.body().string(), true);
        }
    };

    //显示请求返回的结果
    private void setResult(final String msg, final boolean success) {
        Log.e("aaa", "上传结果:" + msg);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (success) {
                    try {
                        JSONObject data = new JSONObject(msg);
                        String mTmpImgPath = data.getString("resultData");
//                        Log.e("aaa", "开始回调:" + mTmpImgPath);
                        callBack.sucess(mTmpImgPath);
//                        Picasso.with(activity).load(mTmpImgPath).placeholder(R.drawable.zanwu).error(R.drawable.zanwu).into(iv_change_icon);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    toast("请求成功");
                } else {
                    callBack.failed("上传失败");
//                    Toast.makeText(activity,"请求失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }







}
