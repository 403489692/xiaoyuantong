package com.jlstudio.main.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.jlstudio.main.util.CrashHandler;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by gzw on 2015/10/14.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        Log.d("hehe","初始化成功");
        context = this;
    }
    public static Context getContext(){
        return context;
    }

}
