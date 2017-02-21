package com.example.mzxy.utils;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 *图片密码样式改变方法
 * @param
 *
 */
public class ChangePasswordPicture extends PasswordTransformationMethod {
    @NonNull
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new ChangePassword(source);
    }

    private class ChangePassword implements CharSequence {
        private CharSequence mSource;

        public ChangePassword(CharSequence mSource) {
            this.mSource = mSource;
        }

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public char charAt(int index) {
            return '●';
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(1, end);
        }
    }
}
