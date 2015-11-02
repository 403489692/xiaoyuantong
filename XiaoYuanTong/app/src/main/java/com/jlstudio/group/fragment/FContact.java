package com.jlstudio.group.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jlstudio.R;
import com.jlstudio.group.activity.ContactsDataActivity;
import com.jlstudio.group.adapter.ListViewAdapter;
import com.jlstudio.group.bean.Contacts;
import com.jlstudio.group.net.GetDataAction;
import com.jlstudio.group.util.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by gzw on 2015/10/14.
 */
public class FContact extends Fragment implements AdapterView.OnItemClickListener {
    private View view;

    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    private LinearLayout layoutIndex;
    private ListView listView;
    private TextView tv_show;

    private ListViewAdapter adapter;
    private String[] indexStr = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private List<Contacts> persons = null;
    private List<Contacts> newPersons = new ArrayList<Contacts>();
    private int height;// 字体高度
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();

        //监听焦点变化
        ViewTreeObserver observer = layoutIndex.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {
                if (!flag) {
                    height = layoutIndex.getMeasuredHeight() / indexStr.length;
                    getIndexView();
                    flag = true;
                }
                return true;
            }
        });

    }

    private void initEvent() {
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ContactsDataActivity.class);
        intent.putExtra("name", newPersons.get(position).getName());
        startActivity(intent);
    }

    private void initView() {

        layoutIndex = (LinearLayout) view.findViewById(R.id.layout);
        layoutIndex.setBackgroundColor(Color.parseColor("#ffffff"));
        listView = (ListView) view.findViewById(R.id.listView);
        tv_show = (TextView) view.findViewById(R.id.tv);
        tv_show.setVisibility(View.GONE);
        setData();

        String[] allNames = sortIndex(persons);
        sortList(allNames);

        selector = new HashMap<String, Integer>();
        for (int j = 0; j < indexStr.length; j++) {// 循环字母表，找出newPersons中对应字母的位置
            for (int i = 0; i < newPersons.size(); i++) {
                if (newPersons.get(i).getName().equals(indexStr[j])) {
                    selector.put(indexStr[j], i);
                }
            }
        }
        adapter = new ListViewAdapter(getActivity(), newPersons);

        listView.setAdapter(adapter);
    }

    /**
     * 重新排序获得一个新的List集合
     *
     * @param allNames
     */
    private void sortList(String[] allNames) {
        for (int i = 0; i < allNames.length; i++) {
            if (allNames[i].length() != 1) {
                for (int j = 0; j < persons.size(); j++) {
                    if (allNames[i].equals(persons.get(j).getPinYinName())) {
                        Contacts p = new Contacts(persons.get(j).getName(), persons
                                .get(j).getPinYinName());
                        newPersons.add(p);
                    }
                }
            } else {
                newPersons.add(new Contacts(allNames[i]));
            }
        }

    }


    /**
     * 获取排序后的新数据
     *
     * @param persons
     * @return
     */
    public String[] sortIndex(List<Contacts> persons) {
        TreeSet<String> set = new TreeSet<String>();
        // 获取初始化数据源中的首字母，添加到set中
        for (Contacts person : persons) {
            set.add(StringHelper.getPinYinHeadChar(person.getName()).substring(
                    0, 1));
        }
        // 新数组的长度为原数据加上set的大小
        String[] names = new String[persons.size() + set.size()];
        int i = 0;
        for (String string : set) {
            names[i] = string;
            i++;
        }
        String[] pinYinNames = new String[persons.size()];
        for (int j = 0; j < persons.size(); j++) {
            persons.get(j).setPinYinName(
                    StringHelper
                            .getPingYin(persons.get(j).getName().toString()));
            pinYinNames[j] = StringHelper.getPingYin(persons.get(j).getName()
                    .toString());
        }
        // 将原数据拷贝到新数据中
        System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
        // 自动按照首字母排序
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    /**
     * 绘制索引列表
     */
    public void getIndexView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(getActivity());
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setPadding(10, 0, 10, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event)

                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.length) {// 防止越界
                        String key = indexStr[index];
                        if (selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                listView.setSelectionFromTop(
                                        pos + listView.getHeaderViewsCount(), 0);
                            } else {
                                listView.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(indexStr[index]);
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#bebaba"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#ffffff"));
                            tv_show.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 设置模拟数据
     */
    private void setData() {
        persons = new ArrayList<Contacts>();
        Contacts p1 = new Contacts("耿琦");
        persons.add(p1);
        Contacts p2 = new Contacts("王宝强");
        persons.add(p2);
        Contacts p3 = new Contacts("柳岩");
        persons.add(p3);
        Contacts p4 = new Contacts("文章");
        persons.add(p4);
        Contacts p5 = new Contacts("马伊琍");
        persons.add(p5);
        Contacts p6 = new Contacts("李晨");
        persons.add(p6);
        Contacts p7 = new Contacts("张馨予");
        persons.add(p7);
        Contacts p8 = new Contacts("韩红");
        persons.add(p8);
        Contacts p9 = new Contacts("韩寒");
        persons.add(p9);
        Contacts p10 = new Contacts("丹丹");
        persons.add(p10);
        Contacts p11 = new Contacts("丹凤眼");
        persons.add(p11);
        Contacts p12 = new Contacts("哈哈");
        persons.add(p12);
        Contacts p13 = new Contacts("萌萌");
        persons.add(p13);
        Contacts p14 = new Contacts("蒙混");
        persons.add(p14);
        Contacts p15 = new Contacts("烟花");
        persons.add(p15);
        Contacts p16 = new Contacts("眼黑");
        persons.add(p16);
        Contacts p17 = new Contacts("许三多");
        persons.add(p17);
        Contacts p18 = new Contacts("程咬金");
        persons.add(p18);
        Contacts p19 = new Contacts("程哈哈");
        persons.add(p19);
        Contacts p20 = new Contacts("爱死你");
        persons.add(p20);
        Contacts p21 = new Contacts("阿莱");
        persons.add(p21);
        Contacts p22 = new Contacts("aaaaa");
        persons.add(p22);
        Contacts p23 = new Contacts("bbb");
        persons.add(p23);
        Contacts p24 = new Contacts("abv");
        persons.add(p24);
    }

    /**
     * 从服务端获取通讯录信息
     */
    private void getData() {
        GetDataAction action = new GetDataAction();
        String data = action.getContactsData("getContactsData", "username");
    }
}
