package com.jlstudio.publish.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.activity.SelectPersonAty;
import com.jlstudio.publish.adapter.SelectDepartmentadapter;
import com.jlstudio.publish.bean.PublishData;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gzw on 2015/9/26.
 */
public class SelectPersonDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context context;
    private Button btn_submit;
    private List<String> departments;
    private SelectDepartmentadapter adapter;
    private ListView listView;
    private GetNotificationNet gn;
    private DBOption db;
    private CatchData data;

    public SelectPersonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectPersonDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_person);
        gn = GetNotificationNet.getInstence(context);
        initView();
        getDatas();
        initListView();
    }

    private void getDatas(){
        departments = new ArrayList<>();
        adapter = new SelectDepartmentadapter(context,departments);
       // String data = Config.loadDepartement(context);
        db = new DBOption(context);
        data = db.getCatch(Config.URL+Config.GETDEPARTMENT);
        if(data == null){
            getDatasFromNet();
        }else{
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if((time - catchtime) > Config.CATCHTIME){
                getDatasFromNet();
            }else{
                departments = JsonToPubhlishData.getDepartment(data.getContent());
                adapter.setList(departments);
            }

        }
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listview);
        //departments = new ArrayList<>();
       // departments.add("信息工程系");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    private void initView() {
        //this.getWindow().setWindowAnimations(R.style.dialoganim);
       // LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.cet_dialog, null);
        Window dialogwindow = getWindow();
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        //btn_submit = (Button) findViewById(R.id.btn_submit);
        //btn_submit.setOnClickListener(this);
        lp.width = (int) (dm.widthPixels * 0.8);
        lp.height = (int) (dm.heightPixels * 0.6);
        dialogwindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        String string = Config.loadType(context);
        if(string!=null){
            Intent intent = new Intent(context, SelectPersonAty.class);
            intent.putExtra("datas",string);
            intent.putExtra("department",departments.get(position));
            context.startActivity(intent);
            dismiss();
        }else{
            gn.getDepartment(Config.URL,Config.GETTYPE, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    if (s != null) {
                        Config.saveType(context, s);
                        Intent intent = new Intent(context, SelectPersonAty.class);
                        intent.putExtra("datas",s);
                        intent.putExtra("department",departments.get(position));
                        context.startActivity(intent);
                        dismiss();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ShowToast.show(context, "获取数据失败");
                    dismiss();
                }
            }, departments.get(position));
        }

    }

    private void getDatasFromNet(){
        gn.getDepartment(Config.URL,Config.GETDEPARTMENT,new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                if(s!=null){
                    departments = JsonToPubhlishData.getDepartment(s);
                    adapter.setList(departments);
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(context,"获取数据失败");
            }
        },"department");
    }
}
