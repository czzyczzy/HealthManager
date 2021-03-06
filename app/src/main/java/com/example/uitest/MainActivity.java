package com.example.uitest;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import android.Manifest;
import android.os.Message;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimeZone;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private BodyStateFragment firstFragment = null;// ??????????????????????????????
    private RecipeRecommended secondFragment = null;// ????????????????????????
    private SelfFragment thirdFragment = null;// ????????????????????????

    private View firstLayout = null;// ????????????????????????
    private View secondLayout = null;// ??????????????????
    private View ThirdLayout = null;// ??????????????????

    private ImageView stateImg = null;
    private ImageView recommend = null;
    private ImageView selfImg = null;
    public static SQLiteDatabase db2;

    private TextView stateText = null;
    private TextView  recommendText = null;
    private TextView selfText = null;

    private FragmentManager fragmentManager = null;// ?????????Fragment????????????

    static String[] permissions={
            "android.permission.SEND_SMS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.ACCESS_NOTIFICATION_POLICY"
    };
    DBclass dbsqLiteOpenHelper ;
    //final SQLiteDatabase db1 = dbsqLiteOpenHelper.getWritableDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.startService(new Intent(this,PushService.class));
        checkPermission();
        //?????????
        dbsqLiteOpenHelper = new DBclass(this,"Ding.db",null,3);
        final SQLiteDatabase db1 = dbsqLiteOpenHelper.getWritableDatabase();
        db1.delete("BaseDatas", "CreatTime=?", new String[]{"2022-06-24 06:00:00"});
        db2=db1;
        for(int i =0;i<=4;i++){
            ContentValues values = new ContentValues();
            values.put("id",i);
            values.put("BeatRate",62);
            values.put("HighPressure",120);
            values.put("LowPressure",80);
            values.put("BloodGlucose",9);
            values.put("BloodOxygen",100);
            values.put("CreatTime","2022-06-24 06:00:00");
            db1.insert("BaseDatas",null,values);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);//??????????????????title
        setContentView(R.layout.activity_main);
        // ?????????????????????
        initViews();
        fragmentManager = getFragmentManager();//?????????Fragment????????????
        // ???????????????????????????
        setTabSelection(0);


        //????????????
        /*List<String> list=new ArrayList<>();
        list.clear();
        for(int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(this,permissions[i])!= PackageManager.PERMISSION_GRANTED){
                list.add(permissions[i]);
            }
        }
        if(list.isEmpty()){
            *//*String[] needPP={"android.permission.ACCESS_NOTIFICATION_POLICY"};
            ActivityCompat.requestPermissions(this,needPP,2);*//*
        }else{
            String[] needP=list.toArray(new String[list.size()]);
            ActivityCompat.requestPermissions(this,needP,1);
        }
        getDoNotDisturb();*/
    }

    /**
     * ???????????????
     */
    private void initViews() {
        fragmentManager = getFragmentManager();
        firstLayout = findViewById(R.id.state_layout);
        secondLayout = findViewById(R.id.recipe_layout);
        ThirdLayout = findViewById(R.id.self_layout);

        stateImg = (ImageView) findViewById(R.id.state_img);
        recommend = (ImageView) findViewById(R.id.recipe_img);
        selfImg = (ImageView) findViewById(R.id.self_img);

        stateText = (TextView) findViewById(R.id.state_text);
        recommendText = (TextView) findViewById(R.id.recipe_text);
        selfText = (TextView) findViewById(R.id.self_text);

        //??????????????????
        firstLayout.setOnClickListener(this);
        secondLayout.setOnClickListener(this);
        ThirdLayout.setOnClickListener(this);
    }

    /**
     * ????????????????????????fragment
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.state_layout:
                setTabSelection(0);// ?????????????????????????????????1???tab
                break;
            case R.id.recipe_layout:
                setTabSelection(1);// ?????????????????????????????????2???tab
                break;
            case R.id.self_layout:
                setTabSelection(2);// ?????????????????????????????????3???tab
                break;
            default:
                break;
        }
    }

    /**
     * ???????????????index????????????????????????tab??? ??????tab?????????????????????0?????????????????????1???????????????2????????????
     */
    private void setTabSelection(int index) {
        clearSelection();// ???????????????????????????????????????????????????
        FragmentTransaction transaction = fragmentManager.beginTransaction();// ????????????Fragment??????
        hideFragments(transaction);// ?????????????????????Fragment?????????????????????Fragment???????????????????????????
        switch (index) {
            case 0:
                // ????????????????????????tab???????????????????????????????????????
                stateImg.setImageResource(R.drawable.ic_menu_state_tab);//????????????????????????
                stateText.setTextColor(Color.parseColor("#0090ff"));//??????????????????

                if (firstFragment == null) {
                    /*????????????activity?????????????????????*//*
                    Intent intent = getIntent();
                    String number = intent.getStringExtra("weixin_number");*/
                    // ??????FirstFragment?????????????????????????????????????????????
                    firstFragment = new BodyStateFragment();
                    transaction.add(R.id.fragment, firstFragment);

                } else {
                    // ??????FirstFragment???????????????????????????????????????
                    transaction.show(firstFragment);//???????????????
                }
                break;
            // ?????????firstFragment??????
            case 1:
                recommend.setImageResource(R.drawable.ic_menu_recipe_tab);
                recommendText.setTextColor(Color.parseColor("#0090ff"));
                if (secondFragment == null) {
                    secondFragment = new RecipeRecommended();
                    transaction.add(R.id.fragment, secondFragment);
                } else {
                    transaction.show(secondFragment);
                }
                break;
            case 2:
                selfImg.setImageResource(R.drawable.ic_menu_self_tab);
                selfText.setTextColor(Color.parseColor("#0090ff"));
                if (thirdFragment == null) {
                    thirdFragment = new SelfFragment();
                    transaction.add(R.id.fragment, thirdFragment);
                } else {
                    transaction.show(thirdFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * ????????????Fragment???????????????????????? ?????????Fragment?????????????????????
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }
        if (secondFragment != null) {
            transaction.hide(secondFragment);
        }
        if (thirdFragment != null) {
            transaction.hide(thirdFragment);
        }

    }

    /**
     * ??????????????????????????????
     */
    private void clearSelection() {
        stateImg.setImageResource(R.drawable.ic_menu_state);
        stateText.setTextColor(Color.parseColor("#82858b"));

        recommend.setImageResource(R.drawable.ic_menu_recipe);
        recommendText.setTextColor(Color.parseColor("#82858b"));

        selfImg.setImageResource(R.drawable.ic_menu_self);
        selfText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * ????????????
     */

    private void alarmAlert(){
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
        VibrateUtils.vibrate(MainActivity.this,1000);
        AudioManager localAudioManager = (AudioManager) getSystemService(MainActivity.this.AUDIO_SERVICE);
        int currentMode = localAudioManager.getRingerMode();
        if(currentMode==0|localAudioManager.getRingerMode()==1){
            localAudioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_UNMUTE,AudioManager.USE_DEFAULT_STREAM_TYPE,0);
        }
        int currentVolume = localAudioManager.getStreamVolume(AudioManager.STREAM_RING);//??????????????????
        MediaUtils.maxRing(localAudioManager);
        MediaUtils.playRing(MainActivity.this);
        WaringDialog.dialog(MainActivity.this,localAudioManager,currentVolume);
        View view = View.inflate(MainActivity.this,R.layout.self_info,null);
        EditText contact = view.findViewById(R.id.contact);
        String number = contact.getText().toString();
        Alarming.alarm(number);
//            }
//        });


    }

    /**
     * ?????????????????????
     */
    private void getDoNotDisturb(){

        NotificationManager notificationManager =
                (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("???????????????????????????????????????????????????????????? ????????????");
            builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent1 = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    finish();
                    MainActivity.this.startActivity(intent1);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
                {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH)
                    {
                        return true;
                    }
                    else
                    {
                        return false; //???????????? false
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();
            builder.show().getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        }
    }

    /**
     * ???????????????????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("???????????????????????????????????????????????????????????????????????? ????????????");
                        builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent2 = new Intent();
                                intent2.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                                intent2.setData(Uri.parse("package:" + getPackageName()));
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                finish();
                                startActivity(intent2);
                            }
                        });
                        builder.setNegativeButton("??????", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
                            {
                                if (keyCode == KeyEvent.KEYCODE_SEARCH)
                                {
                                    return true;
                                }
                                else
                                {
                                    return false; //???????????? false
                                }
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                        builder.show().getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    }
                }
        }
    }
//    static String readMessage;
//
//
//    public static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    byte[] readBuf = (byte[]) msg.obj;
//                    readMessage= new String(readBuf, 0, readBuf.length);
//                    // ReadCdata(readMessage);
//                    String[] textMany = readMessage.split("-");
//
//
//                    break;
//            }
//
//        }
//    };
    public static   void ReadCdata(String AA){
        String[] g = AA.split("-");
        ContentValues values = new ContentValues();
        if(g[0]!=null)
            System.out.println(g[0]);
            values.put("BeatRate",Integer.valueOf(g[0]));
        if(g[1]!=null)
            values.put("HighPressure",Integer.valueOf(g[1]));
        if(g[2]!=null)
            values.put("LowPressure",Integer.valueOf(g[1]));
        if(g[3]!=null)
            values.put("BloodGlucose",Integer.valueOf(g[2]));
        if(g[4]!=null)
            values.put("BloodOxygen",Integer.valueOf(g[3]));

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String hour;
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH))+1;
        String day = String.valueOf(cal.get(Calendar.DATE));
        if (cal.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else

            hour = String.valueOf(cal.get(Calendar.HOUR)+12);
        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        String second = String.valueOf(cal.get(Calendar.SECOND));
        values.put("CreatTime",year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
        db2.insert("BaseDatas",null,values);
    }
    static String readMessage;
    public static TextView textView3;
    public static Handler handler = new Handler() {


        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    System.out.println(msg.obj);
//                    byte[] readm =(byte[])msg.obj;
//                    String readMessage = new String(readm,0, msg.arg1);
                    System.out.println(msg.obj.toString());
                    //readMessage=new String((byte[])msg.obj,0, msg.arg1);
                    //textView3.setText(msg.arg1);
                    ReadCdata(msg.obj.toString());
                    //MakeScores(db2);
                    break;
                case 2:
                    //textView3.setText("????????????");
                    break;
                case 3:


            }
        }
    };
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    public void checkPermission() {
        //???????????????NEED_PERMISSION?????????????????? PackageManager.PERMISSION_GRANTED??????????????????
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
            //????????????
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
        }
    }
}
