package com.jlstudio.iknow.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.adapter.QuestionAdapter;
import com.jlstudio.iknow.bean.Question;
import com.jlstudio.iknow.net.GetDataNet;
import com.jlstudio.iknow.util.JsonUtil;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionAty extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView title_name,back,texticon;
    private LinearLayout search;
    private ListView showQuestion;
    private List<Question> questions;
    private QuestionAdapter adapter;
    private GetDataNet gn;
    private LinearLayout layout;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);
        initLayout();
        gn = GetDataNet.getInstence(this);
        initData();
        initView();
    }

    private void initLayout() {
        layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_show_question,null);
        animation = new TranslateAnimation(0,100,0,-100);
        animation.setDuration(200);
    }

    private void initData() {
        questions = new ArrayList<>();
        adapter = new QuestionAdapter(this,questions);
        String data = Config.loadQuestion(this);
        if(StringUtil.isEmpty(data)){
            getDataFromNet();
        }else{
            questions = JsonUtil.getQuestions(data);
            adapter.setList(questions);
        }
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        search = (LinearLayout) findViewById(R.id.search);
        showQuestion = (ListView) findViewById(R.id.show_question);
        texticon = (TextView) findViewById(R.id.text_icon);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        texticon.setTypeface(iconfont);
        title_name.setText("问答专区");
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        showQuestion.setAdapter(adapter);
        showQuestion.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.search:
                layout.setAnimation(animation);
                animation.startNow();
                layout.startAnimation(animation);
                break;

        }
    }
    private void getDataFromNet(){
        gn.sendAnswer(Config.URL, Config.GETQUESTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Config.saveQuestion(ShowQuestionAty.this, s);
                questions = JsonUtil.getQuestions(s);
                adapter.setList(questions);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowQuestionAty.this, "获取数据失败");
            }
        }, "department");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ShowQuestionDetailAty.class);
        intent.putExtra("question",questions.get(position));
        startActivity(intent);
    }
}
