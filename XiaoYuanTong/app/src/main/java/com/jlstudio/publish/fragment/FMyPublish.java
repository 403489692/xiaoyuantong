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
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.CatchData;
import com.jlstudio.main.db.DBOption;
import com.jlstudio.main.util.ProgressUtil;
import com.jlstudio.main.widget.SwipeRefreshLayout;
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
public class FMyPublish extends Fragment implements AdapterView.OnItemClickListener {
    private SwipeRefreshLayout view;
    private ListView listView;
    private PublishDatasAdapter adapter;
    private List<PublishData> list;
    private GetNotificationNet gn;
    private CatchData data;
    private DBOption db;
    private String action = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_publish_datas, container, false);
        action = getActivity().getIntent().getAction();
        gn = GetNotificationNet.getInstence(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        getDatas();
        initListView();
        super.onResume();
    }

    private void getDatas(){
        list = new ArrayList<>();
        db = new DBOption(getActivity());
        adapter = new PublishDatasAdapter(getActivity(),list);
        data = db.getCatch(Config.URL+Config.SENDMSG+Config.loadUser(getActivity()).getUsername());
        if(data == null){
            getDatasFromNet();
        }else{
            Long time = System.currentTimeMillis();
            long catchtime = Long.parseLong(data.getTime());
            if((time - catchtime) > Config.CATCHTIME||action!=null){
                action = null;

                getDatasFromNet();
            }else{
                list = JsonToPubhlishData.getPublishDataSend(data.getContent());
                adapter.setList(list);
            }

        }
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);
        //list = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            list.add(new PublishData(i+"会议","今天是个好日子","2015.10.17"));
//        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        view.setColorScheme(R.color.fresh_one, R.color.fresh_two, R.color.fresh_three, R.color.fresh_four);
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDatasFromNet();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ShowSendPublishAty.class);
        intent.putExtra("data", list.get(position));
        intent.putExtra("action","1");
        startActivity(intent);
    }

    private void getDatasFromNet(){
        //ProgressUtil.showProgressDialog(getActivity(),"数据加载");
        gn.getDepartment(Config.URL, Config.SENDMSG, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                if (s != null) {
                    if(data == null){
                        db.insertCatch(Config.URL+Config.SENDMSG+Config.loadUser(getActivity()).getUsername(),s,System.currentTimeMillis()+"");
                    }else{
                        db.updateCatch(Config.URL+Config.SENDMSG+Config.loadUser(getActivity()).getUsername(),s,System.currentTimeMillis()+"");
                    }
                    list = JsonToPubhlishData.getPublishDataSend(s);
                    adapter.setList(list);


                } else {
                    ShowToast.show(getActivity(), "没有通知");
                    list = new ArrayList<>();
                }
                view.setRefreshing(false);
                //ProgressUtil.closeProgressDialog();
                Log.d("hehe", s);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ShowToast.show(getActivity(), "获取数据失败");
                //ProgressUtil.closeProgressDialog();
                view.setRefreshing(false);
                Log.d("hehe", "error");
            }
        }, Config.loadUser(getActivity()).getUsername());

    }
}
