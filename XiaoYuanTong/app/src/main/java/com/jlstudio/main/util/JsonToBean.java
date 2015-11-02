package com.jlstudio.main.util;

import com.jlstudio.main.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gzw on 2015/10/30.
 */
public class JsonToBean {
    public static User getUser(JSONObject json) {
        User user = null;
        try {
            user = new User(json.getString("username"), json.getString("password"),
                    json.getString("nickname"), json.getString("role"),
                    json.getString("classname"), json.getString("departmentname"));
            return user;

        } catch (JSONException e) {
            e.printStackTrace();
            return user;
        }
    }
}
