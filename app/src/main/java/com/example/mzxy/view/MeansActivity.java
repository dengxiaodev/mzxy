package com.example.mzxy.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mzxy.R;
import com.example.mzxy.adapter.MeansAdapter;
import com.example.mzxy.utils.Means;
import com.example.mzxy.welcome.GetDataByVolley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MeansActivity extends SwipeBackActivity {
    private PullToRefreshListView mPullRefreshListView;
    private MeansAdapter adapter;
    private List<Means.MeansInfoBean> list;
    private String url = "http://cloud.bmob.cn/d9f6840be6bb07cf/service_test?clive=means";
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(500);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        GetDataByVolley.getStringByGet(url, "means", new GetDataByVolley.CallBack() {
            @Override
            public void returnData(Object result) {
                if (result == null) {
                    Toast.makeText(MeansActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    Means means = gson.fromJson((String) result, Means.class);
                    list = means.MeansInfo;
                    adapter = new MeansAdapter(MeansActivity.this, list);
                    mPullRefreshListView.setAdapter(adapter);
                    Log.e("MeansActivity", "" + result);
                }
            }
        });
        /** 设置PullToRefresh
         * Mode.BOTH：同时支持上拉下拉
         * Mode.PULL_FROM_START：只支持下拉Pulling Down
         * Mode.PULL_FROM_END：只支持上拉Pulling Up

         * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。
         * 如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。
         * 当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
         * Mode.PULL_FROM的时候只调用onPullUpToRefresh()方法.
         */

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (list != null) {
                    list.clear();
                    GetDataByVolley.getStringByGet(url, "means", new GetDataByVolley.CallBack() {
                        @Override
                        public void returnData(Object result) {
                            if (result == null) {
                                Toast.makeText(MeansActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                Means means = gson.fromJson((String) result, Means.class);
                                list = means.MeansInfo;
                                adapter = new MeansAdapter(MeansActivity.this, list);
                                mPullRefreshListView.setAdapter(adapter);
                                //设置刷新完成
//                                mPullRefreshListView.onRefreshComplete();
                            }
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshListView.onRefreshComplete();
                        }
                    }, 500);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                GetDataByVolley.getStringByGet(url, "means", new GetDataByVolley.CallBack() {
                    @Override
                    public void returnData(Object result) {
                        if (result == null) {
                            Toast.makeText(MeansActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            Means means = gson.fromJson((String) result, Means.class);
                            for (int i = 0; i < means.MeansInfo.size(); i++) {
                                Means.MeansInfoBean infoBean = means.MeansInfo.get(i);
                                list.add(infoBean);
                            }
                        }
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                    }
                }, 500);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetDataByVolley.cancelRequest("means");
    }
}
