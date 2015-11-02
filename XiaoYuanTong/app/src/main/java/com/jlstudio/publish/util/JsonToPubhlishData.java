package com.jlstudio.publish.util;

import com.jlstudio.publish.bean.Person;
import com.jlstudio.publish.bean.PublishData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzw on 2015/10/20.
 */
public class JsonToPubhlishData {
    public static List<PublishData> getPublishData(String string){
        List<PublishData> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(string);
            JSONArray array = json.getJSONArray("datas");
            for(int i = 0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                PublishData p = new PublishData();
                p.setTitle(o.getString("title"));
                p.setContent(o.getString("content"));
                p.setId(o.getString("id"));
                p.setFlag(o.getString("flag"));
                p.setTime(o.getString("time"));
                list.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    public static List<PublishData> getPublishDataSend(String string){
        List<PublishData> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(string);
            JSONArray array = json.getJSONArray("datas");
            for(int i = 0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                list.add(new PublishData(o.getString("title"),o.getString("time"),o.getString("content"),o.getString("replyedCount"),o.getString("unreplyCount")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    public static List<String> getDepartment(String string){
        String[] str = string.split(",");
        List<String> list = new ArrayList<String>();
        for(String s : str){
            list.add(s);
        }
        return list;
    }
    public static List<Person> getPersons(String string){
        List<Person> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(string);
            JSONArray array = json.getJSONArray("datas");
            for(int i = 0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                list.add(new Person(o.getString("realname"),o.getString("userid")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    public static List<Person> getPersonFromJson(String string){
        List<Person> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(string);
            JSONArray array = json.getJSONArray("datas");
            for(int i = 0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                list.add(new Person(o.getString("realname"),o.getString("phone")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
