package com.example.mzxy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mzxy.R;
import com.example.mzxy.fragment.FragmentBasket;
import com.example.mzxy.fragment.FragmentHome;
import com.example.mzxy.fragment.FragmentMore;
import com.example.mzxy.fragment.FragmentMy;
import com.example.mzxy.utils.CountEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class HomeActivity extends FragmentActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private FragmentHomeAdapter homeAdapter;
    private static Boolean flag = false;
    private static int jumpPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        if (jumpPage == 1) {
            EventBus.getDefault().post(new CountEvent(4));
            viewPager.setCurrentItem(jumpPage);
        }
    }

    private void init() {
        EventBus.getDefault().register(this);
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main_tab);
        fragmentList = new ArrayList<>();
        FragmentHome fragmentHome = new FragmentHome();
        FragmentBasket fragmentBasket = new FragmentBasket();
        FragmentMy fragmentMy = new FragmentMy();
        FragmentMore fragmentMore = new FragmentMore();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentBasket);
        fragmentList.add(fragmentMy);
        fragmentList.add(fragmentMore);
        fragmentManager = getSupportFragmentManager();
        homeAdapter = new FragmentHomeAdapter(fragmentManager);
        viewPager.setAdapter(homeAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动页面改变后刷新选项卡界面
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
//                radioGroup.getChildAt(position).setClickable(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int postion = 0;
                switch (checkedId) {
                    case R.id.rb_main_home:
                        postion = 0;
                        break;
                    case R.id.rb_main_basket:
                        postion = 1;
                        break;
                    case R.id.rb_main_my:
                        postion = 2;
                        break;
                    case R.id.rb_main_more:
                        postion = 3;
                        break;
                }
                //RadioButton点击后改变页面
                viewPager.setCurrentItem(postion);
            }

        });
    }

    class FragmentHomeAdapter extends FragmentPagerAdapter {

        public FragmentHomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public void back() {
        viewPager.setCurrentItem(0);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUserEvent(CountEvent event) {
        int count = event.getCount();
        if (count == 1) {
            viewPager.setCurrentItem(count);
        }
        if (count == 2) {
            viewPager.setCurrentItem(2);
            viewPager.setCurrentItem(3);
            viewPager.setCurrentItem(2);
            viewPager.setCurrentItem(1);
            viewPager.setCurrentItem(0);
            viewPager.setCurrentItem(1);
        }
        if (count == 3) {
            viewPager.setCurrentItem(3);
            viewPager.setCurrentItem(2);
            viewPager.setCurrentItem(1);
            viewPager.setCurrentItem(0);
            for (int i = 0; i < fragmentList.size(); i++) {
                viewPager.setCurrentItem(i + 1);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag) {
            viewPager.setCurrentItem(1);
            EventBus.getDefault().post(new CountEvent(4));
        }
        flag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jumpPage = 0;
        EventBus.getDefault().unregister(this);
    }

    public static void jumpPage(int page) {
        jumpPage = page;
        flag = true;
    }

    public static void jumpToPage(Boolean flags) {
        flag = flags;
    }
}
