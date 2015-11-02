package com.jlstudio.swzl.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jlstudio.R;
import com.jlstudio.swzl.activity.LostAndFoundDetail;
import com.jlstudio.swzl.bean.lostfound;

import java.util.ArrayList;

/**
 * Created by Joe on 2015/10/23.
 */
public class LostAndFoundAdapter extends BaseAdapter{
    private ArrayList<lostfound> allLostFound = new ArrayList<lostfound>();
    LayoutInflater inflater;
    private lostfound lf = new lostfound();
    Context context = null;
    View convertView;
    public LostAndFoundAdapter(Context context,ArrayList<lostfound> allLostFound) {
        this.inflater = inflater.from(context);
        this.allLostFound = allLostFound;
        this.context = context;
    }

    @Override
    public int getCount() {
        return allLostFound.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //获取当前item对应的对象
        lf = allLostFound.get(position);
        convertView = inflater.inflate(R.layout.lostandfound_item, null);

        TextView title = (TextView) convertView.findViewById(R.id.lost_found_title);
        TextView nickname = (TextView) convertView.findViewById(R.id.lost_found_nickname);
        TextView comment_count = (TextView) convertView.findViewById(R.id.lost_found_count);
        TextView time = (TextView) convertView.findViewById(R.id.lost_found_time);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.lost_found_linear);


        title.setText(lf.getTitle());
        nickname.setText(lf.getNickname());
        comment_count.setText("1");
        time.setText(lf.getTime());

        class ItemAdapter implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(context, LostAndFoundDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lostandfound", allLostFound.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
        linearLayout.setOnClickListener(new ItemAdapter());
        return convertView;
    }
}