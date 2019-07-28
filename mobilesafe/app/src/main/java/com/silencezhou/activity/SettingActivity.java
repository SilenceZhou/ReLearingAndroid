package com.silencezhou.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);

        // 获取已有的开关状态，用作显示
        boolean open_update = SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        siv_update.setCheck(open_update);


        // siv_update返回出 为 null， 一定是自定义View的时候三个方法出错
        // 参考链接： https://blog.csdn.net/ITermeng/article/details/52034186
        siv_update.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                boolean check = siv_update.isCheck();
                siv_update.setCheck(!check);
                SpUtils.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE, !check);


            }
        });
    }
}
