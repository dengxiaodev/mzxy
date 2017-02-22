package com.example.mzxy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mzxy.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class OpinionActivity extends SwipeBackActivity implements View.OnClickListener{
    private SwipeBackLayout mSwipeBackLayout;
    private ImageView back;
    private Button commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(500);
        back = (ImageView) findViewById(R.id.opinion_back);
        commit = (Button) findViewById(R.id.opinion_commit);
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opinion_back:
                finish();
                break;
            case R.id.opinion_commit:
                finish();
                Toast.makeText(this,"已提交",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
