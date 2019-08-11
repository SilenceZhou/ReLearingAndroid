package com.silencezhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

class Setup3Activity extends Activity {

    private EditText et_phone_number;
    private Button bt_select_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    private void initUI() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        bt_select_number = (Button) findViewById(R.id.bt_select_number);

        /// 点击选中联系人对话框
        bt_select_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    /// 返回到当前界面接受结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            String phone = data.getStringExtra("phone");
            System.out.println("设置三界面获取的值" + phone);
            et_phone_number.setText(phone);

            // 存储联系人值plist中
            SpUtils.putString(getApplicationContext(), ConstantValue.CONTANT_PHONE, phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextPage(View view){

        Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
        startActivity(intent);
        finish();

    }

    public void prePage(View view){

        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();

    }
}
