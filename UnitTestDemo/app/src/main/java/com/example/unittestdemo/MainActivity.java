package com.example.unittestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private  static  final String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.v(tag, "我是v级别");
        Log.i(tag, "我是info级别");
        Log.d(tag, "我是debug级别");
        Log.w(tag, "我是warn级别");
        Log.e(tag, "我是error级别");



    }
    public void show(){
        int a = 11;
        int b = 12;
        System.out.print("a+b=" + (a+b));
    }

}
