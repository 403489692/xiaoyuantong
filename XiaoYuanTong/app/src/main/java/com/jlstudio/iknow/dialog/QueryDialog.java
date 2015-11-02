package com.jlstudio.iknow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.iknow.activity.CETScoreAty;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.activity.SelectPersonAty;
import com.jlstudio.publish.adapter.SelectDepartmentadapter;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gzw on 2015/9/26.
 */
public class QueryDialog extends Dialog {
    private Context context;
    private EditText username, password;
    private TextView title_name,uid,pwd;
    private Button submit;
    private GetNotificationNet gn;

    public QueryDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_querycet);
        gn = new GetNotificationNet(context);
        initView();
    }
    private void initView() {
        Window dialogwindow = getWindow();
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = Config.loadDisplay(context);
       // if(width <= 480){
            lp.width = (int) (dm.widthPixels * 0.8);
            lp.height = (int) (dm.heightPixels * 0.45);
//        }else{
//            lp.width = 600;
//            lp.height = 500;
//        }
        dialogwindow.setAttributes(lp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        title_name = (TextView) findViewById(R.id.title_name);
        uid = (TextView) findViewById(R.id.uid);
        pwd = (TextView) findViewById(R.id.pwd);
        uid.setText("准考证号:");
        pwd.setText("姓名:        ");
        title_name.setText("查询");
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                if (StringUtil.isEmpty(user, pwd)) {
                    ShowToast.show(context, "用户名或密码不能为空");
                } else {
                    getDatasFromNet(user, pwd);
                }
            }
        });
    }


    private void getDatasFromNet(final String user, String pwd) {
        JSONObject json = new JSONObject();
        String servlet = null;
        try {
            json.put("id", user);
            json.put("name", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gn.getDepartment(Config.URL, Config.CETSCORE, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String status = json.getString("status");
                    if (status.equals("success")) {
                        Intent intent = new Intent(context, CETScoreAty.class);
                        intent.putExtra("data", s);
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
            }
        }, json.toString());
    }
}
