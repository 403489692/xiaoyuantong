package com.jlstudio.publish.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.bean.PublishData;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowReceivePublishAty extends BaseActivity implements View.OnClickListener {
    private TextView title, time, content, back, title_name, content_name, publish_user;
    private Button delete;
    private String flag;
    private DBOption db;
    private String temptime;
    private GetNotificationNet gn;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_msg);
        gn = new GetNotificationNet(this);
        action = getIntent().getAction();
        initView();
        initData();
    }

    private void initData() {
        if (action != null) {
            String datas = getIntent().getStringExtra("msg");
            try {
                JSONObject json = new JSONObject(datas);
                title.setText(json.getString("title"));
                temptime = json.getString("datetime");
               //temptime = temptime.substring(0,temptime.length()-3)+"0";
                time.setText(temptime);
                content_name.setText(json.getString("content"));
                delete.setText("收到");
                flag = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            PublishData data = (PublishData) getIntent().getSerializableExtra("data");
            title.setText(data.getTitle());
            temptime = data.getTime();
            time.setText(temptime);
            publish_user.setText(data.getId());
            if (!StringUtil.isEmpty(data.getContent())) {
                content_name.setText(data.getContent());
            }
            time.setText(temptime);
            flag = data.getFlag();
            if (flag.equals("0")) delete.setText("收到");
            else delete.setText("删除");
        }

    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);
        publish_user = (TextView) findViewById(R.id.publish_user);
        title_name = (TextView) findViewById(R.id.title_name);
        title = (TextView) findViewById(R.id.publish_title);
        time = (TextView) findViewById(R.id.publish_time);
        content_name = (TextView) findViewById(R.id.publish_content);
        content = (TextView) findViewById(R.id.content);
        title_name.setText("显示通知");
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            ActivityContror.removeActivity(this);
        } else {
            if (flag.equals("0")) {
                JSONObject json = new JSONObject();
                try {
                    json.put("datetime", temptime);
                    json.put("uid", Config.loadUser(this).getUsername());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                gn.getDepartment(Config.URL, Config.NOTICECONFIRM, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        ShowToast.show(ShowReceivePublishAty.this, "回执成功");
                        getDatasFromNet();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ShowToast.show(ShowReceivePublishAty.this, "回执失败");
                        finish();
                    }
                }, json.toString());
            } else {
                deletepublish();
            }

        }

    }

    private void deletepublish() {
        JSONObject json = new JSONObject();
        try {
            json.put("datetime", temptime);
            json.put("uid", Config.loadUser(this).getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.getDepartment(Config.URL, Config.DELETENOTICE, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                ShowToast.show(ShowReceivePublishAty.this, "删除成功");
                getDatasFromNet();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowReceivePublishAty.this, "删除失败");
            }
        }, json.toString());
    }

    private void getDatasFromNet() {
        gn.getDepartment(Config.URL, Config.RECMSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s != null) {
                    db = new DBOption(ShowReceivePublishAty.this);
                    db.updateCatch(Config.URL + Config.RECMSG+Config.loadUser(ShowReceivePublishAty.this).getUsername(), s, System.currentTimeMillis() + "");
                    Intent intent = new Intent(ShowReceivePublishAty.this, PublishDatasAty.class);
                    intent.setAction("refresh");
                    startActivity(intent);
                    ActivityContror.removeActivity(ShowReceivePublishAty.this);
                    //Config.saveRecmsg(getActivity(), s);
                } else {
                    ShowToast.show(ShowReceivePublishAty.this, "没有通知");
                }
                Log.d("hehe", s);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowReceivePublishAty.this, "获取数据失败");
            }
        }, Config.loadUser(ShowReceivePublishAty.this).getUsername());
    }
}
