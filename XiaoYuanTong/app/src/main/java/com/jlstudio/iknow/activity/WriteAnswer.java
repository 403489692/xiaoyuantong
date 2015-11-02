package com.jlstudio.iknow.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.net.GetDataNet;
import com.jlstudio.iknow.util.JsonUtil;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteAnswer extends BaseActivity implements View.OnClickListener {
    private TextView back,title_name;
    private Button submit;
    private EditText write;
    private String id;
    private GetDataNet gn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_answer);
        gn = new GetDataNet(this);
        initData();
        initView();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        submit = (Button) findViewById(R.id.submit);
        write = (EditText) findViewById(R.id.write);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        title_name.setText("回答");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.submit:
                sendAnswer();
                break;
        }
    }

    private void sendAnswer() {
        String content = write.getText().toString();

        if(StringUtil.isEmpty(content)){
            ShowToast.show(this,"请输入内容");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("answer",content);
            json.put("qid",id);
            json.put("uid","123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.sendAnswer(Config.URL, Config.SENDANSWER, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                ShowToast.show(WriteAnswer.this,"提交成功");
                ActivityContror.removeActivity(WriteAnswer.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(WriteAnswer.this,"提交失败");
            }
        },json.toString());
    }
}
