package com.example.mzxy.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mzxy.R;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] datas;

    public ViewPagerAdapter(Context context, int[] datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.view_pager_item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_image_view);
        //设置显示图片
        imageView.setImageResource(datas[position]);
        //添加到ViewPager
        container.addView(view);
        return view;
    }
}
