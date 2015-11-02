package com.jlstudio.publish.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.adapter.ShowPersonAdapter;
import com.jlstudio.publish.bean.Person;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ShowPersonAty extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button sure;
    private ListView showperson;
    private List<Person> persons;
    private ShowPersonAdapter adapter;
    private TextView back,title_name;
    private String titlename;
    private GetNotificationNet gn;
    private DBOption db;
    private CatchData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_person);
        gn = GetNotificationNet.getInstence(this);
        initDatas();
        inintView();
    }

    private void inintView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        sure = (Button) findViewById(R.id.publish);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
        title_name.setText(titlename);

        showperson = (ListView) findViewById(R.id.showperson);
        showperson.setAdapter(adapter);
        showperson.setOnItemClickListener(this);
    }

    private void initDatas() {
        titlename = getIntent().getStringExtra("titlename");
        persons = new ArrayList<>();
        adapter = new ShowPersonAdapter(this,persons);
        db = new DBOption(this);
        data = db.getCatch(Config.URL+Config.GETPERSON+titlename);
        if(data == null){
            getDatasFromNet();
        }else{
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if((time - catchtime) > Config.CATCHTIME){
                getDatasFromNet();
            }else{
                persons = JsonToPubhlishData.getPersons(data.getContent());
                adapter.setList(persons);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.publish:
                analysis();
                break;
        }
    }
    private void getDatasFromNet(){
        gn.getDepartment(Config.URL,Config.GETPERSON,new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                if(s!=null){
                    if(data == null){
                        db.insertCatch(Config.URL+Config.GETPERSON,s,System.currentTimeMillis()+"");
                    }else{
                        db.updateCatch(Config.URL+Config.GETPERSON,s,System.currentTimeMillis()+"");
                    }
                    persons = JsonToPubhlishData.getPersons(s);
                    adapter.setList(persons);
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(ShowPersonAty.this, "获取数据失败");
            }
        },titlename);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(persons.get(position).isSelect()){
            persons.get(position).setIsSelect(false);
            view.setBackgroundResource(R.drawable.school_message_item_selector);
        }else{
            persons.get(position).setIsSelect(true);
            view.setBackgroundResource(R.drawable.select_person_item_pressed);
        }
    }
    private void analysis(){
        String string = null;
        for(Person p : persons){
            if(p.isSelect()){
                if(string ==null){
                    string = p.getUsername()+",";
                }else{
                    string += p.getUsername()+",";
                }
            }
        }
//        if(!StringUtil.isEmpty(string)){
//            string = string.substring(0,string.length()-1);
//        }
        Intent intent = new Intent();
        intent.putExtra("datas",string);
        setResult(1, intent);
        ActivityContror.removeActivity(this);
    }
}
