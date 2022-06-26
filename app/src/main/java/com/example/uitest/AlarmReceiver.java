package com.example.uitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
public class AlarmReceiver extends BroadcastReceiver {
    App app=new App();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(app.readSdcard().equals("false")){
            Log.d("--ddd--", app.readSdcard());
            Intent i = new Intent(context, PushService.class);
            context.startService(i);
        }}
}