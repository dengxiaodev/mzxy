package com.example.mzxy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class ShareUtils {
    private static final String FILE_NAME = "mzxy";
    private static final String MODE_NAME = "welcome";

    //获取boolean类型的值
    public static boolean getWelcomeBoolean(Context context) {
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getBoolean(MODE_NAME,false);
    }
    //写入boolean类型的值
    public static void putWelcomeBoolean(Context context,boolean isFirst){
        SharedPreferences.Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        edit.putBoolean(MODE_NAME,isFirst);
        edit.apply();
    }
}
