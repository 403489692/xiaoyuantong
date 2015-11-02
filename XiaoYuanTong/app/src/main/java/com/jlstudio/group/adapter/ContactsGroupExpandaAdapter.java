package com.jlstudio.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.group.bean.ContactStudent;
import com.jlstudio.group.bean.ContactsGrade;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/10/24.
 */
public class ContactsGroupExpandaAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ContactsGrade> gradeList;
    private HashMap<ContactsGrade, List<ContactStudent>> studentMap;

    public ContactsGroupExpandaAdapter(HashMap<ContactsGrade, List<ContactStudent>> studentMap, Context context, List<ContactsGrade> gradeList) {
        this.studentMap = studentMap;
        this.context = context;
        this.gradeList = gradeList;
    }

    @Override
    public int getGroupCount() {
        return this.gradeList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        return this.studentMap.get(this.gradeList.get(position)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.gradeList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.studentMap.get(this.gradeList.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosititon) {
        return childPosititon;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contacts_parent, null);
        }
        holder.parent_tv_grade = (TextView) convertView.findViewById(R.id.parent_tv_grade);
        holder.parent_tv_counts = (TextView) convertView.findViewById(R.id.parent_tv_counts);

        holder.parent_tv_grade.setText(gradeList.get(groupPosition).getGrade());
        holder.parent_tv_counts.setText(studentMap.get(gradeList.get(groupPosition)).size()+" 人");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosititon,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contacts_child, null);
        }
        holder.child_tv_name = (TextView) convertView.findViewById(R.id.child_tv_name);

        holder.child_tv_name.setText(this.studentMap.get(gradeList.get(groupPosition)).get(childPosititon).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public class ViewHolder {
        TextView parent_tv_grade;
        TextView parent_tv_counts;

        TextView child_tv_name;
    }
}
