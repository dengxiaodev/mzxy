package com.example.mzxy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.HomeAdapter;
import com.example.mzxy.utils.CountEvent;
import com.example.mzxy.utils.HttpUtils;
import com.example.mzxy.utils.LaundryUtils;
import com.example.mzxy.utils.ListLaundry;
import com.example.mzxy.utils.WinterJean;
import com.example.mzxy.view.HomeActivity;
import com.example.mzxy.view.MeansActivity;
import com.example.mzxy.view.WashActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private View view;
    private View popView;
    private ImageView[] imageDot;
    private ImageView[] imageViews;
    public static final int[] images = {R.mipmap.fragment_text1, R.mipmap.fragment_text2,
            R.mipmap.fragment_text3, R.mipmap.fragment_text4, R.mipmap.fragment_text5};
    private ViewPager viewPager;
    private LinearLayout viewGroup;
    private ImageView singleWash;
    private ImageView bagWash;
    private ImageView serviceDescription;

    private PopupWindow window;
    private TextView unit;
    private TextView fr_home_bag;
    private TextView fr_home_amounts;
    private ImageView fr_home_less;
    private TextView fr_home_num;
    private ImageView fr_home_plus;
    private Button fr_home_join;
    private int num;
    private int i = 1;
    private LaundryUtils laundryUtils;

    private HomeAdapter homeAdapter;
    //自动轮播定时器
    private ScheduledExecutorService scheduledExecutorService;
    //当前图片索引
    private int currentIndex;

    private final String URL = "http://cloud.bmob.cn/d9f6840be6bb07cf/wash_page?clive=bagpicture";
    private List<WinterJean.WashInfoEntity> washInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        init();
        initDot();
        initPopwindow();
        return view;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
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
        unit.setText(" /袋");
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
    }

    private void initDot() {
        imageDot = new ImageView[images.length];
        for (int i = 0; i < imageDot.length; i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.setMargins(0, 0, 10, 10);
            imageView.setLayoutParams(layoutParams);
            imageDot[i] = imageView;
            if (i == 0) {
                imageDot[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageDot[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            viewGroup.addView(imageView);
        }
        //将图片装载到数组中
        imageViews = new ImageView[imageDot.length];
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = new ImageView(getContext());
//            imageView.setImageDrawable(getResources().getDrawable(images[i]));
            imageView.setImageResource(images[i]);
            imageViews[i] = imageView;
        }
        //适配器初始化
        homeAdapter = new HomeAdapter(getContext(), images);
        //绑定适配器
        viewPager.setAdapter(homeAdapter);
        //添加手势滑动的监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //这里赋值是为了不让自动轮播出错，手动去滑动ViewPager时将position传给currentIndex
                currentIndex = position;
                setImageBackground(position % imageViews.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置当前条目，这里是为了让ViewPager一开始就能往左边滑动
        currentIndex = images.length * 1000;
        viewPager.setCurrentItem(images.length * 1000, true);
    }

    /**
     * 设置选中的imageDot的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < imageDot.length; i++) {
            if (i == selectItems) {
                imageDot[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageDot[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    private void init() {
        FragmentBasket.sum = 0;
        viewPager = (ViewPager) view.findViewById(R.id.home_view_pager);
        viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        singleWash = (ImageView) view.findViewById(R.id.single_wash);
        bagWash = (ImageView) view.findViewById(R.id.bag_wash);
        serviceDescription = (ImageView) view.findViewById(R.id.service_description);
        singleWash.setOnClickListener(this);
        bagWash.setOnClickListener(this);
        serviceDescription.setOnClickListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //刷新控件选中对象的图片
                    viewPager.setCurrentItem(currentIndex);
                    break;
            }
        }
    };

    //当界面可见时每隔5秒切换一次图片
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //切换图片
                currentIndex++;
                //刷新控件
                handler.sendEmptyMessage(1);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    //当界面不可见时关闭自动轮播
    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.single_wash://件洗
                Intent singleIntent = new Intent(getContext(), WashActivity.class);
                HomeActivity.jumpToPage(false);
                startActivity(singleIntent);
                break;
            case R.id.bag_wash://袋洗
                HttpUtils.sendOKHttpRequest(URL, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        Gson gson = new Gson();
                        WinterJean winterJean = gson.fromJson(responseText,WinterJean.class);
                        washInfo = new ArrayList<WinterJean.WashInfoEntity>();
                        washInfo = winterJean.washInfo;
                    }
                });
                if (window != null && window.isShowing()) {
                    window.dismiss();
                } else {
                    // 显示
                    window.showAsDropDown(bagWash, 10, 10);
                    backgroundAlpha(0.3f);
                }
                break;
            case R.id.service_description://服务说明
                Intent serviceIntent = new Intent(getContext(), MeansActivity.class);
                startActivity(serviceIntent);
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
                    if (isRepeat) {
                        for (int i = 0; i < ListLaundry.washJavas.size(); i++) {
                            laundryUtils = ListLaundry.washJavas.get(i);
                            if (name.equals(laundryUtils.getPictureName())) {
                                String s1 = laundryUtils.getCount();
                                int a1 = Integer.parseInt(s1);
                                String s2 = (String) fr_home_num.getText();
                                int a2 = Integer.parseInt(s2);
                                laundryUtils.setCount(a1 + a2 + "");
                            }
                        }
                    } else {
                        LaundryUtils utils = new LaundryUtils();
                        utils.setPicture(washInfo.get(0).getWashHead());
                        utils.setCount((String) fr_home_num.getText());
                        utils.setPictureName((String) fr_home_bag.getText());
                        utils.setAmounts((String) fr_home_amounts.getText());
                        ListLaundry.washJavas.add(utils);
                    }
                    EventBus.getDefault().post(new CountEvent(4));
                    window.dismiss();
                    fr_home_num.setText("1");
                    Toast.makeText(getContext(), "已加入洗衣篮", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private Boolean isRepeatData() {
        String name = (String) fr_home_bag.getText();
        for (int i = 0; i < ListLaundry.washJavas.size(); i++) {
            laundryUtils = ListLaundry.washJavas.get(i);
            if (name.equals(laundryUtils.getPictureName())) {
                return true;
            }
        }
        return false;
    }

}
