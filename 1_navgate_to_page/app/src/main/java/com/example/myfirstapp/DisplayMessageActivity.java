package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {


    public static final String TAG2 = "DisplayMessage:";
    /// cmd + shift + '-'折叠  /  '+' 展开

    /// 为什么导航栈再返回的时候回先销毁 然后重新创建试图呢？？？？？ --- iOS是一直存在导航栈中

    /// 系统创建Activity时，在这里创建视图 与 数据绑定
    /// 必须在此处调用 setContentView() 以定义活动用户界面的布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        /// Log: https://blog.csdn.net/ccpat/article/details/46363137
        Log.d(TAG2,"onCreate:");



        setTitle("我是第二个页面");


        Intent intent = getIntent();
        /// 接收参数
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }


    /// onCreate之后，Activity 进入开始状态
    /// 此回调包含活动最终准备到达前台并变为交互式的内容。
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG2, "onStart: ");
    }

    /// 系统在活动开始与用户交互之前
    /// 此时，活动位于活动堆栈的顶部，并捕获所有用户输入。
    /// 应用程序的大多数核心功能都是在该onResume()方法中实现的。
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG2, "onResume: ");
    }

    /// 当Activity失去焦点并进入暂停状态时， 系统会调用  ==> 下一个回调是onStop()或 onResume()
    /// 当用户点击“后退”或“最近”按钮时，会出现此状态。
    // 当系统调用 onPause()您的活动时，从技术上讲，您的活动仍然部分可见，
    // 但通常表示用户正在离开活动，活动很快将进入“已停止”或“已恢复”状态。
    /// 如果用户期望UI更新，则处于暂停状态的活动可以继续更新UI。
    ///
    /// 这种活动的示例包括示出导航地图屏幕或媒体播放器播放的活动。
    /// 即使这些活动失去焦点，用户也希望他们的UI继续更新。
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG2, "onPause: ");
    }



    /// 当Activity不再对用户可见时， 系统会调用 ==>下一个回调是 onRestart(),如果活动返回与用户交互，或者 onDestroy() 此活动是否完全终止。
    /// 这可能是因为Activity正在被破坏，新活动正在开始，或者现有活动正在进入恢复状态并且正在覆盖已停止的活动。在所有这些情况下，停止的活动根本不再可见。
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG2, "onStop: ");
    }
    /// 当处于“已停止”状态的活动即将重新启动时，系统将调用此回调。onRestart() 从停止时恢复活动状态。
    //
    //此回调始终紧随其后 onStart()。
    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG2, "onRestart: ");
    }

    /// 系统在销毁活动之前调用此回调
    /// 此回调是活动收到的最后一个回调。
    /// onDestroy()通常实现以确保在活动或包含它的进程被销毁时释放所有活动的资源。
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG2, "onDestroy: ");
    }
}
