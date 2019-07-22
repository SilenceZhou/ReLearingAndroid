package com.silencezhou.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


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
    private String TAG = "SplashActivity.TAG";

    // 成员变量命名推荐添加m，向系统命名靠拢
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
                case ENTER_HOME: // 跳转主界面
                    enterHome();
                    break;
                case URL_ERROR:

                    ToastUtils.show(getApplicationContext(), "url 异常");
                    enterHome();
                    break;
                case IO_ERROR:

                    ToastUtils.show(getApplicationContext(), "IO 异常");
                    enterHome();
                    break;
                case JSON_ERROR:

                    ToastUtils.show(getApplicationContext(), "JSON解析异常");
                    enterHome();
                    break;
            }
        }
    };
    private String mVersionDes;
    private String mDownloadUrl;

    private void showUpdateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("是否更新");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(mVersionDes);

        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 下载更新
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });

//        builder.setCancelable(true); // 加了这一句就IO异常了
        builder.setCancelable(false);

        builder.show();

    }

    public void downloadApk() {

        // 参考链接：https://blog.csdn.net/tyk9999tyk/article/details/53306035

        // apk 下载地址，放置apk的所在路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取sd路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mobilesafe.apk";

            // 发送请求 获取apk 并放到指定路径
            System.out.println("=======" + mDownloadUrl);

            RequestParams params = new RequestParams(mDownloadUrl);


            // 为RequestParams设置文件下载后的保存路径
            params.setSaveFilePath(path);
            // 下载完成后自动为文件命名
            params.setAutoRename(true);

            x.http().get(params, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                    Log.i(TAG, "onSuccess: ");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.i(TAG, "onError: ");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.i(TAG, "onCancelled: ");
                }

                @Override
                public void onFinished() {
                    Log.i(TAG, "onFinished: ");
                }

                @Override
                public void onWaiting() {
                    Log.i(TAG, "onWaiting: ");
                }

                @Override
                public void onStarted() {
                    Log.i(TAG, "onStarted: ");
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    double progress = current / (double)total;
                    Log.i(TAG, "onLoading: 下载百分比" + progress * 100 + "%%");
                }
            });
        }


    }

    /**
     * 进入主页
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // 关闭主界面
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果不初始化会报错
        // java.lang.NullPointerException: Attempt to invoke interface method
        // 'org.xutils.common.task.AbsTask org.xutils.common.TaskController.start(org.xutils.common.task.AbsTask)'
        // on a null object reference

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

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
        final long startTime = System.currentTimeMillis();


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

                        Log.i(TAG, jsonString);

                        // json 解析
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONObject dictionary = jsonObject.getJSONObject("data");

                        String versionName = dictionary.getString("versionName");
                        mVersionDes = dictionary.getString("versionDes");
                        String versionCode = dictionary.getString("versionCode");
                        mDownloadUrl = dictionary.getString("downloadUrl");

                        Log.i(TAG, "versionName : "+versionName);
                        Log.i(TAG, "versionDes : "+ mVersionDes);
                        Log.i(TAG, "versionCode : "+versionCode);
                        Log.i(TAG, "mDownloadUrl : "+ mDownloadUrl);


                        // 比对版本号 (服务器版本号>本地版本号，提示用户更新)
                        if (mLocalVersionCode < Integer.parseInt(versionCode)) {

                            // 提示用户更新
                            msg.what = UPDATE_VERSION;

                        } else {

                            // 进入用户主界面
                            msg.what = ENTER_HOME;

                        }

                    } else {
                        Log.i(TAG, "请求失败。。。。");


                    }

                } catch (MalformedURLException e) {
                    System.out.println("url异常==============");
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
                } finally {

                    long endTime = System.currentTimeMillis();
                    /// 强制执行4秒后发送消息
                    if (endTime - startTime < 4000) {
                        try {
                            Thread.sleep(4000-(endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
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
