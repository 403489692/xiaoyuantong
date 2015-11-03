package com.jlstudio.iknow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.activity.CETScoreAty;
import com.jlstudio.iknow.activity.ScheduleAty;
import com.jlstudio.iknow.activity.ScoreAty;
import com.jlstudio.iknow.adapter.ScoreAdapter;
import com.jlstudio.iknow.util.JsonUtil;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.application.MyApplication;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.main.util.ProgressUtil;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by gzw on 2015/9/26.
 */
public class QueryScoreDialog extends Dialog implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private EditText username, password;//输入的用户名，密码
    private TextView title_name;//titlbar的标题
    private Button submit;//提交按钮
    private CheckBox chack;//记住密码单选框
    private GetNotificationNet gn;//网络连接类
    private DBOption db;//数据库操作
    private int type;//查课表和查分数共用此对话框，0表示查成绩，1表示查课表
    String servlet = null;//查成绩或查课表入口
    private boolean saveuser = false;//是否保存用户名
    private CatchData data;//缓存数据

    public QueryScoreDialog(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
        if (type == 0) servlet = Config.GETSCORE;
        else servlet = Config.SCHEDULE;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_query);
        gn = new GetNotificationNet(context);
        db = new DBOption(context);
        initView();
        initUser();
    }

    private void initUser() {
        String user = Config.loadQueryuser(context);
        if (!StringUtil.isEmpty(user)) {
            String[] up = user.split(",");
            username.setText(up[0]);
            password.setText(up[1]);
            chack.setChecked(true);
        }
    }

    private void initData(String user, String pwd) {
        data = db.getCatch(Config.URL + servlet + user);
        if (data == null) {
            getDatasFromNet(user, pwd);
        } else {
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if ((time - catchtime) > (1000 * 60 * 24 * 30 * 6)) {
                getDatasFromNet(user, pwd);
            } else {
                Intent intent = null;
                if (type == 0) {
                    intent = new Intent(context, ScoreAty.class);
                } else {
                    intent = new Intent(context, ScheduleAty.class);
                }
                intent.putExtra("data", data.getContent());
                context.startActivity(intent);
                if (saveuser == true) {
                    Config.saveQueryuser(context, user + "," + pwd);
                } else {
                    Config.saveQueryuser(context, "");
                }
                dismiss();
            }
        }

    }

    private void initView() {
        Window dialogwindow = getWindow();
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = Config.loadDisplay(context);
        //if(width <= 480){
        lp.width = (int) (dm.widthPixels * 0.8);
        lp.height = (int) (dm.heightPixels * 0.5);
//        }else{
//            lp.width = 600;
//            lp.height = 500;
//        }
        dialogwindow.setAttributes(lp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        title_name = (TextView) findViewById(R.id.title_name);
        chack = (CheckBox) findViewById(R.id.chack);
        chack.setOnCheckedChangeListener(this);
        title_name.setText("查询");
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                if (StringUtil.isEmpty(user, pwd)) {
                    ShowToast.show(context, "用户名或密码不能为空");
                    return;
                }
                initData(user, pwd);
            }
        });
    }


    private void getDatasFromNet(final String user, final String pwd) {
        ProgressUtil.closeProgressDialog();
        JSONObject json = new JSONObject();
//        if(type == 0) servlet = Config.GETSCORE;
//        else servlet = Config.SCHEDULE;
        try {
            json.put("username", user);
            json.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.getDepartment(Config.URL, servlet, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String status = json.getString("status");
                    if (status.equals("success")) {
                        if (data == null) {
                            db.insertCatch(Config.URL + servlet + user, s, System.currentTimeMillis() + "");
                        } else {
                            db.updateCatch(Config.URL + servlet + user, s, System.currentTimeMillis() + "");
                        }
                        if (saveuser == true) {
                            Config.saveQueryuser(context, user + "," + pwd);
                        } else {
                            Config.saveQueryuser(context, "");
                        }
                        Intent intent = null;
                        if (type == 0) {
                            intent = new Intent(context, ScoreAty.class);
                        } else {
                            intent = new Intent(context, ScheduleAty.class);
                        }
                        intent.putExtra("data", s);
                        ProgressUtil.closeProgressDialog();
                        context.startActivity(intent);
                        dismiss();
                    } else {
                        ShowToast.show(context, "用户名或密码错误");
                        username.setText("");
                        password.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(context, "获取数据失败");
                ProgressUtil.closeProgressDialog();
            }
        }, json.toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            saveuser = true;
        } else {
            saveuser = false;
        }
    }
}
