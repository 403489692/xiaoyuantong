package com.jlstudio.publish.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.bean.PublishData;
import com.jlstudio.publish.bean.Type;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.server.MyIntentService;
import com.jlstudio.publish.server.MyService;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectPersonAty extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private String[] types;
    private String[] classes;
    private Button submit;
    private TextView back, title_name;
    private List<Type> typeList;
    private List<Type> classesList;
    private LinearLayout typeLayout, classesLayout;
    private LinearLayout currentLayout;
    private Random random;
    private String datas;
    private boolean isSend = false;
    private GetNotificationNet gn;
    private String titlename;
    private String selectType;
    private String selectClasses;
    private String selectPerson;
    private boolean group, item;//判断是否选人
    private int[] bkcolor = new int[]{R.drawable.main_func_item_shape_unpress1,
            R.drawable.main_func_item_shape_unpress2, R.drawable.main_func_item_shape_unpress3,
            R.drawable.main_func_item_shape_unpress4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
        gn = new GetNotificationNet(this);
        random = new Random();
        initData();
        initView();
        setOnclickListener();

    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        title_name.setText(titlename);
        typeList = new ArrayList<>();
        classesList = new ArrayList<>();
        typeLayout = (LinearLayout) findViewById(R.id.type);
        classesLayout = (LinearLayout) findViewById(R.id.classes);
        int count = 0;
        for (int i = 0; i < types.length; i++) {
            if (i % 6 == 0) {
                count = 0;
                currentLayout = createLinearLayout();
                typeLayout.addView(currentLayout);
            }
            Button bt = createButton();
            bt.setText("团支书");
            typeList.add(new Type(bt, bt.getBackground()));
            currentLayout.addView(bt);
            count++;
        }
        for (int i = count; i < 6; i++) {
            Button bt = createButton();
            bt.setVisibility(View.INVISIBLE);
            currentLayout.addView(bt);
        }
        for (int i = 0; i < classes.length; i++) {
            if (i % 6 == 0) {
                count = 0;
                currentLayout = createLinearLayout();
                classesLayout.addView(currentLayout);
            }
            Button bt = createButton();
            bt.setText(classes[i]);
            // typeList.add(new Type(bt,bt.getBackground()));
            classesList.add(new Type(bt, bt.getBackground()));
            currentLayout.addView(bt);
            count++;
        }
        for (int i = count; i < 6; i++) {
            Button bt = createButton();
            bt.setVisibility(View.INVISIBLE);
            currentLayout.addView(bt);
        }

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    private void initData() {
        datas = getIntent().getStringExtra("datas");
        titlename = getIntent().getStringExtra("department");
        String[] string = datas.split("\\.");
        types = string[0].split(",");
        classes = string[1].split(",");
//        types = new int[10];
//        classes = new int[13];
    }

    private LinearLayout createLinearLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        //layout.setPadding(0,10,0,5);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 5);
        layout.setLayoutParams(params);
        return layout;
    }

    private Button createButton() {
        int i = random.nextInt(4);
        Button bt = new Button(this);
        bt.setTextSize(10);
        bt.setBackgroundResource(bkcolor[i]);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(0, 0, 5, 0);
        bt.setLayoutParams(params);
        return bt;
    }

    private void setOnclickListener() {
        for (Type bt : typeList) {
            bt.getBt().setOnClickListener(this);
        }
        for (Type bt : classesList) {
            bt.getBt().setOnClickListener(this);
        }
        for (Type bt : classesList) {
            bt.getBt().setOnLongClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            if(isSend == false){
                isSend = true;
                anilysis();
            }
        } else if (v.getId() == R.id.back) {
            startActivity(new Intent(this, AddPublishAty.class));
            ActivityContror.removeActivity(this);
        } else {
            for (Type bt : typeList) {
                if (v == bt.getBt()) {
                    if (bt.isSelect()) {
                        bt.setIsSelect(false);
                        bt.getBt().setBackground(bt.getDrawable());
                    } else {
                        bt.setIsSelect(true);
                        v.setBackgroundResource(R.drawable.select_person_item_pressed);
                    }
                }
            }
            for (Type bt : classesList) {
                if (v == bt.getBt()) {
                    if (bt.isSelect()) {
                        bt.setIsSelect(false);
                        bt.getBt().setBackground(bt.getDrawable());
                    } else {
                        bt.setIsSelect(true);
                        v.setBackgroundResource(R.drawable.select_person_item_pressed);
                    }
                }
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Button bt = (Button) v;
        Intent intent = new Intent(this, ShowPersonAty.class);
        intent.putExtra("titlename", bt.getText());
        startActivityForResult(intent, 0);
        Toast.makeText(this, "进入名单", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String string = data.getStringExtra("datas");
        if (!StringUtil.isEmpty(string)) {
            if (selectPerson == null) {
                selectPerson = string;
                item = true;
            } else {
                selectPerson += string;
            }

        }
    }

    private void anilysis() {
        for (Type p : typeList) {
            if (p.isSelect()) {
                group = true;
                if (selectType == null) {
                    selectType = p.getBt().getText().toString() + ",";
                } else {
                    selectType += p.getBt().getText().toString() + ",";
                }
            }
        }
        for (Type p : classesList) {
            if (p.isSelect()) {
                group = true;
                if (selectClasses == null) {
                    selectClasses = p.getBt().getText().toString() + ",";
                } else {
                    selectClasses += p.getBt().getText().toString() + ",";
                }
            }
        }
        //去除字符串最后一个逗号
        if (selectType != null) {
            selectType = selectType.substring(0, selectType.length() - 1);
        }
        if (selectClasses != null) {
            selectClasses = selectClasses.substring(0, selectClasses.length() - 1);
        }
        if (selectPerson != null) {
            selectPerson = selectPerson.substring(0, selectPerson.length() - 1);
        }
        JSONObject json = new JSONObject();
        try {
            json.put("type", selectType);
            json.put("classes", selectClasses);
            json.put("person", selectPerson);
            json.put("uid", Config.loadUser(this).getUsername());
            String string = Config.loadPublish(this);
            json.put("publish", string);
            String department = Config.loadDepartment(this);
            json.put("departmentname", department);
            String type = Config.loadPublishType(this);
            if(type.equals("云推送")){
                sendDatasJpush(json);
            }else{
                sendDatasSMS(json,string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendDatasJpush(final JSONObject data) {
        gn.getDepartment(Config.URL, Config.SENDPUBLISH, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                if (s != null) {
                    if (s.equals("1")) {
                        ShowToast.show(SelectPersonAty.this, "发送成功");
                        startActivity(new Intent(SelectPersonAty.this, PublishDatasAty.class));
                        selectPerson = null;
                        getDatasFromNet();
                        ActivityContror.removeActivity(SelectPersonAty.this);
                    }
                } else {
                    ShowToast.show(SelectPersonAty.this, "发送失败");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(SelectPersonAty.this, "发送失败");
            }
        }, data.toString());
    }
    private void sendDatasSMS(final JSONObject data,final String msg) {
        gn.getDepartment(Config.URL, Config.GETPHONE, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                if (s != null) {
                    Intent server = new Intent(SelectPersonAty.this, MyIntentService.class);
                    server.putExtra("phone",s);
                    server.putExtra("message",msg);
                    startService(server);
                    startActivity(new Intent(SelectPersonAty.this,PublishDatasAty.class));
                    finish();
                } else {
                    ShowToast.show(SelectPersonAty.this, "获取联系人失败");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(SelectPersonAty.this, "获取联系人失败");
            }
        }, data.toString());
    }
    private void getDatasFromNet() {
        gn.getDepartment(Config.URL, Config.SENDMSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                DBOption db = new DBOption(SelectPersonAty.this);
                if (s != null) {
                    db.updateCatch(Config.URL + Config.SENDMSG + Config.loadUser(SelectPersonAty.this).getUsername(), s, System.currentTimeMillis() + "");
                    Intent intent = new Intent(SelectPersonAty.this, PublishDatasAty.class);
                    intent.setAction("refresh");
                    startActivity(intent);
                    ActivityContror.removeActivity(SelectPersonAty.this);
                } else {
                }
                Log.d("hehe", s);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(SelectPersonAty.this, "获取数据失败");
                Log.d("hehe", "error");
            }
        }, Config.loadUser(SelectPersonAty.this).getUsername());

    }
}
