package com.jlstudio.group.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.group.net.GetDataAction;

public class ContactsDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView contacts_name;
    private TextView contacts_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_data);
        initView();
        getData();
        initEvent();
    }

    private void initEvent() {
        contacts_back.setOnClickListener(this);
    }

    private void initView() {
        contacts_name = (TextView) findViewById(R.id.contacts_name);
        contacts_back = (TextView) findViewById(R.id.contacts_back);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        contacts_back.setTypeface(iconfont);
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        contacts_name.setText(bundle.getString("name"));
    }


    @Override
    public void onClick(View v) {
        ActivityContror.removeActivity(this);
    }

    /**
     * 从服务器获取好友的信息
     */
    public void setData() {
        GetDataAction action = new GetDataAction();
        String friend_info = action.getFriendData("getFriendInfo", "friends_name");
    }
}
