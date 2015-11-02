package com.jlstudio.main.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jlstudio.R;
import com.jlstudio.main.activity.AppHelperAty;
import com.jlstudio.main.activity.SessionAty;
import com.jlstudio.main.activity.SystemMessageAty;

/**
 * Created by gzw on 2015/10/14.
 */
public class FSchoolMessage extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout friendMessage,systemMessage,appHelper,onlineServer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schoolmessage,container,false);
        initView();
        return view;
    }

    private void initView() {
        friendMessage = (LinearLayout) view.findViewById(R.id.friend_message);
        systemMessage = (LinearLayout) view.findViewById(R.id.system_message);
        appHelper = (LinearLayout) view.findViewById(R.id.app_helper);
        onlineServer = (LinearLayout) view.findViewById(R.id.online_server);
        friendMessage.setOnClickListener(this);
        systemMessage.setOnClickListener(this);
        appHelper.setOnClickListener(this);
        onlineServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friend_message:
                startActivity(new Intent(getActivity(), SessionAty.class));
                break;
            case R.id.system_message:
                startActivity(new Intent(getActivity(), SystemMessageAty.class));
                break;
            case R.id.app_helper:
                startActivity(new Intent(getActivity(), AppHelperAty.class));
                break;
            case R.id.online_server:
                break;
        }
    }
}
