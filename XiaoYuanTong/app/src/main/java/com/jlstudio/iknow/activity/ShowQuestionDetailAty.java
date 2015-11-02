package com.jlstudio.iknow.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.adapter.AnswerAdapter;
import com.jlstudio.iknow.adapter.QuestionAdapter;
import com.jlstudio.iknow.bean.Answer;
import com.jlstudio.iknow.bean.Question;
import com.jlstudio.iknow.net.GetDataNet;
import com.jlstudio.iknow.util.JsonUtil;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionDetailAty extends BaseActivity implements View.OnClickListener {
    private TextView back,title_name,user,score,title,content;
    private Question question;
    private List<Answer> answers;
    private AnswerAdapter adapter;
    private GetDataNet gn;
    private ListView showanswer;
    private Button pushanswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question_detail);
        gn = GetDataNet.getInstence(this);
        initData();
        initView();
    }

    private void initData() {
        question = (Question) getIntent().getSerializableExtra("question");
        answers = new ArrayList<>();
        adapter = new AnswerAdapter(this,answers);
        String data = Config.loadAnswer(this);
        if(StringUtil.isEmpty(data)){
            getDataFromNet();
        }else{
            answers = JsonUtil.getAnswers(data);
            adapter.setList(answers);
        }
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        pushanswer = (Button) findViewById(R.id.pushanswer);
        showanswer = (ListView) findViewById(R.id.answer);
        user = (TextView) findViewById(R.id.user);
        score = (TextView) findViewById(R.id.score);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        showanswer.setAdapter(adapter);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        pushanswer.setOnClickListener(this);
        title_name.setText(question.getCount() + "个回答");
        user.setText(question.getUser());
        score.setText(question.getScore());
        title.setText(question.getTitile());
        content.setText(question.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.pushanswer:
                Intent intent = new Intent(this,WriteAnswer.class);
                intent.putExtra("id",question.getId());
                startActivity(intent);
                break;
        }
    }
    private void getDataFromNet(){
        gn.sendAnswer(Config.URL, Config.GETANSWER, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Config.saveAnswer(ShowQuestionDetailAty.this, s);
                answers = JsonUtil.getAnswers(s);
                adapter.setList(answers);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowQuestionDetailAty.this, "获取数据失败");
            }
        }, question.getId());
    }
}
