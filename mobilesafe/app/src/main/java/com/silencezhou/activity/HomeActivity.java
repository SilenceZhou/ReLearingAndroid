package com.silencezhou.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.Md5Utils;
import com.silencezhou.mobilesafe.utils.SpUtils;

import org.w3c.dom.Text;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mTitleArr;
    private int[] mPictureArr;

    /**
     * 检查是否已被授权危险权限
     *
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(Activity ac, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        System.out.println("requestCode =" + requestCode);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        String[] permissions = {Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE};

        if (!checkDangerousPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this,permissions, 1);
        }


        initUI();
        initData();
    }

    /**
     * 准备数据 文字、图片
     */
    private void initData() {
        mTitleArr = new String[]{
                "手机防盗",
                "通讯卫士",
                "软件管理",
                "进程管理",
                "流量统计",
                "手机杀毒",
                "缓存清理",
                "高级工具",
                "设置中心",};
        mPictureArr = new int[]{
                R.drawable.home_safe, R.drawable.home_callmsgsafe,
                R.drawable.home_apps, R.drawable.home_taskmanager,
                R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools,
                R.drawable.home_settings,};

        // 九宫格设置数据适配器
        gv_home.setAdapter(new MyAdapter());

        // grideView 跳转
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showDialog();
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        // 判断本地是否存储密码
        String psd = SpUtils.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            // 1、设置初始化密码
            showSetpsdDialog();
        } else {
            // 2、读取 初始密码
            showConfigPsdDialog();
        }


    }

    /**
     * 确认密码对话框
     */
    private void showConfigPsdDialog() {
// 需自定展示样式，所以需要调用alertDialog.setView();
        // view有自己编写的xml转换成view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_config_psd, null);
        alertDialog.setView(view);

        // 为了兼容低版本，给对话框布局的时候 去掉内边距（系统默认上下为2，在低版本系统里面）
//        alertDialog.setView(view,0,0,0,0);
        alertDialog.show();

        /// 这个地方注意要用View的findViewById, 不然会报错
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ed_config_psd = (EditText) view.findViewById(R.id.ed_config_psd);

                String config_psd = ed_config_psd.getText().toString();


                if (!TextUtils.isEmpty(config_psd)) {

                    String psd = SpUtils.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");

                    // 存储密码 和 确认密码一样 则进入别的页面
                    if (psd.equals(Md5Utils.encoder(config_psd))) {
                        /// 跳转到设置页面 ： 这个页面必须在AndroidManifest.xml里面声明
//                        Intent intent = new Intent(getApplicationContext(), TestActivety.class);
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);

                        /// 跳转到新的页面 把对话框进行隐藏
                        alertDialog.dismiss();

                    } else {
                        ToastUtils.show(getApplicationContext(), "确认密码错误");
                    }
                } else {
                    ToastUtils.show(getApplicationContext(), "请输入密码");
                }

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 内部类使用外部变量，需要把外部变量改成final
                alertDialog.dismiss();
            }
        });

    }

    /**
     * 设置密码对话框
     */
    private void showSetpsdDialog() {
        // 需自定展示样式，所以需要调用alertDialog.setView();
        // view有自己编写的xml转换成view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
        //alertDialog.setView(view);
        // 为了兼容低版本，给对话框布局的时候 去掉内边距（系统默认上下为2，在低版本系统里面）
        alertDialog.setView(view, 0, 0, 0, 0);
        alertDialog.show();

        /// 这个地方注意要用View的findViewById, 不然会报错
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ed_set_psd = (EditText) view.findViewById(R.id.ed_set_psd);
                EditText ed_config_psd = (EditText) view.findViewById(R.id.ed_config_psd);

                String psd = ed_set_psd.getText().toString();
                String config_psd = ed_config_psd.getText().toString();

                if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(config_psd)) {

                    if (psd.equals(config_psd)) {
                        /// 跳转到设置页面 ： 这个页面必须在AndroidManifest.xml里面声明
//                        Intent intent = new Intent(getApplicationContext(), TestActivety.class);
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);
                        /// 跳转到新的页面 把对话框进行隐藏
                        alertDialog.dismiss();

                        SpUtils.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, Md5Utils.encoder(psd));

                    } else {
                        ToastUtils.show(getApplicationContext(), "确认密码错误");
                    }

                } else {
                    ToastUtils.show(getApplicationContext(), "请输入密码");

                }

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 内部类使用外部变量，需要把外部变量改成final
                alertDialog.dismiss();


            }
        });

    }

    private void initUI() {

        gv_home = findViewById(R.id.gv_home);
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mTitleArr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleArr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_title.setText(mTitleArr[position]);
            iv_icon.setImageResource(mPictureArr[position]);

            return view;
        }
    }
}



