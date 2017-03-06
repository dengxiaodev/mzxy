package com.example.mzxy.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.example.mzxy.Item.FragmentMoreItem;
import com.example.mzxy.R;
import com.example.mzxy.utils.CountEvent;
import com.example.mzxy.view.HomeActivity;
import com.example.mzxy.view.OpinionActivity;

import de.greenrobot.event.EventBus;

import static com.example.mzxy.R.id.more_exit;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class FragmentMore extends Fragment implements View.OnClickListener, ActionSheet.ActionSheetListener {
    private View view;
    private ImageView moreBack;
    private FragmentMoreItem moreReached;
    private FragmentMoreItem moreService;
    private FragmentMoreItem moreUser;
    private FragmentMoreItem moreOpinion;
    private Button moreExit;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private String name;
    private String pass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, null);
        init();
        return view;
    }

    private void init() {
        moreBack = (ImageView) view.findViewById(R.id.more_back);
        moreReached = (FragmentMoreItem) view.findViewById(R.id.fragment_more_reached);
        moreService = (FragmentMoreItem) view.findViewById(R.id.fragment_more_service);
        moreUser = (FragmentMoreItem) view.findViewById(R.id.fragment_more_user);
        moreOpinion = (FragmentMoreItem) view.findViewById(R.id.fragment_more_opinion);
        moreExit = (Button) view.findViewById(more_exit);
        moreBack.setOnClickListener(this);
        moreReached.setOnClickListener(this);
        moreService.setOnClickListener(this);
        moreUser.setOnClickListener(this);
        moreOpinion.setOnClickListener(this);
        moreExit.setOnClickListener(this);
        mSharedPreferences = getActivity().getSharedPreferences("maizi", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        name = mSharedPreferences.getString("name", "用户名");
        pass = mSharedPreferences.getString("pass", "手机号码");
        if (name.equals("用户名") && pass.equals("手机号码")) {
            moreExit.setVisibility(View.GONE);
        }else {
            moreExit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_back://返回
                HomeActivity activity = (HomeActivity) getActivity();
                activity.back();
                break;
            case R.id.fragment_more_reached://联系客服
                ActionSheet.createBuilder(getContext(), getActivity().getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("客服电话:1388888888", "确认拨打")
                        .setCancelableOnTouchOutside(true)
                        .setListener(this)
                        .show();
                break;
            case R.id.fragment_more_service://服务范围
                //TODO 百度地图
                break;
            case R.id.fragment_more_user://用户协议
                break;
            case R.id.fragment_more_opinion://意见反馈
                Intent intent = new Intent(getActivity(), OpinionActivity.class);
                startActivity(intent);
                break;
            case more_exit://退出登录
                editor.remove("name");
                editor.remove("pass");
                editor.remove("gender");
                editor.remove("address");
                editor.remove("detailed");
                editor.clear();
                editor.commit();
                moreExit.setVisibility(View.GONE);
                EventBus.getDefault().post(new CountEvent(3));
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("DIAL", "onRequestPermissionsResult:1111111");
                    callReached();
                } else {
                    Toast.makeText(getContext(), "您需要同意通话权限后再重试!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (index == 1) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("DIAL", "onRequestPermissionsResult:222222");
                FragmentMore.this.requestPermissions(new String[]{
                        Manifest.permission.CALL_PHONE}, 1); //第三个参数为requestCode
            } else {
                callReached();
            }
        }
    }

    //拨打客服电话
    public void callReached() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1388888888"));
        startActivity(intent);
    }
}
