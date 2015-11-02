package com.jlstudio.publish.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.bean.PublishData;
import com.jlstudio.publish.dialog.ShowUnreplyPersonDialog;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowSendPublishAty extends BaseActivity implements View.OnClickListener {
    private TextView title, time, content, back, title_name;
    private TextView replyCount, unreplyCount, text1, text2;
    private LinearLayout unReply;
    private Button delete;
    private List<PublishData> list;
    private String datetime;
    private PublishData data;
    private DBOption db;
    private GetNotificationNet gn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_publish_send);
        gn = new GetNotificationNet(this);
        initView();
        initData();
    }

    private void initData() {
        getDatasFromNetbefore();
        Intent intent = getIntent();
        data = (PublishData) intent.getSerializableExtra("data");
        title.setText(data.getTitle());
        datetime = data.getTime();
        time.setText(datetime);
        content.setText(data.getContent());
        replyCount.setText(data.getReplyedCount());
        unreplyCount.setText(data.getUnReplyCount());
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);
        title_name = (TextView) findViewById(R.id.title_name);
        title = (TextView) findViewById(R.id.publish_title);
        time = (TextView) findViewById(R.id.publish_time);
        content = (TextView) findViewById(R.id.publish_content);
        unReply = (LinearLayout) findViewById(R.id.unReply);
        unreplyCount = (TextView) findViewById(R.id.unreplyCount);
        replyCount = (TextView) findViewById(R.id.replyedCount);
        content = (TextView) findViewById(R.id.publish_content);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        title_name.setText("显示通知");
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        unReply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, PublishDatasAty.class));
                ActivityContror.removeActivity(this);
                break;
            case R.id.unReply:
                new ShowUnreplyPersonDialog(this, datetime).show();
                break;
            case R.id.delete:
                deletepublish();
                break;
        }
    }

    private void deletepublish() {
        JSONObject json = new JSONObject();
        try {
            json.put("uid", Config.loadUser(this).getUsername());
            json.put("datetime", datetime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.getDepartment(Config.URL, Config.DELETEMYSENDNOTICE, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                ShowToast.show(ShowSendPublishAty.this, "删除成功");
                getDatasFromNetafter();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowSendPublishAty.this, "删除失败");
            }
        }, json.toString());
    }

    private void getDatasFromNetbefore() {
        gn.getDepartment(Config.URL, Config.SENDMSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                db = new DBOption(ShowSendPublishAty.this);
                if (s != null) {
                    list = JsonToPubhlishData.getPublishDataSend(s);
                    Intent intent = getIntent();
                    data = (PublishData) intent.getSerializableExtra("data");
                    for(PublishData d :list){
                        if(d.getTime().equals(d.getTime())){
                            title.setText(d.getTitle());
                            datetime = d.getTime();
                            time.setText(datetime);
                            content.setText(d.getContent());
                            replyCount.setText(d.getReplyedCount());
                            unreplyCount.setText(d.getUnReplyCount());
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }, Config.loadUser(ShowSendPublishAty.this).getUsername());

    }
    private void getDatasFromNetafter() {
        gn.getDepartment(Config.URL, Config.SENDMSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                db = new DBOption(ShowSendPublishAty.this);
                if (s != null) {
                    db.updateCatch(Config.URL + Config.SENDMSG+Config.loadUser(ShowSendPublishAty.this).getUsername(), s, System.currentTimeMillis() + "");
                    Intent intent = new Intent(ShowSendPublishAty.this, PublishDatasAty.class);
                    intent.setAction("refresh");
                    startActivity(intent);
                    ActivityContror.removeActivity(ShowSendPublishAty.this);
                } else {
                    ShowToast.show(ShowSendPublishAty.this, "没有通知");
                }
                Log.d("hehe", s);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowSendPublishAty.this, "获取数据失败");
                Log.d("hehe", "error");
            }
        }, Config.loadUser(ShowSendPublishAty.this).getUsername());

    }
}
