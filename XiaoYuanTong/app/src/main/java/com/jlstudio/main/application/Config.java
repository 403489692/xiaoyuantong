package com.jlstudio.main.application;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.jlstudio.main.bean.User;

/**
 * Created by gzw on 2015/10/20.
 */
public class Config {
    //网络相关
    public static final String URL = "http://192.168.1.104:8080/xiaoyuantong/";
    //public static final String URL = "http://zmzmdr.com:8080/xiaoyuantong/";
    public static final String RECMSG = "GetRecMsg";
    public static final String SENDMSG = "GetSendMsg";
    public static final String GETDEPARTMENT = "GetDepartment";
    public static final String GETTYPE = "GetType";
    public static final String GETPERSON = "GetPerson";
    public static final String SENDPUBLISH = "SendPublish";
    public static final String GETQUESTION = "GetQuestion";
    public static final String GETANSWER = "GetAnswer";
    public static final String SENDANSWER = "PostAnswer";
    public static final String SENDQUESTION = "PostQuestion";
    public static final String GETUNREPLYPERSON = "GetUnReplyPerson";
    public static final String GETSCHEDULE = "GetSchedule";
    public static final String GETPHONE = "GetPhone";
    public static final String NOTICECONFIRM = "NoticeConfirm";
    public static final String DELETENOTICE = "DeleteNotice";
    public static final String DELETEMYSENDNOTICE = "DeleteMySendNotice";
    public static String SCHEDULE = "CourseTable";
    public static String GETSCORE = "Score";
    public static String CETSCORE = "CetScore";
    public static String LOGIN = "Login";
    public static String UPDATEPWD = "UpdatePwd";

    //文件相关
    public static final String FILENAME = "share";
    public static final String USERFILE = "user";
    //缓存时间
    public static final int CATCHTIME = 1000*60 ;



    public static void savePublishType(Context context, String str) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("publishtype", str);
        editor.commit();
    }

    public static String loadPublishType(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return share.getString("publishtype", null);
    }




    public static void saveType(Context context, String str) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("type", str);
        editor.commit();
    }

    public static String loadType(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return share.getString("type", null);
    }



    public static void savePublish(Context context, String str) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("publish", str);
        editor.commit();
    }

    public static String loadPublish(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return share.getString("publish", null);
    }

    public static void saveQuestion(Context context, String str) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("question", str);
        editor.commit();
    }

    public static String loadQuestion(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return share.getString("question", null);
    }

    public static void saveAnswer(Context context, String str) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("answer", str);
        editor.commit();
    }

    public static String loadAnswer(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return share.getString("answer", null);
    }

    public static void saveSchedule(Context context,String string) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("schedule", string);
        editor.commit();
    }
    public static void saveDepartment(Context context,String string) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("department",string);
        editor.commit();
    }
    public static String loadDepartment(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return share.getString("department", "");
    }
    public static void saveUser(Context context,User user) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username",user.getUsername());
        editor.putString("password",user.getPassword());
        editor.putString("nickname",user.getNickname());
        editor.putString("classid",user.getClassid());
        editor.putString("departmentid",user.getDepartmentid());
        editor.putString("role",user.getRole());
        editor.commit();
    }
    public static User loadUser(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return new User(share.getString("username",""),share.getString("password",""),
                share.getString("nickname",""),share.getString("role",""),share.getString("classid",""),
                share.getString("departmentid",""));
    }
    public static void saveBaseUser(Context context,User user) {
        SharedPreferences share = context.getSharedPreferences(USERFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username",user.getUsername());
        editor.putString("password",user.getPassword());
        editor.putString("nickname",user.getNickname());
        editor.putString("classid",user.getClassid());
        editor.putString("departmentid",user.getDepartmentid());
        editor.putString("role",user.getRole());
        editor.commit();
    }
    public static User loadBaseUser(Context context) {
        SharedPreferences share = context.getSharedPreferences(USERFILE,Context.MODE_PRIVATE);
        return new User(share.getString("username",""),share.getString("password",""),
                share.getString("nickname",""),share.getString("role",""),share.getString("classid",""),
                share.getString("departmentid",""));
    }
    public static void saveDisplay(Context context,int width) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt("display", width);
        editor.commit();
    }
    public static int loadDisplay(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return share.getInt("display", 480);
    }
    public static void saveQueryuser(Context context,String user) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("queryuser", user);
        editor.commit();
    }
    public static String loadQueryuser(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return share.getString("queryuser", "");
    }
    public static boolean isFirst(Context context) {
        SharedPreferences share = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        boolean flag = share.getBoolean("isfirst", true);
        if(flag == true){
            SharedPreferences.Editor editor = share.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
