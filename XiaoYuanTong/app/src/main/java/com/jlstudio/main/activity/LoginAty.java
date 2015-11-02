package com.jlstudio.main.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.dialog.LoginQuery;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.User;
import com.jlstudio.main.net.GetDataNet;
import com.jlstudio.main.util.JsonToBean;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginAty extends BaseActivity implements View.OnClickListener {
    private EditText username,password;
    private Button login;
    private GetDataNet gn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gn = GetDataNet.getInstence(this);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Config.saveDisplay(this, dis.getWidth());
        initView();
//        User user = Config.loadBaseUser(this);
//        if(!StringUtil.isEmpty(user.getUsername(),getIntent().getAction())){
//            Config.saveUser(this,Config.loadBaseUser(this));
//            startActivity(new Intent(this,MainActivity.class));
//            ActivityContror.removeActivity(this);
//        }else{
//            if(!Config.isNetworkAvailable(this)){
//                ShowToast.show(this, "网络好像发生了错误");
//            }
//        }
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String user = username.getText().toString();
        String pwd = password.getText().toString();
        if(StringUtil.isEmpty(user,pwd)) {
            ShowToast.show(this, "用户名或密码不能为空");
            return;
        }
        gn.getUserData(Config.URL, Config.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    Log.d("hehe", s);
                    if (json.getString("status").equals("succeed")) {

                        User user = JsonToBean.getUser(json);
                        new LoginQuery(LoginAty.this,user).show();
                        //Config.saveUser(LoginAty.this, user);
//                        startActivity(new Intent(LoginAty.this, MainActivity.class));
//                        ActivityContror.removeActivity(LoginAty.this);
                    } else {
                        ShowToast.show(LoginAty.this, "用户名密码错误");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, user, pwd);
    }

}
