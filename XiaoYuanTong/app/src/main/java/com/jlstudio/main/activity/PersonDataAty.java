package com.jlstudio.main.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.iknow.dialog.UpdatePwdDialog;
import com.jlstudio.main.application.ActivityContror;

public class PersonDataAty extends BaseActivity implements View.OnClickListener {
    private TextView back;
    private Button switchuser,updatapwd;
    private RelativeLayout sign,name,phone,qqNumber,weixin,sex,school,department,interest,skill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        initView();
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);

        sign = (RelativeLayout) findViewById(R.id.sign);
        name = (RelativeLayout) findViewById(R.id.name);
        phone = (RelativeLayout) findViewById(R.id.phone);
        qqNumber = (RelativeLayout) findViewById(R.id.qqNumber);
        weixin = (RelativeLayout) findViewById(R.id.weixin);
        sex = (RelativeLayout) findViewById(R.id.sex);
        school = (RelativeLayout) findViewById(R.id.school);
        department = (RelativeLayout) findViewById(R.id.department);
        interest = (RelativeLayout) findViewById(R.id.interest);
        skill = (RelativeLayout) findViewById(R.id.skill);

        switchuser = (Button) findViewById(R.id.switchuser);
        updatapwd = (Button) findViewById(R.id.updatapwd);

        sign.setOnClickListener(this);
        name.setOnClickListener(this);
        phone.setOnClickListener(this);
        qqNumber.setOnClickListener(this);
        weixin.setOnClickListener(this);
        sex.setOnClickListener(this);
        school.setOnClickListener(this);
        department.setOnClickListener(this);
        interest.setOnClickListener(this);
        skill.setOnClickListener(this);

        switchuser.setOnClickListener(this);
        updatapwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                ActivityContror.removeActivity(this);
                break;
            case R.id.sign:
                ActivityContror.removeActivity(this);
                break;
            case R.id.name:
                ActivityContror.removeActivity(this);
                break;
            case R.id.phone:
                ActivityContror.removeActivity(this);
                break;
            case R.id.qqNumber:
                ActivityContror.removeActivity(this);
                break;
            case R.id.weixin:
                ActivityContror.removeActivity(this);
                break;
            case R.id.sex:
                ActivityContror.removeActivity(this);
                break;
            case R.id.school:
                ActivityContror.removeActivity(this);
                break;
            case R.id.department:
                ActivityContror.removeActivity(this);
                break;
            case R.id.interest:
                ActivityContror.removeActivity(this);
                break;
            case R.id.skill:
                ActivityContror.removeActivity(this);
                break;
            case R.id.switchuser:
                Intent intent = new Intent(this,LoginAty.class);
                intent.setAction("action");
                startActivity(intent);
                ActivityContror.removeActivity(this);
                break;
            case R.id.updatapwd:
                new UpdatePwdDialog(this).show();
                break;
        }
    }
}
