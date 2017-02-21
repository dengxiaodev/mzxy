package com.example.mzxy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.MeGridAdapter;
import com.example.mzxy.view.ShareActivity;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class FragmentMy extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private TextView user_name;
    private TextView user_phone;
    private Button fr_me_code;
    private GridView fr_me_grid;
    private MeGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        init();
        return view;
    }

    private void init() {
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_phone = (TextView) view.findViewById(R.id.user_phone);
        fr_me_code = (Button) view.findViewById(R.id.fr_me_code);
        fr_me_grid = (GridView) view.findViewById(R.id.fr_me_grid);
        SharedPreferences preferences = getActivity().getSharedPreferences("maizi", Activity.MODE_PRIVATE);
        String name = preferences.getString("name", user_name.getText().toString());
        String pass = preferences.getString("pass", user_phone.getText().toString());
        user_name.setText(name);
        user_phone.setText(pass);
        fr_me_code.setOnClickListener(this);
        adapter = new MeGridAdapter(getActivity());
        fr_me_grid.setAdapter(adapter);
        fr_me_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://我的订单
                break;
            case 3://分享验证码
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fr_me_code://推荐码
                Toast.makeText(getActivity(), fr_me_code.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
