package com.example.mzxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mzxy.R;
import com.example.mzxy.utils.GlideCircleTransform;
import com.example.mzxy.utils.ListUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<ListUtils.UserInfoBean> list;

    public ListAdapter(Context context, List<ListUtils.UserInfoBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.coat_adapter_item, null);
            holder.imageHead = (ImageView) convertView.findViewById(R.id.head);
            holder.textName = (TextView) convertView.findViewById(R.id.name);
            holder.textSignature = (TextView) convertView.findViewById(R.id.signature);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ListUtils.UserInfoBean info = list.get(position);
        Glide.with(context)
                .load(info.getHead())
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
                .into(holder.imageHead);

        holder.textName.setText(info.getName());
        holder.textSignature.setText(info.getSignature());
        return convertView;
    }

    public class ViewHolder {
        public ImageView imageHead;
        public TextView textName;
        public TextView textSignature;
    }
}
