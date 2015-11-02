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

/**
 * Created by gzw on 2015/10/14.
 */
public class FPerson extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout toPersonData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person,container,false);
        initView();
        return view;
    }

    private void initView() {
        toPersonData = (LinearLayout) view.findViewById(R.id.to_person_data);
        toPersonData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_person_data:
                startActivity(new Intent(getActivity(), PersonDataAty.class));
                break;
        }
    }
}
