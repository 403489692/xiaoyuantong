package com.jlstudio.swzl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.jlstudio.R;
import com.jlstudio.main.activity.BaseActivity;
import com.jlstudio.main.activity.MainActivity;
import com.jlstudio.main.application.ActivityContror;
import com.jlstudio.swzl.adapter.LostAndFoundAdapter;
import com.jlstudio.swzl.bean.lostfound;

import java.util.ArrayList;

public class LostAndFound extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout linearLayout;
    private ArrayList<lostfound> lostfounds = new ArrayList<lostfound>();
    private ListView listView;
    private ImageView imageView_back;
    private ImageView imageView_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lost_found_all_list);
        imageView_back = (ImageView) findViewById(R.id.lost_found_back);
        imageView_menu = (ImageView) findViewById(R.id.lost_found_menu);
        //获取数据源
        lostfounds = getData();
        //声明适配器对象
        LostAndFoundAdapter lostAndFoundAdapter = new LostAndFoundAdapter(this,lostfounds);
        listView.setAdapter(lostAndFoundAdapter);
        imageView_menu.setOnClickListener(this);
        imageView_back.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private ArrayList<lostfound> getData() {
        //访问服务器 获取的是全部的失物招领数据
//       util.LostAndFound(this, util.URL, ExtraData.LOSTANDFOUND);
////        初始化数据
//        ArrayList<lostfound> arr_lost = ExtraData.All_lostf;
        ArrayList<lostfound> arr_lost = new ArrayList<lostfound>();
        arr_lost.add(new lostfound(1,"丢失于二食堂",R.drawable.logo,30,"2015-10-22 23:00:53",
                0,1,"爱死了小秃驴","乔思荣捡到一个钱包"));
        arr_lost.add(new lostfound(1,"丢失于二食堂",R.drawable.logo,30,"2015-10-22 23:00:53",
                10,1,"爱死了韩晓怡","乔思荣捡到一个钱包"));
        arr_lost.add(new lostfound(1,"丢失于二食堂",R.drawable.logo,30,"2015-10-22 23:00:53",
                20,1,"爱死了小可爱","乔思荣捡到一个钱包"));
        arr_lost.add(new lostfound(1,"丢失于二食堂",R.drawable.logo,30,"2015-10-22 23:00:53",
                30,1,"爱死了李易安","乔思荣捡到一个钱包"));
        arr_lost.add(new lostfound(1,"丢失于二食堂",R.drawable.logo,30,"2015-10-22 23:00:53",
                40,1,"爱死了李清照","乔思荣捡到一个钱包"));

        if(arr_lost == null)
        {
            Toast.makeText(this,"服务器没有返回值",Toast.LENGTH_LONG).show();
        }
            return arr_lost;


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.lost_found_back:
                startActivity(new Intent(this, MainActivity.class));
                //ActivityContror.removeActivity(this);
                break;
            case R.id.lost_found_menu:
                startActivity(new Intent(this,LostPublishActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"你点击了"+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, LostAndFoundDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lostandfound", lostfounds.get(position));
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
