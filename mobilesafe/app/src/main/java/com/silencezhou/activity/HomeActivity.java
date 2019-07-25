package com.silencezhou.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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



