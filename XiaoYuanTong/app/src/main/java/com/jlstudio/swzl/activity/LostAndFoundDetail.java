package com.jlstudio.swzl.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.swzl.adapter.LostAndFoundDetailAdapter;
import com.jlstudio.swzl.bean.commons;
import com.jlstudio.swzl.bean.lostfound;
import com.jlstudio.swzl.httpNet.util;

import java.util.ArrayList;

public class LostAndFoundDetail extends BaseActivity implements View.OnClickListener{
    private TextView firstcontent;
    private TextView firstnickname;
    private TextView firsttime;
    private ListView common_lv;
    private TextView title_name;
    private Button button_sent;//发送评论按钮
    private EditText editText_common;//评论
    private TextView back;
    private ArrayList<commons> c = new ArrayList<commons>();
    private lostfound lf;
    private commons common = new commons();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_detail);
        initView();
    }

    private void initView() {
        firstcontent = (TextView) findViewById(R.id.lost_found_first_detail_content);
        firstnickname = (TextView) findViewById(R.id.lost_found_first_detail_nickname);
        firsttime = (TextView) findViewById(R.id.lost_found_first_detail_time);
        common_lv = (ListView) findViewById(R.id.lost_found_detail_list);
        editText_common = (EditText) findViewById(R.id.lost_found_detail_edit);
        button_sent = (Button) findViewById(R.id.lost_found_detail_button);

        back = (TextView) findViewById(R.id.back);
        Intent intent = this.getIntent();
        lf=(lostfound)intent.getSerializableExtra("lostandfound");
        //设置本页一楼的信息
        firstcontent.setText(lf.getDescribe());
        firstnickname.setText(lf.getNickname());
        firsttime.setText(lf.getTime());
        //设置本页标题
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText(lf.getTitle());
        //设置本页返回按钮
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        //设置监听事件
        back.setOnClickListener(this);
        button_sent.setOnClickListener(this);
        //初始化数据
        c = getData();
        //声明适配器对象
        LostAndFoundDetailAdapter lafda = new LostAndFoundDetailAdapter(c,this);
        //设置适配器
        common_lv.setAdapter(lafda);
    }


    private ArrayList<commons> getData() {
        //根据id写上网络连接获取评论集合
        ArrayList<commons> co = new ArrayList<commons>();
        co.add(new commons("爱死了1","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了2","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了3","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了4","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了5","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了6","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了7","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了8","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了9","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了10","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了11","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        co.add(new commons("爱死了12","我丢了一个手表在一食堂希望见到跟我说一声，手机号是2628926","2015-12-12 18:00:00"));
        return co;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, LostAndFound.class));
                break;
            case R.id.lost_found_detail_button:
                if (!editText_common.getText().toString().equals(""))
                {
                    String s = editText_common.getText().toString();
                    common.setContent(s);
                    common.setNickname("当前用户的昵称");
                    common.setTime(util.getcurTime());
                    util.sendCommon(this, util.URL, common);
                }
                else{
                    Toast.makeText(this,"评论不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
