package com.example.tabbardemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/// 参考链接 ： https://www.jianshu.com/p/0b9d5777abba
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        findViewById(R.id.bottom_tab_layout).setOnClickListener(this);
        Button btn = (Button) findViewById(R.id.center_btn);

    }

    public void centerbtnclick(View v) {

        System.out.println("按钮点击。。。。。。。");
        Intent intent = new Intent(this,BottomTabLayoutActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onClick(View v) {
//
//        System.out.println("按钮点击。。。。。。。");
//        Intent intent = new Intent(this,BottomTabLayoutActivity.class);
//        startActivity(intent);
//    }
}
