package com.jlstudio.publish.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.dialog.SelectDepartmentDialog;
import com.jlstudio.publish.util.ShowToast;

public class AddPublishAty extends BaseActivity implements View.OnClickListener {
    private static final int TITLE_COUNT = 20;
    private static final int CONTENT_COUNT = 210;
    private static final int CONTENT_COUNT2 = 70;
    private EditText title,content;
    private Button publish;
    private String publishType;
    private TextView back,title_name,title_count,content_count,titlename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_publish);
        publishType = Config.loadPublishType(this);
        initView();
    }

    private void initView() {
        //公共
        publish = (Button) findViewById(R.id.publish);
        content_count = (TextView) findViewById(R.id.content_count);
        back = (TextView) findViewById(R.id.back);
        content = (EditText) findViewById(R.id.publish_content);
        title_name = (TextView) findViewById(R.id.title_name);//titlebar的标题
        title_count = (TextView) findViewById(R.id.title_count);//计算标题还能写几个字
        title = (EditText) findViewById(R.id.publish_title);//输入的消息的标题
        titlename = (TextView) findViewById(R.id.titlname);//显示标题两个字的textview
        publish.setOnClickListener(this);
        back.setOnClickListener(this);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        title_name.setText("添加消息");
        title.addTextChangedListener(new MyTextChangeListener(1));
        content.addTextChangedListener(new MyTextChangeListener(2));
        //区分
        if(publishType.equals("短信")){
            titlename.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            title_count.setVisibility(View.GONE);
            content_count.setText("您还剩下70个汉字可以输入");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(this,PublishDatasAty.class));
                break;
            case R.id.publish:
                String str = title.getText().toString();
                String strcontent = content.getText().toString();
                if(publishType.equals("云推送")){
                    if(str == null ||str.equals("")){
                        ShowToast.show(this,"标题不能为空");
                        return;
                    }else{
                        if (str.length()>=20) str.substring(0,20);
                        str+="$"+content.getText().toString();
                        Log.d("hehe",str);
                    }
                }else{
                    if(strcontent == null ||strcontent.equals("")){
                        ShowToast.show(this,"内容不能为空");
                        return;
                    }else{
                        if (strcontent.length()>=20) strcontent.substring(0,20);
                        str = strcontent;
                        Log.d("hehe",str);
                    }
                }
                Config.savePublish(AddPublishAty.this,str);
                new SelectDepartmentDialog(this).show();
               // ActivityContror.removeActivity(this);
                break;
        }
    }
    private class MyTextChangeListener implements TextWatcher{
        private int e;//1为title，2为content
        public MyTextChangeListener(int e) {
            this.e = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(e == 1){
                int count = TITLE_COUNT - s.length();
                if (count == 0){
                    title_count.setTextColor(Color.RED);
                    title_count.setText("您还剩"+count+"个汉字可以输入");
                }else if(count < 0){
                    title_count.setTextColor(Color.RED);
                    title_count.setText("您输入的字数已经达到上限，超出部分将作废");
                }else{
                    title_count.setTextColor(Color.BLACK);
                    title_count.setText("您还剩"+count+"个汉字可以输入");
                }

            }else{
                int count = 0;
                if (publishType.equals("短信")){
                    count = CONTENT_COUNT2 - s.length();
                }else{
                    count = CONTENT_COUNT - s.length();
                }
                if (count == 0){
                    content_count.setTextColor(Color.RED);
                    content_count.setText("您还剩" + count + "个汉字可以输入");
                }else if(count < 0){
                    content_count.setTextColor(Color.RED);
                    content_count.setText("您输入的字数已经达到上限，超出部分将作废");
                }else{
                    content_count.setTextColor(Color.BLACK);
                    content_count.setText("您还剩"+count+"个汉字可以输入");
                }

            }
        }
    }

}
