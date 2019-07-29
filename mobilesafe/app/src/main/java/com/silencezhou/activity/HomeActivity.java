package com.silencezhou.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mTitleArr;
    private int[] mPictureArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
    }

    /**
     * 设置密码对话框
     */
    private void showSetpsdDialog() {
        // 需自定展示样式，所以需要调用alertDialog.setView();
        // view有自己编写的xml转换成view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();

        View view = View.inflate(this,R.layout.dialog_set_psd, null);
        alertDialog.setView(view);
        alertDialog.show();
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



