package com.jlstudio.publish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.publish.bean.PublishData;

import java.util.List;

/**
 * Created by gzw on 2015/10/16.
 */
public class PublishDatasAdapter extends BaseAdapter {
    private List<PublishData> list;
    private Context context;

    public PublishDatasAdapter(Context context, List<PublishData> list) {
        this.list = list;
        this.context = context;
    }

    public List<PublishData> getList() {
        return list;
    }

    public void setList(List<PublishData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.publish_datas_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            hoder = new ViewHoder(title,time);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.title.setText(list.get(position).getTitle());
        hoder.time.setText(list.get(position).getTime());
        return convertView;
    }

    private class ViewHoder {
        public TextView title;
        public TextView time;

        public ViewHoder(TextView title, TextView time) {
            this.title = title;
            this.time = time;
        }
    }
}
