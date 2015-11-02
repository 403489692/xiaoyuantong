package com.jlstudio.iknow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jlstudio.R;
import com.jlstudio.main.activity.MainActivity;
import com.jlstudio.main.application.Config;
import com.jlstudio.main.bean.User;


/**
 * Created by gzw on 2015/9/26.
 */
public class LoginQuery extends Dialog implements View.OnClickListener {
    private Context context;
    private Button sure,cancle;
    private User user;

    public LoginQuery(Context context,User user) {
        super(context);
        this.context = context;
        this.user = user;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_login_query);
        initView();
    }
    private void initView() {
        Window dialogwindow = getWindow();
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = Config.loadDisplay(context);
       // if(width <= 480){
            lp.width = (int) (dm.widthPixels * 0.8);
            lp.height = (int) (dm.heightPixels * 0.45);
        dialogwindow.setAttributes(lp);

        sure = (Button) findViewById(R.id.sure);
        cancle = (Button) findViewById(R.id.cancle);
        sure.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                Config.saveBaseUser(context,user);
                context.startActivity(new Intent(context, MainActivity.class));
                dismiss();
                break;
            case R.id.cancle:
                Config.saveUser(context, user);
                Intent intent = new Intent(context, MainActivity.class);
                intent.setAction("action");
                context.startActivity(intent);
                dismiss();
                break;
        }
    }
}
