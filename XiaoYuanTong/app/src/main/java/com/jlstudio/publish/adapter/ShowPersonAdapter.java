package com.jlstudio.publish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.publish.bean.Person;

import java.util.List;

/**
 * Created by gzw on 2015/10/20.
 */
public class ShowPersonAdapter extends BaseAdapter {
    private List<Person> list;
    private Context context;

    public ShowPersonAdapter(Context context, List<Person> list) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<Person> list) {
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
        ViewHolder hoder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null);
            TextView text = (TextView) convertView.findViewById(R.id.title);
            hoder = new ViewHolder(text);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHolder) convertView.getTag();
        }
        hoder.text.setText(list.get(position).getName());
        return convertView;
    }

    private class ViewHolder{
        public TextView text;

        public ViewHolder(TextView text) {
            this.text = text;
        }
    }
}
