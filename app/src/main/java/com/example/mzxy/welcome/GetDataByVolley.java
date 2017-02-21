package com.example.mzxy.welcome;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class GetDataByVolley {

    private final static MyApplication application = MyApplication
            .getInstance();


    /**
     * 请求结果的回调函数，可以使程序逻辑解耦
     *
     * @author Admin
     */
    public interface CallBack {
        /**
         * 返回volley得到的数据
         *
         * @param result 如果为null说明volley请求失败
         */
        public void returnData(Object result);

    }

    /**
     * 通过volley的get请求返回数据
     *
     * @param url
     * @param TAG
     * @param callBack
     */
    public static void getStringByGet(String url, final String TAG,
                                      final CallBack callBack) {

        Log.d(TAG, "get请求url：" + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        callBack.returnData(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                callBack.returnData(null);
                Log.d("main", "Volley的Get请求数据获取失败");
            }
        });

        application.startRequest(stringRequest, TAG);

    }

    /**
     * 通过volley的post请求返回数据
     *
     * @param url      请求的地址
     * @param params   请求的参数
     * @param callBack 结果的回调
     */
    public static void getStringByPost(String url,
                                       final Map<String, String> params, final String TAG,
                                       final CallBack callBack) {

        Log.d(TAG, "post请求url：" + url);

        // 创建一个请求字符串数据的对象
        // 参数（请求方法，地址，请求成功的监听器，请求失败的监听器）
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // 利用回调函数将数据传出去
                        callBack.returnData(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // 利用回调函数将错误传出去
                callBack.returnData(null);
            }
        }) {
            // post普通请求的参数
            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };

        application.startRequest(stringRequest, TAG);

    }

    /**
     * 通过volley得到图片
     *
     * @param url      请求的地址
     * @param callBack 结果的回调
     */
    public static void getImage(String url, final String TAG,
                                final CallBack callBack) {

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {

                        callBack.returnData(response);

                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.returnData(null);
                Log.w(TAG, "图片获取失败");
            }
        });

        application.startRequest(imageRequest, TAG);

    }

    /**
     * 通过volley直接加载图片到控件
     *
     * @param url               图片路径
     * @param iv                要显示图片的控件
     * @param defaultImageResId 加载图片时显示的图片
     * @param errorImageResId   加载失败时显示的图片
     */
    public static void loaderImage(String url, ImageView iv, int defaultImageResId, int errorImageResId) {

        // 参数（view是ImageView的一个实例，defaultImageResId是设置的默认图片，errorImageResId是请求失败时设置的图片）
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv,
                defaultImageResId, errorImageResId);
        // 得到图片加载器
        ImageLoader loader = application.getImageLoader();
        // 图片加载
        loader.get(url, imageListener);
    }

    /**
     * 取消请求
     *
     * @param TAG
     */
    public static void cancelRequest(String TAG) {

        application.cancelRequest(TAG);

    }
}
