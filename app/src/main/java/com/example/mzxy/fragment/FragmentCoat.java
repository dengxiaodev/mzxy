package com.example.mzxy.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.ListAdapter;
import com.example.mzxy.utils.HttpUtils;
import com.example.mzxy.utils.ListUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCoat extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private final String URL = "http://cloud.bmob.cn/d9f6840be6bb07cf/friends_test?clive=contacts";
    private ListView listView;
    private List<ListUtils.UserInfoBean> infoBeanList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    listView.setAdapter(new ListAdapter(getActivity(), infoBeanList));
                    listView.setOnItemClickListener(FragmentCoat.this);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coat, null);
        listView = (ListView) view.findViewById(R.id.coat_list_view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            HttpUtils.sendOKHttpRequest(URL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Gson gson = new Gson();
                    ListUtils coatList = gson.fromJson(responseText,ListUtils.class);
                    infoBeanList = new ArrayList<ListUtils.UserInfoBean>();
                    infoBeanList = coatList.userInfo;
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "我是" + infoBeanList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
