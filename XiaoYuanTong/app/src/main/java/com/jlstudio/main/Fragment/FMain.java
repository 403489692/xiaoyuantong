package com.jlstudio.main.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jlstudio.R;
import com.jlstudio.iknow.activity.CETScoreAty;
import com.jlstudio.iknow.activity.ScheduleAty;
import com.jlstudio.iknow.activity.ScoreAty;
import com.jlstudio.iknow.activity.ShowQuestionAty;
import com.jlstudio.iknow.dialog.QueryDialog;
import com.jlstudio.iknow.dialog.QueryScoreDialog;
import com.jlstudio.main.activity.LoginAty;
import com.jlstudio.main.application.Config;
import com.jlstudio.publish.activity.PublishDatasAty;
import com.jlstudio.publish.util.StringUtil;
import com.jlstudio.swzl.activity.LostAndFound;

/**
 * Created by gzw on 2015/10/14.
 */
public class FMain extends Fragment implements ViewTreeObserver.OnPreDrawListener, View.OnClickListener {
    private View view;
    private int width = 0;
    private boolean onlyOne;//控件大小只检查一次
    private ViewTreeObserver observer;
    private Animation operatingAnim;
    private LinearLayout clickLayout;
    private Button func1, func2, func3, func4, func5, func6, func7, func8;
    private LinearLayout fun1, fun2, fun3, fun4, fun5, fun6, fun7, fun8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        observer = view.getViewTreeObserver();
        operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.main_item_ani);
        operatingAnim.setAnimationListener(new MyAnimationListener());
        observer.addOnPreDrawListener(this);//监听view绘制事件
        initView();
        return view;
    }

    private void initView() {
        //文字
        func1 = (Button) view.findViewById(R.id.func1);
        func2 = (Button) view.findViewById(R.id.func2);
        func3 = (Button) view.findViewById(R.id.func3);
        func4 = (Button) view.findViewById(R.id.func4);
        func5 = (Button) view.findViewById(R.id.func5);
        func6 = (Button) view.findViewById(R.id.func6);
//        func7 = (Button) view.findViewById(R.id.func7);
//        func8 = (Button) view.findViewById(R.id.func8);
        func1.setOnClickListener(this);
        func2.setOnClickListener(this);
        func3.setOnClickListener(this);
        func4.setOnClickListener(this);
        func5.setOnClickListener(this);
        func6.setOnClickListener(this);
//        func7.setOnClickListener(this);
//        func8.setOnClickListener(this);
        setText();

        //文字背景
        fun1 = (LinearLayout) view.findViewById(R.id.fun1);
        fun2 = (LinearLayout) view.findViewById(R.id.fun2);
        fun3 = (LinearLayout) view.findViewById(R.id.fun3);
        fun4 = (LinearLayout) view.findViewById(R.id.fun4);
        fun5 = (LinearLayout) view.findViewById(R.id.fun5);
        fun6 = (LinearLayout) view.findViewById(R.id.fun6);
//        fun7 = (LinearLayout) view.findViewById(R.id.fun7);
//        fun8 = (LinearLayout) view.findViewById(R.id.fun8);

    }

    private void setText() {
        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iconfont.ttf");
        func1.setTypeface(iconfont);
        func2.setTypeface(iconfont);
        func3.setTypeface(iconfont);
        func4.setTypeface(iconfont);
        func5.setTypeface(iconfont);
        func6.setTypeface(iconfont);
//        func7.setTypeface(iconfont);
//        func8.setTypeface(iconfont);
    }

    private void setTextSize() {
        func1.setTextSize(width / 3);
        func2.setTextSize(width / 3);
        func3.setTextSize(width / 3);
        func4.setTextSize(width / 3);
        func5.setTextSize(width / 3);
        func6.setTextSize(width / 3);
//        func7.setTextSize(width / 3);
//        func8.setTextSize(width / 3);
    }

    @Override
    public boolean onPreDraw() {
        if (!onlyOne) {
            width = func1.getMeasuredWidth();
            setTextSize();
            onlyOne = true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.func1:
                clickLayout = fun1;
                startAnima(fun1);
                break;
            case R.id.func2:
                clickLayout = fun2;
                startAnima(fun2);
                break;
            case R.id.func3:
                clickLayout = fun3;
                startAnima(fun3);
                break;
            case R.id.func4:
                clickLayout = fun4;
                startAnima(fun4);
                break;
            case R.id.func5:
                clickLayout = fun5;
                startAnima(fun5);
                break;
            case R.id.func6:
                clickLayout = fun6;
                startAnima(fun6);
                break;
//            case R.id.func7:
//                clickLayout = fun7;
//                startAnima(fun7);
//                break;
//            case R.id.func8:
//                clickLayout = fun8;
//                startAnima(fun8);
//                break;
        }
    }
    private void startAnima(Button button){
        //LinearInterpolator lin = new LinearInterpolator();
        //operatingAnim.setInterpolator(lin);
        //main.setAnimation(operatingAnim);
        button.startAnimation(operatingAnim);
    }
    private void startAnima(LinearLayout layout){
        //LinearInterpolator lin = new LinearInterpolator();
        //operatingAnim.setInterpolator(lin);
        //main.setAnimation(operatingAnim);
        layout.startAnimation(operatingAnim);
    }
    private class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(clickLayout == fun1){
                if(StringUtil.isEmpty(Config.loadUser(getActivity()).getUsername())){
                    startActivity(new Intent(getActivity(), LoginAty.class));
                }else{
                    startActivity(new Intent(getActivity(), PublishDatasAty.class));
                }
            }else if(clickLayout == fun2){
                startActivity(new Intent(getActivity(), LostAndFound.class));
            }else if(clickLayout == fun3){
                new QueryScoreDialog(getActivity(),1).show();
                //startActivity(new Intent(getActivity(), ScheduleAty.class));
            }else if(clickLayout == fun4){
                new QueryScoreDialog(getActivity(),0).show();
               // startActivity(new Intent(getActivity(), ScoreAty.class));
            }else if(clickLayout == fun5){
                new QueryDialog(getActivity()).show();
                //startActivity(new Intent(getActivity(), CETScoreAty.class));
            }else if(clickLayout == fun6){}

//            }else if(clickLayout == fun7){
//
//            }else if(clickLayout == fun8){
//
//            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
