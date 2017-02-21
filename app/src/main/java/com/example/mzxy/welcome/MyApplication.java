package com.example.mzxy.welcome;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.mzxy.utils.LaundryUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class MyApplication extends Application {
    /**请求队列
     * @param
     */
    private RequestQueue queue;
    /**图片加载器
     * @param
     */
    private ImageLoader imageLoader;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }
    /**全局集合
     * @param
     */
    public static List<LaundryUtils> washJavas=  new ArrayList<LaundryUtils>();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化一个请求队列
        queue = Volley.newRequestQueue(getApplicationContext());
        // 创建一个图像加载器
        imageLoader = new ImageLoader(queue, new BitmapCache());
        //初始化PushSDK
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("myToken", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });


        //百度地图初始化
//        SDKInitializer.initialize(getApplicationContext());
        //腾讯错误日志收集
//        CrashReport.initCrashReport(getApplicationContext(), "900009621", false);
        /**激光推送
         * @param
         */
//        JPushInterface.setDebugMode(true); 	// 激光推送初始化
//        JPushInterface.init(this);
    }
    /**
     * 开始请求
     *
     * @param r
     * @param tag
     * @return
     */
    public <T> Request<T> startRequest(Request<T> r, Object tag) {
        // 给传来的请求设置TAG
        r.setTag(tag);

        Request<T> request = queue.add(r);

        queue.start();

        return request;
    }
    /**
     * 返回图片加载器
     *
     * @return
     */
    public ImageLoader getImageLoader() {

        return imageLoader;

    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelRequest(Object tag) {
        queue.cancelAll(tag);
    }
    // ------------------------------------------------
    /**
     * 图片内存缓存类 把获取过的图片缓存到内存
     *
     * @author Admin
     *
     */
    class BitmapCache implements ImageLoader.ImageCache {

        // LRU算法（近期最少使用算法）
        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {

            Log.d("BitmapCache", "BitmapCache");

            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    // 返回图片在内存中占用的大小
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}