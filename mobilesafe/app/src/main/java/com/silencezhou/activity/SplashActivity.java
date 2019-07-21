package com.silencezhou.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;

public class SplashActivity extends AppCompatActivity {

    /**
     * 更新新版本状态码
     */
    private static final int UPDATE_VERSION = 100;
    /**
     * 进入应用主界面状态码
     */
    private static final int ENTER_HOME = 101;
    /**
     * url错误
     */
    private static final int URL_ERROR = 102;
    /**
     * io错误
     */
    private static final int IO_ERROR = 103;
    /**
     * json解析错误
     */
    private static final int JSON_ERROR = 104;


    private TextView textView;
    private int mLocalVersionCode;
    private String tag = "SplashActivity.tag";

    // 成员变量命名推荐添加m，向系统命名靠拢
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    break;
                case ENTER_HOME: // 跳转主界面
                    break;
                case URL_ERROR:
                    break;
                case IO_ERROR:
                    break;
                case JSON_ERROR:
                    break;
            }
        }
    };



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

        final Message msg = Message.obtain();

        new Thread(){
            @Override
            public void run() {
                try {

                    // 封装url
                    URL url = new URL("https://easy-mock.com/mock/5d33c654e9c63f438f2cdd28/example/update.htm");

                    // 开启一个连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    // 设置请求头参数
                    // 请求超时
                    urlConnection.setConnectTimeout(200);
                    // 读取超时
                    urlConnection.setReadTimeout(200);
                    // 默认为GET请求
                    //urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type" , "application/json");
                    // 接收格式
                    // urlConnection.setRequestProperty("Accept-Charset", "utf-8");

                    // 获取响应吗
                    if (urlConnection.getResponseCode() == 200) {

                        /// 以流的形式
                        InputStream is = urlConnection.getInputStream();

                        // 将流转换成字符串
                        String jsonString = StreamUtil.stream2String(is);

                        Log.i(tag, jsonString);

                        // json 解析
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONObject dictionary = jsonObject.getJSONObject("data");

                        String versionName = dictionary.getString("versionName");
                        String versionDes = dictionary.getString("versionDes");
                        String versionCode = dictionary.getString("versionCode");
                        String downloadUrl = dictionary.getString("downloadUrl");

                        Log.i(tag, "versionName : "+versionName);
                        Log.i(tag, "versionDes : "+versionDes);
                        Log.i(tag, "versionCode : "+versionCode);
                        Log.i(tag, "downloadUrl : "+downloadUrl);



                        // 比对版本号 (服务器版本号>本地版本号，提示用户更新)
                        if (mLocalVersionCode < Integer.parseInt(versionCode)) {

                            // 提示用户更新
                            msg.what = UPDATE_VERSION;

                        } else {

                            // 进入用户主界面
                            msg.what = ENTER_HOME;

                        }

                    } else {
                        Log.i(tag, "请求失败。。。。");


                    }

                } catch (MalformedURLException e) {
                    // url 异常
                    e.printStackTrace();

                    // 进入用户主界面
                    msg.what = URL_ERROR;

                } catch (IOException e) {
                    // 数据流异常
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    // json 接卸异常
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
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
