package com.example.mzxy.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mzxy.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener,View.OnTouchListener{
    private ImageView back;
    private EditText register_name;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton lady;
    private EditText register_phone;
    private Spinner city;
    private EditText detailed;
    private Button register_save;
    private ArrayAdapter<String> adapter;
    private static final String[] cities = {"南京", "上海", "北京", "广州", "深圳", "苏州", "杭州", "重庆"};
    private String address;
    private String genderText;
    private LinearLayout registerLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        registerLinear = (LinearLayout) findViewById(R.id.register_linear_layout);
        back = (ImageView) findViewById(R.id.register_back);
        register_name = (EditText) findViewById(R.id.register_name);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        lady = (RadioButton) findViewById(R.id.lady);
        register_phone = (EditText) findViewById(R.id.register_phone);
        city = (Spinner) findViewById(R.id.city);
        detailed = (EditText) findViewById(R.id.detailed);
        register_save = (Button) findViewById(R.id.register_save);
        registerLinear.setOnTouchListener(this);
        register_name.setOnFocusChangeListener(this);
        register_phone.setOnFocusChangeListener(this);
        detailed.setOnFocusChangeListener(this);
        back.setOnClickListener(this);
        register_save.setOnClickListener(this);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = (String) city.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        genderText = "先生";
                        break;
                    case R.id.lady:
                        genderText = "女士";
                        break;
                }
            }
        });
        getUserInfo();
    }

    /**
     * 保存注册页面的信息
     */
    public void getUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("maizi",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String pass = sharedPreferences.getString("pass","");
        String gender = sharedPreferences.getString("gender","先生");
        String address = sharedPreferences.getString("address","南京");
        String detail = sharedPreferences.getString("detailed","");
        register_name.setText(name);
        register_phone.setText(pass);
        if (gender.equals("女士")){
            lady.setChecked(true);
        }else {
            male.setChecked(true);
        }
        if (address.equals("南京")){
            city.setSelection(0);
        }else if (address.equals("上海")){
            city.setSelection(1);
        }else if (address.equals("北京")){
            city.setSelection(2);
        }else if (address.equals("广州")){
            city.setSelection(3);
        }else if (address.equals("深圳")){
            city.setSelection(4);
        }else if (address.equals("苏州")){
            city.setSelection(5);
        }else if (address.equals("杭州")){
            city.setSelection(6);
        }else if (address.equals("重庆")){
            city.setSelection(7);
        }else {
            city.setSelection(0);
        }
        detailed.setText(detail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                RegisterActivity.this.finish();
                break;
            case R.id.register_save:
                //实例化SharedPreferences对象（第一步）
                SharedPreferences mySharedPreferences = getSharedPreferences("maizi", Activity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象（第二步）
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString("name", register_name.getText().toString());
                editor.putString("pass", register_phone.getText().toString());
                editor.putString("gender", genderText);
                editor.putString("address", address);
                editor.putString("detailed", detailed.getText().toString());
                //提交当前数据
                editor.apply();
                RegisterActivity.this.finish();
                break;
        }
    }
    /**
     * 下面的OnFocusChangeListener的作用主要是
     * 点击EditText时获取焦点并隐藏hint值
     * */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.register_name:
                setHintEt(register_name,hasFocus);
                break;
            case R.id.register_phone:
                setHintEt(register_phone,hasFocus);
                break;
            case R.id.detailed:
                setHintEt(detailed,hasFocus);
                break;
        }
    }
    private void setHintEt(EditText et,boolean hasFocus){
        String hint;
        if(hasFocus){
            hint = et.getHint().toString();
            et.setTag(hint);
            et.setHint("");
        }else{
            hint = et.getTag().toString();
            et.setHint(hint);
        }
    }
    /**
     * 给布局加触摸监听，当点击EditText之外的地方时
     * EditText失去焦点
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        registerLinear.setFocusable(true);
        registerLinear.setFocusableInTouchMode(true);
        registerLinear.requestFocus();//布局获得焦点
        return false;
    }


}
