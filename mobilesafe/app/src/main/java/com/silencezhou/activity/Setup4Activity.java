package com.silencezhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

class Setup4Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    public void nextPage(View view){

        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
        startActivity(intent);
        finish();

        SpUtils.putBoolean(this, ConstantValue.SETUP_OVER, true);

    }

    public void prePage(View view){

        Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
        startActivity(intent);
        finish();

    }
}
