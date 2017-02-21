package com.example.mzxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mzxy.R;
import com.example.mzxy.smart.SmartImageView;
import com.example.mzxy.utils.WinterJean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/12 0012.
 */

public class WinterAdapter extends BaseAdapter {
    private Context context;
    private List<WinterJean.WashInfoEntity> list;

    public WinterAdapter(Context context, List<WinterJean.WashInfoEntity> list) {
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
        if (convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.winter_adapter_item,null);
            holder.springImage = (SmartImageView) convertView.findViewById(R.id.spring_image);
            holder.springName = (TextView) convertView.findViewById(R.id.spring_name);
            holder.springAmount = (TextView) convertView.findViewById(R.id.spring_price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        WinterJean.WashInfoEntity infoEntity = list.get(position);
        holder.springImage.setImageUrl(infoEntity.getWashHead(),R.mipmap.ic_launcher,R.mipmap.error);
        holder.springName.setText(infoEntity.getWashName());
        holder.springAmount.setText(infoEntity.getAmount());
        return convertView;
    }
    public static class ViewHolder{
        public SmartImageView springImage;
        public TextView springName;
        public TextView springAmount;
    }
}