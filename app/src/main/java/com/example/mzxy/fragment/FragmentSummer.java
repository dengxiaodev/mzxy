package com.example.mzxy.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mzxy.R;

public class FragmentSummer extends Fragment {
    private View view;
    private ImageView mLeft;
    private ImageView mRight;
    private TextView mText;
    private AnimatorSet animSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summer, null);
        mLeft = (ImageView) view.findViewById(R.id.imageLeft);
        mRight = (ImageView) view.findViewById(R.id.imageRight);
        mText = (TextView) view.findViewById(R.id.anim_text);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            startAnimator();
        }
    }

    private void startAnimator() {
        //向左移动
        ObjectAnimator animatorLeft = ObjectAnimator.ofFloat(mLeft, "translationX", 0f, -500f);
        //向右移动
        ObjectAnimator animatorRight = ObjectAnimator.ofFloat(mRight, "translationX", 0f, +500f);
        //渐变
        ObjectAnimator animatorEnlarge = ObjectAnimator.ofFloat(mText, "alpha", 1f, 0f);
        animatorEnlarge.setDuration(1000);
        //放大
        ObjectAnimator animatorChangeY = ObjectAnimator.ofFloat(mText, "scaleY", 1f, 3f);
        animatorChangeY.setDuration(1500);
        //放大
        ObjectAnimator animatorChangeX = ObjectAnimator.ofFloat(mText, "scaleX", 1f, 3f);
        animatorChangeX.setDuration(1500);
        animSet = new AnimatorSet();
        //同时执行开门
        animSet.play(animatorLeft).with(animatorRight);
        //同时执行放大渐变
        animSet.play(animatorChangeY).with(animatorChangeX).with(animatorEnlarge);
        animSet.setDuration(2000);
        animSet.start();
    }
}
