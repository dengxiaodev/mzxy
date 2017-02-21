package com.example.mzxy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mzxy.R;
import com.example.mzxy.adapter.WashAdapter;
import com.example.mzxy.fragment.FragmentCoat;
import com.example.mzxy.fragment.FragmentSpring;
import com.example.mzxy.fragment.FragmentSummer;
import com.example.mzxy.fragment.FragmentWinter;
import com.example.mzxy.welcome.GetDataByVolley;

import java.util.ArrayList;

import static com.example.mzxy.R.id.fragment_wash_coat;
import static com.example.mzxy.R.id.fragment_wash_pager;
import static com.example.mzxy.R.id.fragment_wash_spring;
import static com.example.mzxy.R.id.fragment_wash_summer;
import static com.example.mzxy.R.id.fragment_wash_winter;

public class WashActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnTouchListener {
    private ImageView back;
    private TextView washSpring;
    private TextView washSummer;
    private TextView washWinter;
    private TextView washCoat;

    private int index = 0;
    private View cursor1, cursor2, cursor3, cursor4;
    private ViewPager washViewPager;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash);
        init();
    }

    @Override
    public void onClick(View v) {
        washViewPager.setCurrentItem(index);
        switch (v.getId()) {
            case R.id.wash_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.fragment_wash_spring:
                index = 0;
                break;
            case R.id.fragment_wash_summer:
                index = 1;
                break;
            case R.id.fragment_wash_winter:
                index = 2;
                break;
            case R.id.fragment_wash_coat:
                index = 3;
                break;
        }
        return false;
    }

    private void init() {
        washViewPager = (ViewPager) findViewById(fragment_wash_pager);
        back = (ImageView) findViewById(R.id.wash_back);
        washSpring = (TextView) findViewById(fragment_wash_spring);
        washSummer = (TextView) findViewById(fragment_wash_summer);
        washWinter = (TextView) findViewById(fragment_wash_winter);
        washCoat = (TextView) findViewById(fragment_wash_coat);
        cursor1 = findViewById(R.id.cursor1);
        cursor2 = findViewById(R.id.cursor2);
        cursor3 = findViewById(R.id.cursor3);
        cursor4 = findViewById(R.id.cursor4);
        back.setOnClickListener(this);
        washSpring.setOnClickListener(this);
        washSummer.setOnClickListener(this);
        washWinter.setOnClickListener(this);
        washCoat.setOnClickListener(this);
        washSpring.setOnTouchListener(this);
        washSummer.setOnTouchListener(this);
        washWinter.setOnTouchListener(this);
        washCoat.setOnTouchListener(this);
        fragments = new ArrayList<>();
        FragmentSpring spring = new FragmentSpring();
        FragmentSummer summer = new FragmentSummer();
        FragmentWinter winter = new FragmentWinter();
        FragmentCoat coat = new FragmentCoat();
        fragments.add(spring);
        fragments.add(summer);
        fragments.add(winter);
        fragments.add(coat);
        WashAdapter washAdapter = new WashAdapter(getSupportFragmentManager(), fragments);
        washViewPager.setAdapter(washAdapter);
        washViewPager.setCurrentItem(0);
        washViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCursorColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCursorColor(int position) {
        int blue = R.color.blue;
        int gray = R.color.gray;
        int text = R.color.textBlack;
        switch (position) {
            case 0:
                washSpring.setTextColor(getResources().getColor(blue));
                washSummer.setTextColor(getResources().getColor(text));
                washWinter.setTextColor(getResources().getColor(text));
                washCoat.setTextColor(getResources().getColor(text));
                cursor1.setBackgroundResource(blue);
                cursor2.setBackgroundResource(gray);
                cursor3.setBackgroundResource(gray);
                cursor4.setBackgroundResource(gray);
                break;
            case 1:
                washSpring.setTextColor(getResources().getColor(text));
                washSummer.setTextColor(getResources().getColor(blue));
                washWinter.setTextColor(getResources().getColor(text));
                washCoat.setTextColor(getResources().getColor(text));
                cursor1.setBackgroundResource(gray);
                cursor2.setBackgroundResource(blue);
                cursor3.setBackgroundResource(gray);
                cursor4.setBackgroundResource(gray);
                break;
            case 2:
                washSpring.setTextColor(getResources().getColor(text));
                washSummer.setTextColor(getResources().getColor(text));
                washWinter.setTextColor(getResources().getColor(blue));
                washCoat.setTextColor(getResources().getColor(text));
                cursor1.setBackgroundResource(gray);
                cursor2.setBackgroundResource(gray);
                cursor3.setBackgroundResource(blue);
                cursor4.setBackgroundResource(gray);
                break;
            case 3:
                washSpring.setTextColor(getResources().getColor(text));
                washSummer.setTextColor(getResources().getColor(text));
                washWinter.setTextColor(getResources().getColor(text));
                washCoat.setTextColor(getResources().getColor(blue));
                cursor1.setBackgroundResource(gray);
                cursor2.setBackgroundResource(gray);
                cursor3.setBackgroundResource(gray);
                cursor4.setBackgroundResource(blue);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetDataByVolley.cancelRequest("WashActivity");
    }
    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            Intent intent = new Intent();
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
