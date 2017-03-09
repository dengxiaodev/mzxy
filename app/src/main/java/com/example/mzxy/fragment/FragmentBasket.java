package com.example.mzxy.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.BasketAdapter;
import com.example.mzxy.utils.CountEvent;
import com.example.mzxy.utils.LaundryUtils;
import com.example.mzxy.utils.ListLaundry;
import com.example.mzxy.view.HomeActivity;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class FragmentBasket extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView basket_back;
    private TextView remove_all;
    private ListView basket_list;
    private ImageView bg_null;
    public static int sum;
    private View basket_item;
    private TextView basket_item_price;
    private TextView basket_item_finally_price;
    private BasketAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, null);
        init();
        return view;
    }

    private void init() {
        EventBus.getDefault().register(this);
        basket_back = (ImageView) view.findViewById(R.id.basket_back);
        remove_all = (TextView) view.findViewById(R.id.basket_remove_all);
        basket_list = (ListView) view.findViewById(R.id.basket_list);
        bg_null = (ImageView) view.findViewById(R.id.bg_null);
        basket_back.setOnClickListener(this);
        remove_all.setOnClickListener(this);
        basket_item = View.inflate(getActivity(), R.layout.fragment_basket_item, null);
        basket_item_price = (TextView) basket_item.findViewById(R.id.basket_item_price);
        basket_item_finally_price = (TextView) basket_item.findViewById(R.id.basket_item_finally_price);
        adapter = new BasketAdapter(getActivity(), ListLaundry.washJavas, basket_item, bg_null);
        basket_list.setAdapter(adapter);
        priceCount();
        adapter.notifyDataSetChanged();
        bg_null.setVisibility(View.INVISIBLE);
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(CountEvent event) {
        int count = event.getCount();
        if (count == 4) {
            if (ListLaundry.washJavas.size() == 0 && basket_list != null) {
                bg_null.setVisibility(View.VISIBLE);
            } else if (ListLaundry.washJavas.size() != 0) {
                bg_null.setVisibility(View.GONE);
            }
            basket_list.setAdapter(adapter);
            priceCount();
            adapter.notifyAll();
        }
    }

    private void priceCount() {
        for (int i = 0; i < ListLaundry.washJavas.size(); i++) {
            LaundryUtils utils = ListLaundry.washJavas.get(i);
            String utilsAmounts = utils.getAmounts().substring(1);
            String utilsCount = utils.getCount();
            int mAmounts = Integer.valueOf(utilsAmounts);
            int mCount = Integer.valueOf(utilsCount);
            sum += mAmounts * mCount;
            Log.e("sum", sum + "");
        }
        basket_item_price.setText("￥" + sum);
        if (sum > 30) {
            basket_item_finally_price.setText("￥" + (sum - 30));
        } else {
            basket_item_finally_price.setText("￥" + 0);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basket_back://返回
                HomeActivity activity = (HomeActivity) getActivity();
                activity.back();
                break;
            case R.id.basket_remove_all://删除订单
                adapter.notifyDataSetChanged();
                if (ListLaundry.washJavas.size() != 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("您是否要清空购物车？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ListLaundry.washJavas.removeAll(ListLaundry.washJavas);
                            adapter.notifyDataSetChanged();
                            Toast toast = Toast.makeText(getActivity(),"已清空购物车",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM ,0, 200);
                            toast.show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast toast = Toast.makeText(getActivity(),"已取消",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM ,0, 200);
                            toast.show();
                        }
                    });
                    dialog.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(),"洗衣篮已经是空的~",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM ,0, 200);
                    toast.show();
                }
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
