package com.example.mzxy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mzxy.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class OpinionActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(500);
        back = (ImageView) findViewById(R.id.opinion_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
