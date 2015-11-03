package com.jlstudio.publish.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.activity.LoginAty;
import com.jlstudio.main.activity.MainActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.User;
import com.jlstudio.publish.dialog.SelectPublishType;
import com.jlstudio.publish.fragment.FMyPublish;
import com.jlstudio.publish.fragment.FMyReceive;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;
import com.jlstudio.publish.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class PublishDatasAty extends BaseActivity implements View.OnClickListener{
    private TextView myReceive,myPublish,back,title_name;
    private ImageView fragmentTitleImage1,fragmentTitleImage2;
    private Fragment currentFragment = null;
    private Button btnPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_datas);
        User user = Config.loadUser(this);
        if(StringUtil.isEmpty(user.getUsername())){
            user = Config.loadBaseUser(this);
            if(StringUtil.isEmpty(user.getUsername())){
                startActivity(new Intent(this,LoginAty.class));
                ActivityContror.removeActivity(this);
            }else{
                Config.saveUser(this,user);
                JPushInterface.setAlias(this, user.getUsername(), new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("hehe", i + "");
                    }
                });
            }
        }else{
            JPushInterface.setAlias(this, user.getUsername(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.d("hehe", i + "");
                }
            });
        }
        initView();
        currentFragment = new FMyReceive();
        getFragmentManager().beginTransaction().add(R.id.fragment,currentFragment).commit();

    }

    private void initView() {
        myReceive = (TextView) findViewById(R.id.myreceive);
        myPublish = (TextView) findViewById(R.id.mypublish);
        btnPublish = (Button) findViewById(R.id.publish);
        back = (TextView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        fragmentTitleImage1 = (ImageView) findViewById(R.id.fragment_title_image1);
        fragmentTitleImage2 = (ImageView) findViewById(R.id.fragment_title_image2);
        fragmentTitleImage2.setVisibility(View.INVISIBLE);
        myReceive.setOnClickListener(this);
        myPublish.setOnClickListener(this);
        btnPublish.setOnClickListener(this);
        Typeface iconfont = Typeface.createFromAsset(getAssets(),"fonts/iconfont.ttf");
        back.setTypeface(iconfont);
        back.setOnClickListener(this);
        title_name.setText("消息");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myreceive:
                switchContent(new FMyReceive());
                myReceive.setTextColor(Color.BLUE);
                myPublish.setTextColor(Color.BLACK);
                fragmentTitleImage1.setVisibility(View.VISIBLE);
                fragmentTitleImage2.setVisibility(View.INVISIBLE);
                break;
            case R.id.mypublish:
                switchContent(new FMyPublish());
                myReceive.setTextColor(Color.BLACK);
                myPublish.setTextColor(Color.BLUE);
                fragmentTitleImage2.setVisibility(View.VISIBLE);
                fragmentTitleImage1.setVisibility(View.INVISIBLE);
                break;
            case R.id.publish:
                new SelectPublishType(this).show();
                //startActivity(new Intent(this,AddPublishAty.class));
                break;
            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));
                ActivityContror.removeActivity(this);
                break;
        }
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

}
