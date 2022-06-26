package com.example.uitest;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class App extends Application {

    public void creatSdcard(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File storage = Environment.getExternalStorageDirectory();
                    File temple = new File(storage.getPath());
                    if (! temple.exists()) {
                        temple.mkdirs();
                    }
                    File file1=new File(temple,"test.txt");
                    FileOutputStream fileOutputStream = null;
                    if (!file1.exists()){
                        try {
                            file1.createNewFile();
                            fileOutputStream = new FileOutputStream(file1);
                            fileOutputStream.write("true".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }}}}
    public void writeSdcard(String s)  {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File storage = Environment.getExternalStorageDirectory();
                    File temple = new File(storage.getPath());
                    if (! temple.exists()) {
                        temple.mkdirs();
                    }
                    File file1=new File(temple,"test.txt");
                    FileOutputStream fileOutputStream = null;
                    if (!file1.exists()){
                        try {
                            file1.createNewFile();
                            fileOutputStream = new FileOutputStream(file1);
                            fileOutputStream.write("true".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        fileOutputStream = new FileOutputStream(file1);
                        fileOutputStream.write(s.getBytes());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }}}
    public String readSdcard() {
        String s = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                InputStream inputStream = null;
                Reader reader = null;
                BufferedReader bufferedReader = null;
                try {
                    File storage = Environment.getExternalStorageDirectory();
                    File temple = new File(storage.getPath());
                    File file=new File(temple, "test.txt");
                    inputStream = new FileInputStream(file);
                    reader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(reader);
                    StringBuilder result = new StringBuilder();
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {
                        s= String.valueOf(result.append(temp));
                    }
//                    Log.i("MainActivity", "result:" + result);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        return s;
    }
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                    readSdcard();
                } else {
                    Toast.makeText(this, "授权被拒绝！", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}

