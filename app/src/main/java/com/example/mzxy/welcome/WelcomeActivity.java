package com.example.mzxy.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mzxy.view.HomeActivity;
import com.example.mzxy.view.MainActivity;
import com.example.mzxy.R;
import com.example.mzxy.utils.ShareUtils;
import com.example.mzxy.view.WashActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Button startBtn;
    private Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startBtn = (Button) findViewById(R.id.button);
        if (ShareUtils.getWelcomeBoolean(getBaseContext())){
            startBtn.setVisibility(View.VISIBLE);
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                Intent intentHome = new Intent(WelcomeActivity.this, HomeActivity.class);
                Intent intentWash = new Intent(WelcomeActivity.this, WashActivity.class);
                startActivities(new Intent[]{intentHome, intentWash});
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //不是第一次启动
                if (ShareUtils.getWelcomeBoolean(getBaseContext())) {
                    if (flag) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
                    startActivity(intent);
                    //保存访问记录
                    ShareUtils.putWelcomeBoolean(getBaseContext(), true);
                }
                finish();
            }
        }, 2000);
    }
}
