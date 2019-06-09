package com.example.callphone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);

        // Button Click
        /// way 1 - 重写 View.OnClickListener
        //  btn.setOnClickListener(new MyClickListner());

        // way 2 - 匿名内部类
//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                callPhone();
//            }
//        });

        /// way3 --- 适合于一个方法多个事件 - 当前类实现 View.OnClickListener接口类型
        //        btn.setOnClickListener(this);

        /// way4 xml 里面设置 onClick="" 就好


    }


    /// way4
    public void testOnclick(View v) {

        Intent intent = new Intent(MainActivity.this,PageTwoActivity.class);

        startActivity(intent);


    }


    @Override
    public void onClick(View v) {
        /// 判断点击是哪个方法

        switch (v.getId()) {
            case R.id.button:
                callPhone();
                break;
//            case R.id.button:
//                break;
            default:
                break;
        }

    }

    private class MyClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            callPhone();


        }
    }

    private void callPhone() {
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
