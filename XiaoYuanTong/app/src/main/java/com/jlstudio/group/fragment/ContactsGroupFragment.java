package com.jlstudio.group.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.jlstudio.R;
import com.jlstudio.group.adapter.ContactsGroupExpandaAdapter;
import com.jlstudio.group.bean.ContactStudent;
import com.jlstudio.group.bean.ContactsGrade;
import com.jlstudio.group.activity.ContactsDataActivity;
import com.jlstudio.group.net.GetDataAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsGroupFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    private View view;

    private ExpandableListView ex_listview_contacts;
    private ContactsGroupExpandaAdapter myAdapter;

    private List<ContactsGrade> gradeList;
    private HashMap<ContactsGrade, List<ContactStudent>> studentMap;

    private ContactsGrade grade;
    private ContactStudent student;

    public ContactsGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_contacts_group, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        grade = new ContactsGrade();
        student = new ContactStudent();
        initView();
        prepareListData();
        myAdapter = new ContactsGroupExpandaAdapter(studentMap, getActivity(), gradeList);
        ex_listview_contacts.setAdapter(myAdapter);
        initEvent();

    }

    private void initEvent() {
        ex_listview_contacts.setOnChildClickListener(this);
    }

    private void prepareListData() {
        gradeList = new ArrayList<>();
        studentMap = new HashMap<>();

        //设置Parent值
        gradeList.add(new ContactsGrade("13软件开发1班"));
        gradeList.add(new ContactsGrade("13软件开发2班"));
        gradeList.add(new ContactsGrade("13软件开发3班"));
        gradeList.add(new ContactsGrade("13软件开发4班"));
        gradeList.add(new ContactsGrade("13软件开发5班"));
        gradeList.add(new ContactsGrade("13软件开发6班"));
        gradeList.add(new ContactsGrade("13软件开发7班"));
        gradeList.add(new ContactsGrade("13软件开发8班"));
        gradeList.add(new ContactsGrade("13软件开发9班"));
        gradeList.add(new ContactsGrade("13软件开发10班"));
        gradeList.add(new ContactsGrade("13软件开发11班"));
        gradeList.add(new ContactsGrade("13软件开发12班"));
        gradeList.add(new ContactsGrade("13中软实验班"));

        //设置Child值
        List<ContactStudent> stu1 = new ArrayList<ContactStudent>();
        stu1.add(new ContactStudent("NewOrin"));
        stu1.add(new ContactStudent("Bingo Zhang"));
        stu1.add(new ContactStudent("Kobe Bryant"));
        List<ContactStudent> stu2 = new ArrayList<ContactStudent>();
        stu2.add(new ContactStudent("NewOrin"));
        stu2.add(new ContactStudent("Bingo Zhang"));
        stu2.add(new ContactStudent("Kobe Bryant"));
        stu2.add(new ContactStudent("Michael Jordan"));
        stu2.add(new ContactStudent("Lebron James"));
        stu2.add(new ContactStudent("Chris Pual"));

        studentMap.put(gradeList.get(0), stu1);
        studentMap.put(gradeList.get(1), stu2);
        studentMap.put(gradeList.get(2), stu2);
        studentMap.put(gradeList.get(3), stu1);
        studentMap.put(gradeList.get(4), stu1);
        studentMap.put(gradeList.get(5), stu2);
        studentMap.put(gradeList.get(6), stu1);
        studentMap.put(gradeList.get(7), stu1);
        studentMap.put(gradeList.get(8), stu1);
        studentMap.put(gradeList.get(9), stu2);
        studentMap.put(gradeList.get(10), stu1);
        studentMap.put(gradeList.get(11), stu1);
        studentMap.put(gradeList.get(12), stu2);


    }

    private void initView() {
        ex_listview_contacts = (ExpandableListView) view.findViewById(R.id.ex_listview_contacts);
    }

    /**
     * 从服务端获取分组通讯录的信息
     */
    private void setData() {
        GetDataAction action = new GetDataAction();
        String group_contacts_data = action.getGroupContactsData("getGroupContactsData", "username", "dept");
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(getActivity(), ContactsDataActivity.class);
        intent.putExtra("name",studentMap.get(gradeList.get(groupPosition)).get(childPosition).getName());
        startActivity(intent);
        return false;
    }
}
