package com.jlstudio.swzl.httpNet;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jlstudio.swzl.bean.commons;
import com.jlstudio.swzl.bean.lostfound;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe on 2015/10/26.
 */
public class util {
    public static String URL = "http://192.168.1.104:8080/xiaoyuantong/GetListLost";


    /**
     *所有的数据访问服务器的函数入口
     * @param context 传入上下文对象
     * @param httpurl 需要访问的服务器地址
     * @param which 需要的数据代码
     */
    public static void LostAndFound(Context context, String httpurl, int which) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //将gson字符串转化为集合
                        //将集合存储到工具类
                        ExtraData.All_lostf = changeJsonToArrayDate(s);
                        Log.d("AAA", "访问数据库成功了！----->");
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("AAA", "response -> 访问失败了！！！！" + volleyError);
                        }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("found","which");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void LostAndFound_publish(final Context context, String httpurl, lostfound lostf) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final lostfound lf = lostf;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //将gson字符串转化为集合
                        //将集合存储到工具类
                        ExtraData.All_lostf = changeJsonToArrayDate(s);
                        Toast.makeText(context,"发布成功",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("AAA","response -> 访问失败了！！！！" +volleyError);
                Toast.makeText(context,"发布失败",Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("title",lf.getTitle());
                map.put("content",lf.getDescribe());
                map.put("time",lf.getTime()+"你是煞笔");
                map.put("nickname",lf.getNickname());
                //上传图片
                map.put("pic","lf.getPic()");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    /**
     * 将json字符串转化为对象集合
     * @param json
     * @return 失物招领数据
     */
    public static ArrayList<lostfound> changeJsonToArrayDate(String json){
        ArrayList<lostfound> list = new ArrayList<lostfound>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("datas");
            //遍历json对象集合
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                list.add(new lostfound(o.getInt("ownerId"),o.getString("describe"),o.getInt("pic"),
                                        o.getInt("integral"), o.getString("time"),o.getInt("flag"),o.getInt("common_count"),
                                        o.getString("nickname"),o.getString("nickname")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //发送给服务器评论的内容
    public static void sendCommon(final Context context, String httpurl, commons com){
        Log.d("AAA","进入发送操作");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final commons comm = com;
        int flag = 0;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //将gson字符串转化为集合
                        //将集合存储到工具类
                        ExtraData.All_lostf = changeJsonToArrayDate(s);
                        Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("AAA", "response -> 访问失败了！！！！" +volleyError);
                Toast.makeText(context,"评论失败",Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("content",comm.getContent());
                map.put("time", comm.getTime());
                map.put("nickname",comm.getNickname());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    //获取当前时间的函数
    public static String getcurTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }
}