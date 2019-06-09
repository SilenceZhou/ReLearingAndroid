package com.example.callphone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);

        /// 第一个点击方法
        btn.setOnClickListener(new MyClickListner());

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }



    private class MyClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            EditText editText = (EditText) findViewById(R.id.editText);
            String number = editText.getText().toString().trim();

            // Android对权限越来越看重了
            // 直接拨打电话 -- 需要手动去开启 在手机的设置->授权管理->应用权限管理中，找到要测试的APP，点击该APP之后，点击“电话”，然后选择“允许”。
            Intent intent = new Intent(Intent.ACTION_CALL);
            // 跳转到拨号界面拨打
//             Intent intent = new Intent(Intent.ACTION_DIAL);

            intent.setData(Uri.parse("tel:"+number));

            // 用模拟器会奔溃
            // java.lang.SecurityException: Permission Denial: starting Intent
            startActivity(intent);


        }
    }
}
