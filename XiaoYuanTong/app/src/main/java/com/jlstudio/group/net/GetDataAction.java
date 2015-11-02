package com.jlstudio.group.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NewOr on 2015/10/26.
 */
public class GetDataAction {
    RequestQueue mQueue;
    StringRequest stringRequest;
    String responce_contacts;
    String friend_info;
    String group_contacts_data;

    /**
     * 普通用户获取通讯录数据
     *
     * @param action
     * @param username
     * @return
     */
    public String getContactsData(String action, final String username) {

        stringRequest = new StringRequest(Request.Method.POST, GetDataUtil.url + "/" + action, new Response.Listener<String>() {
            @Override
            public void onResponse(String responce) {
                responce_contacts = responce;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Test", "获取通讯录信息失败");
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                return map;
            }
        };
        mQueue.add(stringRequest);
        return responce_contacts;
    }

    /**
     * 获取好友信息
     *
     * @param action
     * @param friendname
     * @return
     */
    public String getFriendData(String action, final String friendname) {
        stringRequest = new StringRequest(Request.Method.POST, GetDataUtil.url + "/" + action, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                friend_info = s;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Test", "获取好友信息失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", friendname);
                return super.getParams();
            }
        };
        return friend_info;
    }

    /**
     * 获取分组通讯录信息
     * @param action
     * @param username
     * @param dept
     * @return
     */
    public String getGroupContactsData(String action,final String username, final String dept){
        stringRequest = new StringRequest(Request.Method.POST, GetDataUtil.url + "/" + action, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                group_contacts_data = s;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Test", "获取分组通讯录失败");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("name",username);
                map.put("dept",dept);
                return map;
            }
        };
        return group_contacts_data;
    }
}
