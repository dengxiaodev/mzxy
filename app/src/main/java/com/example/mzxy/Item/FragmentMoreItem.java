package com.example.mzxy.Item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mzxy.R;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class FragmentMoreItem extends RelativeLayout {
    private ImageView more_image;
    private TextView more_customer;
    private ImageView more_right_arrow;
    public FragmentMoreItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_more_item,this);
        more_image = (ImageView) view.findViewById(R.id.more_image);
        more_customer = (TextView) view.findViewById(R.id.more_customer);
        more_right_arrow = (ImageView) view.findViewById(R.id.more_right_arrow);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.FragmentMoreItem);
        Drawable image = typedArray.getDrawable(R.styleable.FragmentMoreItem_fragmentMore_image);
        String text = typedArray.getString(R.styleable.FragmentMoreItem_fragmentMore_text);
        Drawable arrow = typedArray.getDrawable(R.styleable.FragmentMoreItem_fragmentMore_arrow);

        more_image.setImageDrawable(image);
        more_customer.setText(text);
        more_right_arrow.setImageDrawable(arrow);

        typedArray.recycle();

    }
}
