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
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.activity.AddPublishAty;
import com.jlstudio.publish.adapter.SelectDepartmentadapter;
import com.jlstudio.publish.net.GetNotificationNet;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gzw on 2015/9/26.
 */
public class SelectPublishType extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context context;
    private List<String> type;
    private SelectDepartmentadapter adapter;
    private ListView listView;

    public SelectPublishType(Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectPublishType(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_person);
        initView();
        getDatas();
        initListView();
    }

    private void getDatas(){
        type = new ArrayList<>();
        type.add("云推送");
        type.add("短信");
        adapter = new SelectDepartmentadapter(context,type);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    private void initView() {
        Window dialogwindow = getWindow();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("选择消息类型");
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = (int) (dm.widthPixels * 0.8);
        lp.height = (int) (dm.heightPixels * 0.6);
        dialogwindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Config.savePublishType(context,type.get(position));
        context.startActivity(new Intent(context, AddPublishAty.class));
        dismiss();
        //ActivityContror.removeActivity((Activity)context);
    }
}
