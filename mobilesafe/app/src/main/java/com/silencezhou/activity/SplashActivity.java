package com.silencezhou.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SplashActivity extends AppCompatActivity {

    private TextView textView;
    private int mLocalVersionCode;
    private String tag = "SplashActivity.tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // 初始化UI
        initUI();

        // 初始化数据
        initData();
    }


    /**
     * 添加文档注释方法： https://www.jianshu.com/p/9dd8486a35ba
     * 初始化数据
     */
    private void initData() {

        textView.setText("版本名称: " +  getVersionName());

        // 检测是否有新版本  成员变量 一般在前面添加m
        // 获取本地版本号
        mLocalVersionCode = getVersionCode();
        // 获取服务器版本号

        checkVersion();


    }

    private void checkVersion() {

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://easy-mock.com/mock/5d33c654e9c63f438f2cdd28/example/update.htm");

                    // 开启一个连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    //urlConnection.setDoOutput(true);
                    // 设置请求头
//                    urlConnection.setConnectTimeout(200); //请求超时
//                    urlConnection.setReadTimeout(200); // 读取超时
//                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type" , "application/json");
//                    urlConnection.setRequestProperty("Accept-Charset", "utf-8");

                    // 获取响应吗
                    if (urlConnection.getResponseCode() == 200) {

                        /// 以流的形式
                        InputStream is = urlConnection.getInputStream();

                        // 将流转换成字符串
                        String jsonString = StreamUtil.stream2String(is);

                        System.out.println("=====================");

                        Log.i(tag, jsonString);

                    } else {
                        Log.i(tag, "请求失败。。。。");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /// 实现这个接口的对象
//        new  Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

    }

    /**
     * 获取版本号
     */
    private int getVersionCode() {

        // 1. 包管理者对象
        PackageManager pm = getPackageManager();

        // 2. 获取基本信息， 设置0表示获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            // 获取版本名称
            return packageInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取版本名称： 清单文件中 AndroidManifest.xml
     *
     * @return 应用版本名称， 返回null代表返回异常
     */
    private String getVersionName() {

        // 1. 包管理者对象
        PackageManager pm = getPackageManager();

        // 2. 获取基本信息， 设置0表示获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            // 获取版本名称
            return packageInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        /// 快捷键 ：  alt + cmd + v  自动填充返回值
        textView = (TextView) findViewById(R.id.tc_version_name);
    }

}
