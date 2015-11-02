package com.jlstudio.publish.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jlstudio.R;
import com.jlstudio.main.activity.MainActivity;
import com.jlstudio.publish.activity.ShowReceivePublishAty;
import com.jlstudio.publish.util.ShowToast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by gzw on 2015/10/25.
 */
public class PublishReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            Bundle bundle = intent.getExtras();
            ShowToast.show(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
            createNotification(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
        }
    }
    private void createNotification(Context context,String content){
        ShowToast.show(context, "创建notification");
        Intent intent  = new Intent(context, MainActivity.class);
        String title = null;
        try {
            JSONObject json = new JSONObject(content);
            title = json.getString("title");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("msg", content);
        intent.setAction("notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Message")
                .setContentText(title)
                .setTicker("通知来了")
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);

        Notification nf = builder.build();
        nm.notify(0,nf);
    }
}
