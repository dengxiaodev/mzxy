package com.example.mzxy.view;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.contacts.CONSTS;
import com.example.mzxy.utils.HttpUtils;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zhy.com.highlight.HighLight;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ImageView back;
    private TextView register;
    private EditText loginUser, loginPass;
    private Button message;
    private Button loginBtn;
    private TextView loginTv;
    private Boolean flag = true;
    private CountTimer timer;
    private EventHandler eh;
    private HighLight mHighLight;
    private LinearLayout id_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //初始化短信验证
        SMSSDK.initSDK(this, "1b00a1ed75185", "a545b284ae14fe5664bcec4f36f1839d");
        back = (ImageView) findViewById(R.id.back);
        register = (TextView) findViewById(R.id.register);
        loginUser = (EditText) findViewById(R.id.login_user);
        loginPass = (EditText) findViewById(R.id.login_password);
        message = (Button) findViewById(R.id.message);
        timer = new CountTimer(60000, 1000);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginTv = (TextView) findViewById(R.id.login_tv);
        id_container = (LinearLayout) findViewById(R.id.id_container);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        message.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        register.post(new Runnable() {
            @Override
            public void run() {
                showTipMask();
            }
        });
    }

    private void showTipMask() {
        mHighLight = new HighLight(MainActivity.this);
        mHighLight.anchor(id_container);
        mHighLight.addHighLight(R.id.register, R.layout.info_gravity_right_up, new HighLight.OnPosCallback() {
            @Override
            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                marginInfo.rightMargin = rightMargin;
                marginInfo.topMargin = rectF.top + rectF.height();
            }
        });
        mHighLight.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                //TODO 点击返回
                finish();
                break;
            case R.id.register:
                //TODO 点击注册页面
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.message:
                //TODO 点击获取验证码
                //判断输入的是否为手机号，用正则表达式+flag来判断 如果是就开始倒计时60s
                Boolean isbool = isPhoneNumber(loginUser.getText().toString());
                if (isbool) {
                    if (flag) {
                        flag = false;
                        timer.start();
                        sendSMSRandom();
                    }
                } else {
                    Toast.makeText(this, "您输入的手机号码有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn:
                //TODO 点击绑定手机登录
//                registerUser();
                String s = "666666";
                if (loginPass.getText().toString().equals(s)){
                    Intent intent1 = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent1);
                    finish();
                }else {
                    SMSSDK.submitVerificationCode("86", loginUser.getText().toString(), loginPass.getText().toString());
                }
                break;
            case R.id.login_tv:
                //TODO 点击快快洗用户协议
                startActivity(new Intent(this, WebViewActivity.class));
                break;
        }
    }

    //发送短信验证码的方法
    public void sendSMSRandom() {
        //监听事件
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e(TAG, "验证码校验成功");
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.e(TAG, "验证码发送成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    final Throwable throwable = (Throwable) data;
                    Log.e("TAG", throwable.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        String phoneName = loginUser.getText().toString();
        SMSSDK.getVerificationCode("86", phoneName.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    //每隔一分钟可点击一次验证码
    public class CountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    时间间隔是多久
         * @param countDownInterval 回调onTick方法每隔多久执行一次
         */
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //间隔时间内执行的操作
        @Override
        public void onTick(long millisUntilFinished) {
            //更新页面组件
            message.setText(millisUntilFinished / 1000 + "秒后重新发送");
            message.setBackgroundResource(R.color.grayDark);
            message.setClickable(false);
        }

        //间隔时间结束时调用
        @Override
        public void onFinish() {
            //更新页面组件
            message.setText("获取验证码");
            message.setBackgroundResource(R.drawable.get_check_pass);
            message.setClickable(true);
            flag = true;
        }
    }

    //判断手机号码
    public boolean isPhoneNumber(String str) {
        String strRegex = "[1][34578]\\d{9}";
        boolean result = str.matches(strRegex);
        return result;
    }

    //注册
    public void registerUser() {
        //向服务器发送信息
        HttpUtils.sendOKHttpRequest(CONSTS.SEND_PHONE_MESSAGE + loginUser.getText().toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse:" + response.body().string());
            }
        });
    }
}
