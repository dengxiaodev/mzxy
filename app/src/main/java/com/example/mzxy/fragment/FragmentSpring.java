package com.example.mzxy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.WinterAdapter;
import com.example.mzxy.utils.LaundryUtils;
import com.example.mzxy.utils.WinterJean;
import com.example.mzxy.view.HomeActivity;
import com.example.mzxy.welcome.GetDataByVolley;
import com.example.mzxy.welcome.MyApplication;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import static com.example.mzxy.R.mipmap.laundry;

/**
 * Created by Administrator on 2017/2/12 0012.
 */

public class FragmentSpring extends Fragment implements View.OnClickListener {
    private View view;
    private GridView springGridView;
    private ImageView basketImage;
    private final String URL = "http://cloud.bmob.cn/d9f6840be6bb07cf/wash_spring";
    private List<WinterJean.WashInfoEntity> infoEntityList;
    private int positionCount;
    private View popView;
    private PopupWindow window;
    private TextView unit;
    private ImageView fr_home_picture;
    private TextView fr_home_bag;
    private TextView fr_home_amounts;
    private ImageView fr_home_less;
    private TextView fr_home_num;
    private ImageView fr_home_plus;
    private Button fr_home_join;
    private int num;
    private int i = 1;
    private LaundryUtils laundryUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spring, null);
        init();
        return view;
    }

    private void init() {
        springGridView = (GridView) view.findViewById(R.id.spring_grid_view);
        basketImage = (ImageView) view.findViewById(R.id.basket_image);
        if (MyApplication.washJavas.size() != 0) {
            basketImage.setImageResource(R.mipmap.laundry_dot);
        } else {
            basketImage.setImageResource(laundry);
        }
        basketImage.setOnClickListener(this);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initPopwindow() {
        popView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_popwindow, null);
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);// 设置非PopupWindow区域可触摸
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        unit = (TextView) popView.findViewById(R.id.textUnit);
        unit.setText(" /件");
        fr_home_picture = (ImageView) popView.findViewById(R.id.fr_home_picture);
        fr_home_bag = (TextView) popView.findViewById(R.id.fr_home_bag);
        fr_home_amounts = (TextView) popView.findViewById(R.id.fr_home_amounts);
        fr_home_less = (ImageView) popView.findViewById(R.id.fr_home_less);
        fr_home_num = (TextView) popView.findViewById(R.id.fr_home_num);
        fr_home_plus = (ImageView) popView.findViewById(R.id.fr_home_plus);
        fr_home_join = (Button) popView.findViewById(R.id.fr_home_join);
        fr_home_less.setOnClickListener(this);
        fr_home_num.setOnClickListener(this);
        fr_home_plus.setOnClickListener(this);
        fr_home_join.setOnClickListener(this);
        springGridView.setAdapter(new WinterAdapter(getActivity(), infoEntityList));
        springGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionCount = position;
                showPopWindow(position);
                if (window != null && window.isShowing()) {
                    window.dismiss();
                } else {
                    window.showAtLocation(popView, Gravity.BOTTOM, 10, 10);
                    backgroundAlpha(0.3f);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            GetDataByVolley.getStringByGet(URL, "spring", new GetDataByVolley.CallBack() {
                @Override
                public void returnData(Object result) {
                    if (result == null) {
                        Toast.makeText(getContext(), "加载失败，请重试", Toast.LENGTH_SHORT).show();
                    } else {
                        Gson gson = new Gson();
                        WinterJean winterJean = gson.fromJson((String) result, WinterJean.class);
                        infoEntityList = new ArrayList<WinterJean.WashInfoEntity>();
                        infoEntityList = winterJean.washInfo;
                        initPopwindow();
                    }
                }
            });
        }
    }


    private void showPopWindow(int position) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getActivity()).threadPoolSize(5).build();
        ImageLoader.getInstance().init(configuration);
        //ImageLoader配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.error)
                .displayer(new FadeInBitmapDisplayer(100))
                .displayer(new RoundedBitmapDisplayer(50))
                .build();
        //头像
        ImageLoader.getInstance().displayImage(infoEntityList.get(position).getWashHead(), fr_home_picture, options);
        fr_home_bag.setText(infoEntityList.get(position).getWashName());
        fr_home_amounts.setText(infoEntityList.get(position).getAmount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basket_image://洗衣篮小图标
                HomeActivity.jumpPage(1);
                getActivity().finish();
                HomeActivity.jumpToPage(true);
                break;
            //popWindow中：
            case R.id.fr_home_less://减号
                String less = (String) fr_home_num.getText();
                num = Integer.valueOf(less);
                if (num <= i) {
                    Toast.makeText(getContext(), "已经是最后一件咯~", Toast.LENGTH_SHORT).show();
                } else {
                    fr_home_num.setText(num - 1 + "");
                }
                break;
            case R.id.fr_home_num://数量
                break;
            case R.id.fr_home_plus://加号
                String plus = (String) fr_home_num.getText();
                num = Integer.valueOf(plus);
                fr_home_num.setText(num + 1 + "");
                break;
            case R.id.fr_home_join://加入洗衣篮
                if (window != null && window.isShowing()) {
                    Boolean isRepeat = isRepeatData();
                    String name = (String) fr_home_bag.getText();
                    Log.e("Spring", "件洗" + isRepeat);
                    if (isRepeat) {
                        for (int i = 0; i < MyApplication.washJavas.size(); i++) {
                            laundryUtils = MyApplication.washJavas.get(positionCount);
//                            laundryUtils = MyApplication.washJavas.get(i);
                            if (name.equals(laundryUtils.getPictureName())) {
                                String s1 = laundryUtils.getCount();
                                int a1 = Integer.parseInt(s1);
                                String s2 = (String) fr_home_num.getText();
                                int a2 = Integer.parseInt(s2);
                                laundryUtils.setCount(a1 + a2 + "");
                                break;
                            }
                        }
                    } else {
                        LaundryUtils utils = new LaundryUtils();
                        utils.setPicture(infoEntityList.get(positionCount).getWashHead());
//                        utils.setPicture(infoEntityList.get(i).getWashHead());
                        utils.setCount((String) fr_home_num.getText());
                        utils.setPictureName((String) fr_home_bag.getText());
                        utils.setAmounts((String) fr_home_amounts.getText());
                        MyApplication.washJavas.add(utils);
                    }
                    Toast.makeText(getContext(), "已加入洗衣篮", Toast.LENGTH_SHORT).show();
                    fr_home_num.setText("1");
                    if (MyApplication.washJavas.size() != 0) {
                        basketImage.setImageResource(R.mipmap.laundry_dot);
                    } else {
                        basketImage.setImageResource(R.mipmap.laundry);
                    }
                    window.dismiss();
                }
                break;
        }
    }

    private Boolean isRepeatData() {
        String name = (String) fr_home_bag.getText();
        for (int i = 0; i < MyApplication.washJavas.size(); i++) {
            laundryUtils = MyApplication.washJavas.get(i);
            if (name.equals(laundryUtils.getPictureName())) {
                return true;
            }
        }
        return false;
    }
}