package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.List;
import java.util.Map;

public class CustomFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    private static final String TAG = "FirebaseMsgService";
    String text, title;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = remoteMessage.getData().toString();
        Log.d("ResponseData onMessageReceived", message);
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor save = auto.edit();
        int a = auto.getInt("count", 0);
        save.putInt("count", a+1);
        Log.d("Responsedata count", a+"");
        save.commit();
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        title = remoteMessage.getData().get("title");
        text = remoteMessage.getData().get("message");
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("홍순범홍순범홍순범홍순범홍순범홍순범홍순범홍순범")
                .setContentText(dataMap.get("message"))
                .setAutoCancel(true)
                .setLights(000000255,500,2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0 , notificationBuilder.build());*/
       /* String channel  = "SCOS";
        String channel_nm = "채널명";
        NotificationManager notichannel = (android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,android.app.NotificationManager.IMPORTANCE_DEFAULT);
        channelMessage.setDescription("설명");
        channelMessage.enableLights(true);
        channelMessage.enableVibration(true);
        channelMessage.setShowBadge(false);
        notichannel.createNotificationChannel(channelMessage);
        NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this,channel)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(text)
        .setChannelId(channel)
                .setDefaults(Notification.FLAG_NO_CLEAR);
        //.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor save = auto.edit();
        int a = auto.getInt("count", 0);
        save.putInt("count", a+1);
        Log.d("Responsedata count", a+"");
        save.commit();//}
       /* else{
            String channel  = "채널";
            String channel_nm = "채널명";
            NotificationManager notichannel = (android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("설명");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            notichannel.createNotificationChannel(channelMessage);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this,channel)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("홍순범홍순범ㅎㅅㅂㅎㅅㅂㅎㅅㅂㅎㅄㅂㅎㅄㅂㅎㅅㅂㅎㅄㅂㅎㅅ")
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notificationBuilder.build());
        }*/

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "scos";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.scos_mark6)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000,1000})
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(0, notificationBuilder.build());
        }
        if(isAppRunning(getApplicationContext())) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Context mContext = new MainActivity2();
                    AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                    ad.setTitle("알람 발생");
                    ad.setMessage(text);
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.show();
                }
            },0);

        }
    }


    private boolean isAppRunning(Context context){
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            if(procInfos.get(i).processName.equals(context.getPackageName())){
                return true;
            }
        }
        return false;
    }

    }


