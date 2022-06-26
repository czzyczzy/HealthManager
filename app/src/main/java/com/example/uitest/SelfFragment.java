package com.example.uitest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

public class SelfFragment extends Fragment implements OnChangedListener{
    final App app=new App();
    private static final int requestCode = 2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_fragment, container, false);

        //个人信息点击事件
        TextView selfInfo = (TextView) view.findViewById(R.id.self_info);
        selfInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"个人信息",Toast.LENGTH_SHORT).show();
                selfInfo.setPressed(false);
                Intent intent = new Intent(getActivity(),SelfInfo.class);
                startActivity(intent);
                //startActivityForResult(intent,requestCode);
            }
        });
        //收藏点击事件
        TextView collections = (TextView) view.findViewById(R.id.collections);
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"没有",Toast.LENGTH_SHORT).show();
            }
        });
        //关于点击事件
        TextView aboutUs = (TextView) view.findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"这是个设计",Toast.LENGTH_SHORT).show();
            }
        });
        //设置点击事件
        TextView settings = (TextView) view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"暂不开放",Toast.LENGTH_SHORT).show();
            }
        });
        SlipButton myBtn =(SlipButton) view.findViewById(R.id.slipBtn);
        myBtn.SetOnChangedListener(this);
        return view;
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 请求码                          返回码
        if(requestCode==this.requestCode&&resultCode==SelfInfo.resultCode){
            //data就是上一个Activity调用setResult方法时传递过来的Intent
            Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();
        }
    }*/
    public void OnChanged(boolean CheckState) {//当按钮状态被改变时
        // TODO Auto-generated method stub
//        Log.d("--22222--", String.valueOf(CheckState));
        app.writeSdcard(String.valueOf(CheckState));
        if(CheckState){
//            Log.d("--ssss--", app.readSdcard());
//            this.stopService(new Intent(this,PushService.class));
            Toast.makeText(getActivity(),"打开了..." , Toast.LENGTH_SHORT).show();}
        else {
//            Log.d("--ssss--",app.readSdcard());
            getActivity().startService(new Intent(getActivity(),PushService.class));
            Toast.makeText(getActivity(),"关闭了..." , Toast.LENGTH_SHORT).show();}
    }
}
