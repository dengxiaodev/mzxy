package com.example.mzxy.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mzxy.R;
import com.example.mzxy.adapter.WashAdapter;
import com.example.mzxy.fragment.FragmentPay1;
import com.example.mzxy.fragment.FragmentPay2;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView pay_back;
    private TextView view_pay1;
    private TextView view_pay2;
    private ViewPager pay_viewPager;
    private ArrayList<Fragment> fragmentsPay;
    public String amounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        amounts = intent.getStringExtra("amounts");
        pay_back = (ImageView) findViewById(R.id.pay_back);
        view_pay1 = (TextView) findViewById(R.id.view_pay1);
        view_pay2 = (TextView) findViewById(R.id.view_pay2);
        pay_viewPager = (ViewPager) findViewById(R.id.pay_viewpager);
        pay_back.setOnClickListener(this);
        view_pay1.setOnClickListener(this);
        view_pay2.setOnClickListener(this);
        fragmentsPay = new ArrayList<>();
        FragmentPay1 fragmentPay1 = new FragmentPay1();
        FragmentPay2 fragmentPay2 = new FragmentPay2();
        fragmentsPay.add(fragmentPay1);
        fragmentsPay.add(fragmentPay2);
        pay_viewPager.setAdapter(new WashAdapter(getSupportFragmentManager(),fragmentsPay));
        pay_viewPager.setCurrentItem(0);
        pay_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_back://返回
                finish();
                break;
            case R.id.view_pay1://支付1
                pay_viewPager.setCurrentItem(0);
                break;
            case R.id.view_pay2://支付2
                pay_viewPager.setCurrentItem(1);
                break;
        }
    }
    private void setTextColor(int i){
        if(i==0){
            view_pay1.setBackground(getResources().getDrawable(R.color.pink));
            view_pay1.setTextColor(getResources().getColor(R.color.white));
            view_pay2.setBackground(getResources().getDrawable(R.color.white));
            view_pay2.setTextColor(getResources().getColor(R.color.textBlack));
        }else if(i==1){
            view_pay1.setBackground(getResources().getDrawable(R.color.white));
            view_pay1.setTextColor(getResources().getColor(R.color.textBlack));
            view_pay2.setBackground(getResources().getDrawable(R.color.pink));
            view_pay2.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
