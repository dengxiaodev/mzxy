package com.example.mzxy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mzxy.R;
import com.example.mzxy.task.BitmapTask;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class HomeAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] datas;

    public HomeAdapter(Context context, int[] datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //这里给适配器返回一个很大的渲染条目数
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //渲染每一页的数据
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.view_pager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_image_view);
        //设置显示图片，这里设置的为默认图片
        imageView.setImageResource(R.drawable.vp_default);
        //异步加载图片
        BitmapTask bitmapTask = new BitmapTask(context, imageView);
        //取余数 使下标规律一直为0-1-2-3-0-1-2-3
        bitmapTask.execute(datas[position % datas.length]);
        //添加到ViewPager中
        container.addView(view);
        return view;
    }
}
