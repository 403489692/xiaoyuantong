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
import com.jlstudio.main.activity.PersonDataAty;
import com.jlstudio.weather.activity.InitDataActivity;

/**
 * Created by gzw on 2015/10/14.
 */
public class FFind extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout weather,revise;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find,container,false);
        initView();
        return view;
    }

    private void initView() {
        weather = (LinearLayout) view.findViewById(R.id.weather);
        revise = (LinearLayout) view.findViewById(R.id.revise);
        weather.setOnClickListener(this);
        revise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather:
                startActivity(new Intent(getActivity(), InitDataActivity.class));
                break;
            case R.id.revise:
                startActivity(new Intent(getActivity(), PersonDataAty.class));
                break;
        }
    }
}
