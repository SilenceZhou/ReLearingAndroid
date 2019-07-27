package com.silencezhou.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        // siv_update返回出 为 null， 一定是自定义View的时候三个方法出错
        // 参考链接： https://blog.csdn.net/ITermeng/article/details/52034186
        siv_update.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                System.out.println("我点击了");
            }
        });
    }
}
