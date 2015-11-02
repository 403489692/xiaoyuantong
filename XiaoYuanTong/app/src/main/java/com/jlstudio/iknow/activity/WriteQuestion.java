package com.jlstudio.iknow.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteQuestion extends BaseActivity implements View.OnClickListener {
    private TextView back,title_name;
    private EditText title,content,score;
    private Button publish;
    private GetDataNet gn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_question);
        gn = new GetDataNet(this);
        initView();
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        score = (EditText) findViewById(R.id.score);
        publish = (Button) findViewById(R.id.publish);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        title_name.setText("提问");
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.publish:
                publish();
                break;
        }
    }

    private void publish() {
        String tt = title.getText().toString();
        String con = content.getText().toString();
        String sco = score.getText().toString();
        if(StringUtil.isEmpty(tt)){
            ShowToast.show(this,"请输入标题");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("title",tt);
            json.put("content",con);
            json.put("uid","13223227");
            json.put("score",score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.sendAnswer(Config.URL, Config.SENDQUESTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                ShowToast.show(WriteQuestion.this,"提交成功");
                ActivityContror.removeActivity(WriteQuestion.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(WriteQuestion.this,"提交失败");
            }
        },json.toString());
    }
}
