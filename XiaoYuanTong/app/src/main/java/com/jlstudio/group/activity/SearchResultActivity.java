package com.jlstudio.group.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.jlstudio.R;

public class SearchResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
    }

}
