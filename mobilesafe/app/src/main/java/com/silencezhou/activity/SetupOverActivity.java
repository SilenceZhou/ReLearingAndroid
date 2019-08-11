package com.silencezhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

class SetupOverActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean setup_over = SpUtils.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            // 设置密码成功 并且四个导航界面设置完成 => 停留在设置完成功能列表界面
            setContentView(R.layout.activity_setup_over);

        } else {
            // 密码输入成功，四个导航界面没有设置完成 => 跳转到第一个界面

            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);

            /// 开启一个新的界面后，关闭功能列表界面
            finish();
        }

        initUI();

    }

    private void initUI() {
        
    }
}
