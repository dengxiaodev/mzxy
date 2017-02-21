package com.example.mzxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.utils.LaundryUtils;
import com.example.mzxy.view.PayActivity;
import com.example.mzxy.view.RegisterActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class BasketAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<LaundryUtils> list;
    private View fragment_basket_item;
    private ImageView bg_null;
    private final int ITEM = 0;
    private final int ITEM1 = 1;
    private final int ITEMSUM = 2;
    private int currentType;
    private TextView basket_item_price;
    private TextView basket_item_finally_price;
    private RelativeLayout basket_item_info_relative;
    private Button basket_item_pay;
    private TextView fr_basket_num;
    private LaundryUtils utils;
    private int count;

    public BasketAdapter(Context context, List<LaundryUtils> list, View fragment_basket_item, ImageView bg_null) {
        this.context = context;
        this.list = list;
        this.fragment_basket_item = fragment_basket_item;
        this.bg_null = bg_null;
    }

    @Override
    public int getItemViewType(int position) {
        int count = list.size();
        if (count > position) {
            return ITEM;
        } else {
            return ITEM1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return ITEMSUM;
    }

    @Override
    public int getCount() {
        if (list.size() == 0) {
            bg_null.setVisibility(View.VISIBLE);
            return 0;
        } else {
            return list.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View mCourseView = null;

        currentType = getItemViewType(position);
        if (currentType == ITEM1) {
            convertView = fragment_basket_item;
            basket_item_price = (TextView) convertView.findViewById(R.id.basket_item_price);
            basket_item_finally_price = (TextView) convertView.findViewById(R.id.basket_item_finally_price);
            basket_item_info_relative = (RelativeLayout) convertView.findViewById(R.id.basket_item_info_relative);
            basket_item_info_relative.setOnClickListener(this);
            basket_item_pay = (Button) convertView.findViewById(R.id.basket_item_pay);
            basket_item_pay.setOnClickListener(this);
        } else {
//            mCourseView = convertView;
            convertView = View.inflate(context, R.layout.basket_adapter_item, null);
            ImageView fr_basket_clothes = (ImageView) convertView.findViewById(R.id.fr_basket_clothes);
            TextView fr_basket_font = (TextView) convertView.findViewById(R.id.fr_basket_font);
            fr_basket_num = (TextView) convertView.findViewById(R.id.fr_basket_num);
            TextView fr_basket_amounts = (TextView) convertView.findViewById(R.id.fr_basket_amounts);
            ImageView fr_basket_remove = (ImageView) convertView.findViewById(R.id.fr_basket_remove);
            ImageView fr_basket_less = (ImageView) convertView.findViewById(R.id.fr_basket_less);
            ImageView fr_basket_plus = (ImageView) convertView.findViewById(R.id.fr_basket_plus);
            utils = list.get(position);
            fr_basket_less.setOnClickListener(this);
            fr_basket_plus.setOnClickListener(this);
            fr_basket_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0){
                        list.clear();
                    }else {
                        list.remove(position);
//                    EventBus.getDefault().post(new CountEvent(2));
                    }
                }
            });
//            ImageLoader初始化
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                    context).threadPoolSize(5).build();
            ImageLoader.getInstance().init(configuration);
            //ImageLoader配置
            DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.error)
                    .displayer(new FadeInBitmapDisplayer(100)).displayer(new RoundedBitmapDisplayer(50)).build();
            //ImageLoader异步加载
            ImageLoader.getInstance().displayImage(utils.getPicture(), fr_basket_clothes, options);
            fr_basket_font.setText(utils.getPictureName());
            fr_basket_num.setText(utils.getCount());
            fr_basket_amounts.setText(utils.getAmounts());
//            convertView=mCourseView;
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basket_item_info_relative://用户信息
                Intent mIntent = new Intent(context, RegisterActivity.class);
                context.startActivity(mIntent);
                break;
            case R.id.basket_item_pay://立即支付
                Intent intent= new Intent(context, PayActivity.class);
                intent.putExtra("amounts", basket_item_finally_price.getText().toString());
                context.startActivity(intent);
                break;
            case R.id.fr_basket_less://减
                count = Integer.valueOf(utils.getCount());
                if (count > 1) {
                    fr_basket_num.setText((count - 1) + "");
                } else {
                    Toast.makeText(context, "已经是最后一件咯~", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fr_basket_plus://加
                count = Integer.valueOf(utils.getCount());
                fr_basket_num.setText((count + 1) + "");
                break;
        }
    }
}
