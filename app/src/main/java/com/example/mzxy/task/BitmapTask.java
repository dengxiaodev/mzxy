package com.example.mzxy.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class BitmapTask extends AsyncTask<Integer, Void, Bitmap> {
    private Context context;
    private int res;
    private ImageView imageView;

    public BitmapTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        res = params[0];
        //根据图片资源ID获取到对应的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //显示加载成功的图片
        imageView.setImageBitmap(bitmap);
    }
}