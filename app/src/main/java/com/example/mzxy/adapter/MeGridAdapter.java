package com.example.mzxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mzxy.R;

/**
 * Created by Administrator on 2017/2/19 0019.
 */

public class MeGridAdapter extends BaseAdapter {
    private Context context;
    private String[] names;
    private static int[] icons = {R.mipmap.fragment_me_grid1,
            R.mipmap.fragment_me_grid2, R.mipmap.fragment_me_grid3,
            R.mipmap.fragment_me_grid4, R.mipmap.fragment_me_grid5};

    public MeGridAdapter(Context context) {
        this.context = context;
        this.names = context.getResources().getStringArray(R.array.me_grid_names);
    }

    @Override
    public int getCount() {
        return icons.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.fragment_me_adapter_item,null);
            holder.gridImage = (ImageView) convertView.findViewById(R.id.fragment_me_adapter_image);
            holder.gridText = (TextView) convertView.findViewById(R.id.fragment_me_adapter_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gridImage.setImageResource(icons[position]);
        holder.gridText.setText(names[position]);
        return convertView;
    }

    public class ViewHolder {
        public ImageView gridImage;
        public TextView gridText;
    }
}
