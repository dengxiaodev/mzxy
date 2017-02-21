package com.example.mzxy.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.mzxy.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String name;
    private Button androidButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.web_view);
        androidButton = (Button) findViewById(R.id.web_view_btn);
        //设置编码
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        webView.addJavascriptInterface(this,"myObj");
        //载入js
        webView.loadUrl("file:///android_asset/agreement.html");
        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:clos()");
                Toast.makeText(WebViewActivity.this,"我是Android控件调用javaScript方法",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //不同的版本会出现不同的写法
    @JavascriptInterface
    public void fun1FromAndroid(String name){
        Toast.makeText(WebViewActivity.this,""+name,Toast.LENGTH_SHORT).show();
        handler.sendEmptyMessageDelayed(0,20);
        this.name = name;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            androidButton.setText(name);
        }
    };
}
