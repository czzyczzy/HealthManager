package com.example.uitest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Objects;


public class PushService extends Service {
    public int choice=1000;
    final App app=new App();
    private String title="您有新消息";
    private String text="这是一条新的测试消息";
    public Thread mThread;
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        Log.d("--create--","service is created");
        app.creatSdcard();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("--onStartCom--","started");
        new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {//可以在该线程中做需要处理的事
                show();
                stopSelf();//关闭服务
            }
        }).start();
        if(Objects.equals(app.readSdcard(), "false")) {
            AlarmManager manger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(this, AlarmReceiver.class);//广播接收
            //PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity2_Text.this, 0, intent, 0);//意图为开启活动
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);//意图为开启广播
            long triggerAtTime = SystemClock.elapsedRealtime();//开机至今的时间毫秒数
            triggerAtTime = triggerAtTime + 10 * 1000;//比开机至今的时间增长10秒
            manger.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);//设置为开机至今的模式，时间，PendingIntent
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public String setTitle(String title){
        this.title=title;
        return  title;
    }
    public String setText(String text){
        this.text=text;
        return title;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show(){
        // 获取NotificationManager对象
        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String id = "channel_1";
        String name = getString(R.string.app_name);
        // 创建NotificationChannel对象，传入id name 和 重要级别
        NotificationChannel notificationChannel =
                new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        // 创建通知的通道
        manager.createNotificationChannel(notificationChannel);
        // 创建通知
        // Builder中传入上下文对象和通道id
        NotificationCompat.Builder builder1 = new NotificationCompat
                .Builder(this, id)
                .setContentText("hello world")
                .setContentTitle("This is a test")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        Intent intent = new Intent(this, MainActivity.class); //设置点击跳转的activity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        builder1.setContentIntent(pendingIntent);
        // 发送通知
        Notification notification1 = builder1.build();
        manager.notify(choice, notification1);
        choice++;
    }
}
