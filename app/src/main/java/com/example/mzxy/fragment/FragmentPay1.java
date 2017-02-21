package com.example.mzxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.utils.ChangePasswordPicture;
import com.example.mzxy.view.PayActivity;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class FragmentPay1 extends Fragment implements View.OnClickListener, TextWatcher, View.OnKeyListener {
    private View view;
    private TextView pay_amounts;
    private RelativeLayout pay_relative;
    private EditText pay_edit1;
    private EditText pay_edit2;
    private EditText pay_edit3;
    private EditText pay_edit4;
    private Button pay_sure;
    private Button pay_reset;
    private String inputnumber;
    private ChangePasswordPicture changePasswordPicture;
    private OnEditTextListener onEditTextListener;
    private OnFocusListeners focusListeners;
    //    private OnKeyListeners onKeyListener;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pay1, null);
        init();
        return view;
    }

    private void init() {
        PayActivity activity = (PayActivity) getActivity();
        pay_amounts = (TextView) view.findViewById(R.id.pay_amounts);
        pay_amounts.setText(activity.amounts.substring(1) + ".00");
        pay_relative = (RelativeLayout) view.findViewById(R.id.pay_relative);
        pay_edit1 = (EditText) view.findViewById(R.id.pay_edit1);
        pay_edit2 = (EditText) view.findViewById(R.id.pay_edit2);
        pay_edit3 = (EditText) view.findViewById(R.id.pay_edit3);
        pay_edit4 = (EditText) view.findViewById(R.id.pay_edit4);
        pay_sure = (Button) view.findViewById(R.id.pay_sure);
        pay_reset = (Button) view.findViewById(R.id.pay_reset);
        pay_relative.setOnClickListener(this);
        pay_reset.setOnClickListener(this);
        pay_sure.setOnClickListener(this);
        changePasswordPicture = new ChangePasswordPicture();
        //密码默认样式改变监听
        pay_edit1.setTransformationMethod(changePasswordPicture);
        pay_edit2.setTransformationMethod(changePasswordPicture);
        pay_edit3.setTransformationMethod(changePasswordPicture);
        pay_edit4.setTransformationMethod(changePasswordPicture);
        //设置字符改变监听
        pay_edit1.addTextChangedListener(this);
        pay_edit2.addTextChangedListener(this);
        pay_edit3.addTextChangedListener(this);
        pay_edit4.addTextChangedListener(this);
        //焦点监听
        pay_edit1.setOnFocusChangeListener(focusListeners);
        pay_edit2.setOnFocusChangeListener(focusListeners);
        pay_edit3.setOnFocusChangeListener(focusListeners);
        pay_edit4.setOnFocusChangeListener(focusListeners);
        //删除按钮监听
        pay_edit1.setOnKeyListener(this);
        pay_edit2.setOnKeyListener(this);
        pay_edit3.setOnKeyListener(this);
        pay_edit4.setOnKeyListener(this);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pay_edit1.getWindowToken(), 0);
    }

    /**
     * 字符改变监听
     *
     * @param
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 1) {
            if (pay_edit1.isFocused()) {
                pay_edit2.setFocusable(true);
                pay_edit2.requestFocus();
                inputnumber = getEditString();
                if (onEditTextListener != null) {
                    onEditTextListener.inputComplete(4, inputnumber);
                }
            } else if (pay_edit2.isFocused()) {
                pay_edit3.setFocusable(true);
                pay_edit3.requestFocus();
                inputnumber = getEditString();
                if (onEditTextListener != null) {
                    onEditTextListener.inputComplete(4, inputnumber);
                }
            } else if (pay_edit3.isFocused()) {
                pay_edit4.setFocusable(true);
                pay_edit4.requestFocus();
                inputnumber = getEditString();
                if (onEditTextListener != null) {
                    onEditTextListener.inputComplete(4, inputnumber);
                }
            } else if (pay_edit4.isFocused()) {
                inputnumber = getEditString();
                if (onEditTextListener != null) {
                    onEditTextListener.inputComplete(4, inputnumber);
                }
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.e("keyCode", "KEYCODE_DEL");
            if (pay_edit4.isFocused()) {
                pay_edit3.requestFocus();
            } else if (pay_edit3.isFocused()) {
                pay_edit2.requestFocus();
            } else if (pay_edit2.isFocused()) {
                pay_edit1.requestFocus();
            }
        }
        return false;
    }

    /**
     * 删除按钮监听
     *
     * @author
     */
//    class OnKeyListeners implements View.OnKeyListener {
//        @Override
//        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            if(keyCode == KeyEvent.KEYCODE_DEL) {
//                //不知道不知道什么原因，点击一次删除按钮会调两次这个方法，所有处理一下，两次当一次
//                count++;
//                if (count < 1) {
//                    return false;
//                }
//                count=0;
//                Log.e("onKey", "KEYCODE_DEL");
//                if(pay_edit4.isFocused()){
//                    Log.e("onKey", "KEYCODE_DEL4");
//                    pay_edit4.clearFocus();
//                    pay_edit3.requestFocus();
//                }else if(pay_edit3.isFocused()){
//                    Log.e("onKey", "KEYCODE_DEL3");
//                    pay_edit3.clearFocus();
//                    pay_edit2.requestFocus();
//                }else if(pay_edit2.isFocused()){
//                    Log.e("onKey", "KEYCODE_DEL2");
//                    pay_edit2.clearFocus();
//                    pay_edit1.requestFocus();
//                }
//            }
//            return false;
//        }
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_relative://点击editText区域
                if (inputnumber == null) {
                    pay_edit1.requestFocus();
                } else {
                    switch (inputnumber.length()) {
                        case 1:
                            pay_edit2.requestFocus();
                            break;
                        case 2:
                            pay_edit3.requestFocus();
                            break;
                        case 3:
                            pay_edit4.requestFocus();
                            break;
                    }
                }
                //调用显示系统默认的输入法
                InputMethodManager m = (InputMethodManager) pay_edit1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                //这个方法可以实现输入法在窗口上切换显示，如果输入法在窗口上已经显示，则隐藏，如果隐藏，则显示输入法到窗口上
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.pay_reset://重置支付密码
                //点击事件打印用户输入的密码
                Toast.makeText(getActivity(), "" + getInputnumber(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay_sure://确认支付
                Toast.makeText(getActivity(), "你点击了确认支付", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 用户输入的数据
     *
     * @param
     */
    public String getEditString() {
        String number = pay_edit1.getText().toString();
        number += pay_edit2.getText().toString();
        number += pay_edit3.getText().toString();
        number += pay_edit4.getText().toString();
        return number;
    }

    /**
     * editText焦点监听
     *
     * @param
     */
    class OnFocusListeners implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.pay_edit1:
                    pay_edit1.requestFocus();
                    break;
                case R.id.pay_edit2:
                    pay_edit2.clearFocus();
                    pay_edit1.requestFocus();
                    break;
                case R.id.pay_edit3:
                    pay_edit3.clearFocus();
                    pay_edit1.requestFocus();
                    break;
            }
        }
    }


    public interface OnEditTextListener {
        void inputComplete(int state, String password);
    }

    public String getInputnumber() {
        return inputnumber;
    }

    public void setInputnumber(String inputnumber) {
        this.inputnumber = inputnumber;
    }
}
