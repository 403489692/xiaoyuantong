package com.jlstudio.main.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jlstudio.group.fragment.ContactsGroupFragment;
import com.jlstudio.main.Fragment.FContact;
import com.jlstudio.main.Fragment.FFind;
import com.jlstudio.main.Fragment.FMain;
import com.jlstudio.main.Fragment.FPerson;
import com.jlstudio.main.Fragment.FSchoolMessage;
import com.jlstudio.R;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.application.MyApplication;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Fragment currentFragment = null;
    private TextView schoolmessage,contact,main,find,person;
    private TextView tschoolmessage,tcontact,tfind,tperson;
    private TextView currentText = null,tcurrentText = null;
    private TextView title;
    private String id;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!Config.isNetworkAvailable(this)){
            ShowToast.show(this, "网络好像发生了错误");
        }
        if(Config.isFirst(this)){
            startActivity(new Intent(this, WelcomeAty.class));
            ActivityContror.removeActivity(this);
            return;
        }
        initView();
        initFragment();

    }

    private void initFragment() {
        currentFragment = new FMain();
        title.setText("主界面");
        getFragmentManager().beginTransaction().add(R.id.fragment,currentFragment).commit();
    }

    private void initView() {
        //schoolmessage = (TextView) findViewById(R.id.schoolmessage);
        //tschoolmessage = (TextView) findViewById(R.id.tschoolmessage);
        contact = (TextView) findViewById(R.id.contact);
        tcontact = (TextView) findViewById(R.id.tcontact);
        main = (TextView) findViewById(R.id.main);
        find = (TextView) findViewById(R.id.find);
        tfind = (TextView) findViewById(R.id.tfind);
        //person = (TextView) findViewById(R.id.person);
        //tperson = (TextView) findViewById(R.id.tperson);
       // schoolmessage.setOnClickListener(this);
        contact.setOnClickListener(this);
        main.setOnClickListener(this);
        find.setOnClickListener(this);
        //person.setOnClickListener(this);

        title = (TextView) findViewById(R.id.title);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
       // schoolmessage.setTypeface(iconfont);
        contact.setTypeface(iconfont);
        main.setTypeface(iconfont);
        find.setTypeface(iconfont);
        //person.setTypeface(iconfont);
        main.setTextColor(Color.BLUE);
        currentText = main;
    }

    public void switchContent(Fragment to) {
        if (currentFragment != to) {
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(currentFragment).add(R.id.fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            currentFragment = to;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.schoolmessage:
//                title.setText("校信");
//                schoolmessage.setTextColor(Color.BLUE);
//                tschoolmessage.setTextColor(Color.BLUE);
//                if(currentText!=null &&currentText!=schoolmessage ){
//                    if(currentText == main){
//                        startAnima();
//                    }
//                    currentText.setTextColor(Color.BLACK);
//                }
//                if(tcurrentText!=null && tcurrentText!=tschoolmessage){
//                    tcurrentText.setTextColor(Color.BLACK);
//                }
//                currentText = schoolmessage;
//                tcurrentText = tschoolmessage;
//                switchContent(new FSchoolMessage());
//                break;
            case R.id.contact:
                contact.setTextColor(Color.BLUE);
                tcontact.setTextColor(Color.BLUE);
                if(currentText!=null &&currentText!=contact ){
                    currentText.setTextColor(Color.BLACK);
//                    if(currentText == main){
//                        startAnima();
//                    }
                }
                if(tcurrentText!=null && tcurrentText!=tcontact){
                    tcurrentText.setTextColor(Color.BLACK);
                }
                currentText = contact;
                tcurrentText = tcontact;
                title.setText("通讯录");
                ContactsGroupFragment contactsGroupFragment = new ContactsGroupFragment();
                switchContent(contactsGroupFragment);
                break;
            case R.id.main:
                title.setText("主界面");
                switchContent(new FMain());
                main.setTextColor(Color.BLUE);
                //startAnima();
                if(currentText!=null &&currentText!=main ){
                    currentText.setTextColor(Color.BLACK);
                }
                if(tcurrentText!=null){
                    tcurrentText.setTextColor(Color.BLACK);
                }
                currentText = main;
                tcurrentText = null;
                break;
            case R.id.find:
                find.setTextColor(Color.BLUE);
                tfind.setTextColor(Color.BLUE);
                if(currentText!=null &&currentText!=find ){
                    currentText.setTextColor(Color.BLACK);
//                    if(currentText == main){
//                        startAnima();
//                    }
                }
                if(tcurrentText!=null && tcurrentText!=tfind){
                    tcurrentText.setTextColor(Color.BLACK);
                }
                currentText = find;
                tcurrentText = tfind;
                title.setText("发现");
                switchContent(new FFind());
                break;
//            case R.id.person:
//                person.setTextColor(Color.BLUE);
//                tperson.setTextColor(Color.BLUE);
//                if(currentText!=null &&currentText!=person ){
//                    currentText.setTextColor(Color.BLACK);
//                    if(currentText == main){
//                        startAnima();
//                    }
//                }
//                if(tcurrentText!=null && tcurrentText!=tperson){
//                    tcurrentText.setTextColor(Color.BLACK);
//                }
//                currentText = person;
//                tcurrentText = tperson;
//                title.setText("我的");
//                switchContent(new FPerson());
//                break;
        }
    }
    private void startAnima(){
        Animation operatingAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        //LinearInterpolator lin = new LinearInterpolator();
        //operatingAnim.setInterpolator(lin);
        //main.setAnimation(operatingAnim);
        main.startAnimation(operatingAnim);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-exitTime > 2000){
            ShowToast.show(this,"再按一次退出应用");
            exitTime = System.currentTimeMillis();
            return;
        }
        ActivityContror.removeActivity(this);
    }
}
