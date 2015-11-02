package com.jlstudio.publish.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.activity.SelectPersonAty;
import com.jlstudio.publish.adapter.PublishDatasAdapter;
import com.jlstudio.publish.adapter.SelectDepartmentadapter;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gzw on 2015/9/26.
 */
public class SelectDepartmentDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context context;
    private Button btn_submit;
    private List<String> departments;
    private SelectDepartmentadapter adapter;
    private ListView listView;
    private GetNotificationNet gn;
    private DBOption db;
    private CatchData data;

    public SelectDepartmentDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectDepartmentDialog(Context context) {
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
        Config.saveDepartment(context,departments.get(position));
        db = new DBOption(context);
        data = db.getCatch(Config.URL+Config.GETTYPE);
        if(data == null){
            getTypeFromNet(position);
        }else{
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if((time - catchtime) > (1000*60*60*2)){
                getTypeFromNet(position);
            }else{
                Intent intent = new Intent(context, SelectPersonAty.class);
                intent.putExtra("datas",data.getContent());
                intent.putExtra("department",departments.get(position));
                context.startActivity(intent);
                dismiss();
                ActivityContror.removeActivity((Activity)context);
            }

        }

    }

    private void getDatasFromNet(){
        gn.getDepartment(Config.URL,Config.GETDEPARTMENT,new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                if(s!=null){
                    if(data == null){
                        db.insertCatch(Config.URL+Config.GETDEPARTMENT,s,System.currentTimeMillis()+"");
                    }else{
                        db.updateCatch(Config.URL+Config.GETDEPARTMENT,s,System.currentTimeMillis()+"");
                    }
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
    private void getTypeFromNet(final int position){
        gn.getDepartment(Config.URL,Config.GETTYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s != null) {
                    if(data == null){
                        db.insertCatch(Config.URL+Config.GETTYPE,s,System.currentTimeMillis()+"");
                    }else{
                        db.updateCatch(Config.URL+Config.GETTYPE,s,System.currentTimeMillis()+"");
                    }
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
