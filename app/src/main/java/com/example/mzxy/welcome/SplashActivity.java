package com.example.mzxy.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mzxy.view.MainActivity;
import com.example.mzxy.R;

public class SplashActivity extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private Button splashBtn;
    private ImageView[] imageDot;
    private LinearLayout dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dot = (LinearLayout) findViewById(R.id.splash_dot);
        initDot();
        splashBtn = (Button) findViewById(R.id.splash_btn);
        viewPager = (ViewPager) findViewById(R.id.splash_view_pager);
        //适配器初始化
        viewPagerAdapter = new ViewPagerAdapter(this, SplashImages.imageArray);
        //绑定适配器
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPagerChangedListener());
        splashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDot() {
        imageDot = new ImageView[SplashImages.imageArray.length];
        for (int i = 0; i < SplashImages.imageArray.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageDot[i] = imageView;
            if (i == 0) {
                imageDot[i].setBackgroundResource(R.mipmap.dot_red);
            } else {
                imageDot[i].setBackgroundResource(R.mipmap.dot_blue);
            }
            dot.addView(imageView);
        }
    }

    class ViewPagerChangedListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == SplashImages.imageArray.length - 1) {
                splashBtn.setVisibility(View.VISIBLE);
            } else {
                splashBtn.setVisibility(View.GONE);
            }
            for (int i = 0; i < imageDot.length; i++) {
                if (i == position) {
                    imageDot[i].setBackgroundResource(R.mipmap.dot_red);
                } else {
                    imageDot[i].setBackgroundResource(R.mipmap.dot_blue);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

