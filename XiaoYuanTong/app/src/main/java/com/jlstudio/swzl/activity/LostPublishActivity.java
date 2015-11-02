package com.jlstudio.swzl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.swzl.bean.lostfound;
import com.jlstudio.swzl.httpNet.util;

import static com.jlstudio.swzl.httpNet.util.LostAndFound_publish;

public class LostPublishActivity extends BaseActivity implements View.OnClickListener{
    private Spinner spinner_academy;
    private Spinner spinner_type;
    private RelativeLayout rela_xuehao;
    private RelativeLayout rela_name;
    private RelativeLayout rela_phone;
    private RelativeLayout line_diy;
    private RelativeLayout line_diy_content;
    private ImageView imageView;
    private CheckBox checkBoxfound;
    private CheckBox checkBoxlost;
    private Button submit;//提交按钮
    private Button cancle;//取消按钮
    private EditText editText_xuehao;//填写的一卡通通号
    private EditText editText_name;//填写的姓名
    private EditText editText_phone;//填写的手机号
    private EditText editText_content;//填写的自定义具体内容
    private EditText editText_title;//填写的标题

    private lostfound lf  = new lostfound();//对提交该失物招领信息的定义
    private String content  = "未填写";//发布的详细信息
    private String title = "未填写";//发布的标题
    private String academy;//系别
    private View phone;//联系电话
    private String name;//真实姓名
    private String number;//一卡通号
    private int flag = 0;//0代表选择一卡通，1代表其他
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_publish);
        init();
        event();

        //系别选择的事件监听
        spinner_academy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //获取点击的系别
                academy = parent.getItemAtPosition(position).toString();
                Toast.makeText(LostPublishActivity.this, "你点击的是:" + academy, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //类型的项的监听事件
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    //选择了一卡通
                    title = "一卡通";
                    rela_xuehao.setVisibility(View.VISIBLE);
                    rela_name.setVisibility(View.VISIBLE);
                    rela_phone.setVisibility(View.VISIBLE);
                    line_diy.setVisibility(View.GONE);
                    imageView.setVisibility(View.INVISIBLE);
                    line_diy_content.setVisibility(View.GONE);
                    flag = 0;
                } else {
                    //选择了其他
                    title = "";
                    content = "";
                    rela_xuehao.setVisibility(View.GONE);
                    rela_name.setVisibility(View.GONE);
                    rela_phone.setVisibility(View.GONE);
                    line_diy.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    line_diy_content.setVisibility(View.VISIBLE);
                    flag = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void event() {
        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    private void init() {
        spinner_academy = (Spinner) findViewById(R.id.lost_found_publish_academyspinner);
        spinner_type = (Spinner) findViewById(R.id.lost_found_publish_typespinner);
        rela_phone = (RelativeLayout) findViewById(R.id.lost_found_phone);
        rela_name = (RelativeLayout) findViewById(R.id.lost_found_name);
        rela_xuehao = (RelativeLayout) findViewById(R.id.lost_found_xuehao);
        line_diy = (RelativeLayout) findViewById(R.id.lost_found_publish_diy_title);
        imageView = (ImageView) findViewById(R.id.lost_found_publish_pic);
        line_diy_content = (RelativeLayout) findViewById(R.id.lost_found_publish_diy_content);
        checkBoxfound = (CheckBox) findViewById(R.id.lost_found_publish_checkbox_found);
        checkBoxlost = (CheckBox) findViewById(R.id.lost_found_publish_checkbox_lost);
        editText_xuehao = (EditText) findViewById(R.id.lost_found_publish_xuehao);
        editText_name = (EditText) findViewById(R.id.lost_found_publish_name);
        editText_phone = (EditText) findViewById(R.id.lost_found_publish_phone);
        editText_content = (EditText) findViewById(R.id.lost_found_publish_content);
        editText_title = (EditText) findViewById(R.id.lost_found_publish_title);


        submit = (Button) findViewById(R.id.lost_found_publish_submit);
        cancle = (Button) findViewById(R.id.lost_found_publish_cancel);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lost_found_publish_submit:
                    //初始化失物招领对象内容
                    if(!judge()){
                        lf = creatLostfound();
                        LostAndFound_publish(this, util.URL, lf);
                    }
                    break;
                case R.id.lost_found_publish_cancel:
                    startActivity(new Intent(this, LostAndFound.class));
                    break;
                case R.id.lost_found_publish_pic:
                    break;
            }
    }

    private boolean judge() {
        boolean a = false;
        if (checkBoxlost.isChecked() == checkBoxfound.isChecked()) {
            Toast.makeText(this, "必须选择失物或招领其中一个！", Toast.LENGTH_SHORT).show();
            a = true;
        }
        if(flag == 1){
            if (editText_title.getText().toString().equals("") || editText_content.getText().toString().equals("")) {
                Toast.makeText(this, "必须填写标题和内容！", Toast.LENGTH_SHORT).show();
                a = true;
            }
        }
        return a;
    }

    //初始化失物招领对象
    private lostfound creatLostfound() {
        lostfound l = new lostfound();
        if(flag == 0) {
            content = "";
            content += editText_xuehao.getText().toString();
            content += editText_name.getText().toString();
            content += editText_phone.getText().toString();
            Log.d("AAA",content);
        }
        else{
            content  = editText_content.getText().toString();
            title = editText_title.getText().toString();
        }
        l.setTitle(title);
        l.setDescribe(content);
        l.setNickname("当前用户的昵称");
        l.setTime(util.getcurTime());
        l.setIntegral(0);
        l.setCommon_count(0);
        //0代表失物，1代表招领
        l.setFlag(0);

        return l;
    }
}
