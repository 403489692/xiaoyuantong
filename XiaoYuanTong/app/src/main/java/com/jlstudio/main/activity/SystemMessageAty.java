package com.jlstudio.main.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.main.application.ActivityContror;

public class SystemMessageAty extends BaseActivity implements View.OnClickListener {
    private TextView back,title_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        initView();
    }
    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("系统消息");
        back.setOnClickListener(this);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
    }

    @Override
    public void onClick(View v) {
        //startActivity(new Intent(this, InitDataActivity.class));
        ActivityContror.removeActivity(this);
    }
}
