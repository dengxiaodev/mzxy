package com.example.mzxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mzxy.R;
import com.example.mzxy.utils.Means;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class MeansAdapter extends BaseAdapter {
    private Context context;
    private List<Means.MeansInfoBean> list;

    public MeansAdapter(Context context, List<Means.MeansInfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.means_item, null);
            holder.means_Image = (ImageView) convertView.findViewById(R.id.means_Image);
            holder.means_Text = (TextView) convertView.findViewById(R.id.means_Text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Means.MeansInfoBean infoBean = list.get(position);
        Glide.with(context)
                .load(infoBean.getImageMeans())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
                .override(30, 30)
                .into(holder.means_Image);
        holder.means_Text.setText(infoBean.getTextMeans());
        return convertView;
    }

    public static class ViewHolder {
        public ImageView means_Image;
        public TextView means_Text;
    }
}
