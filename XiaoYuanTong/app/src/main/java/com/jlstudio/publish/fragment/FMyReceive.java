package com.jlstudio.publish.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jlstudio.R;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.publish.activity.ShowReceivePublishAty;
import com.jlstudio.publish.activity.ShowSendPublishAty;
import com.jlstudio.publish.adapter.PublishDatasAdapter;
import com.jlstudio.publish.bean.PublishData;
import com.jlstudio.publish.net.GetNotificationNet;
import com.jlstudio.publish.util.JsonToPubhlishData;
import com.jlstudio.publish.util.ShowToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzw on 2015/10/16.
 */
public class FMyReceive extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listView;
    private PublishDatasAdapter adapter;
    private List<PublishData> list;
    private GetNotificationNet gn;
    private DBOption db;
    private CatchData data;
    private String action = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish_datas, container, false);
        action = getActivity().getIntent().getAction();
        gn = GetNotificationNet.getInstence(getActivity());
        initListView();

        return view;
    }

    @Override
    public void onResume() {
        getDatas();
        super.onResume();
    }

    private void getDatas(){
//        list = new ArrayList<>();
//        adapter = new PublishDatasAdapter(getActivity(),list);
        db = new DBOption(getActivity());
        data = db.getCatch(Config.URL+Config.RECMSG+Config.loadUser(getActivity()).getUsername());
        if(true){
            getDatasFromNet();
        }else{
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if((time - catchtime) > Config.CATCHTIME){
                getDatasFromNet();
            }else{
                list = JsonToPubhlishData.getPublishData(data.getContent());
                adapter.setList(list);
            }

        }
    }
    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);
        list = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            list.add(new PublishData("会议","今天是个好日子","2015.10.17"));
//        }
        adapter = new PublishDatasAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ShowReceivePublishAty.class);
        intent.putExtra("data", list.get(position));
        intent.putExtra("action","0");
        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }
    private void getDatasFromNet(){
        gn.getDepartment(Config.URL, Config.RECMSG,  new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                if(s!=null){
                    if(data == null){
                        db.insertCatch(Config.URL+Config.RECMSG+Config.loadUser(getActivity()).getUsername(),s,System.currentTimeMillis()+"");
                    }else{
                        db.updateCatch(Config.URL+Config.RECMSG+Config.loadUser(getActivity()).getUsername(),s,System.currentTimeMillis()+"");
                    }
                    //Config.saveRecmsg(getActivity(), s);
                    list = JsonToPubhlishData.getPublishData(s);
                    adapter.setList(list);
                }else{
                    ShowToast.show(getActivity(),"没有通知");
                    list = new ArrayList<PublishData>();
                }
                Log.d("hehe", s);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(getActivity(), "获取数据失败");
            }
        }, Config.loadUser(getActivity()).getUsername());
    }
}
