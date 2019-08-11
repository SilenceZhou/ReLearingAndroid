package com.silencezhou.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ContactListActivity extends Activity {

    private static final String TAG = "ContactListActivity";
    private List<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

    private MyAdapter myAdapter;
    private Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // 8.填充数据适配器
            myAdapter = new MyAdapter();
            lv_contact.setAdapter(myAdapter);
        }
    };
    private ListView lv_contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        initUI();
        initData();
    }

    /**
     * 获取联系人数据
     */
    private void initData() {

        /// 因为读取系统联系人，可能是一个耗时操作，放到子线程中处理
        new Thread() {

            @Override
            public void run() {
                // 内容解析器
                // Url地址，查看系统联系人
                // content://com.android.contacts/raw_contacts
                // content://com.android.contacts/data
                // 1.获取内容解析器对象
                ContentResolver contentResolver = getContentResolver();
                // 2.做查询系统联系人数据库表过程（读取联系人权限）
                Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"},
                        null,
                        null,
                        null);
                // 3.循环游标，知道没有数据为止
                contactList.clear();
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
//                    System.out.println(string);

                    /// 4.根据用户唯一性id，查询data表和mimetype表生成视图，获取data以及mimetype字段
                    Cursor indexCursor = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1", "mimetype"},
                            "raw_contact_id = ?",
                            new String[]{id},
                            null);

                    /// 5.混选获取每一个联系人的电话号码以及姓名，数据类型
                    System.out.println("=========================\n\n\n\n");
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    while (indexCursor.moveToNext()) {
                        String data = indexCursor.getString(0);
                        String mimetype = indexCursor.getString(1);
                        if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            if (!TextUtils.isEmpty(data) && data.length() > 0) {
                                hashMap.put("phone", data.replace(" ", ""));
                            }

                        } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                            if (!TextUtils.isEmpty(data) && data.length() > 0) {
                                hashMap.put("name", data.replace(" ", ""));
                            }

                        }

//                        Log.i(TAG, "mimetype =" + indexCursor.getString(1));
//                        Log.i(TAG, "data =" + indexCursor.getString(0));
                    }
//                    Log.i(TAG, "hasMap" + hashMap);
                    if (!hashMap.isEmpty()) {
                        contactList.add(hashMap);
                    }
                    indexCursor.close();
                }
                cursor.close();

                // 7.消息机制
                mHandler.sendEmptyMessage(0);

            }
        }.start();


    }

    private void initUI() {

        lv_contact = (ListView) findViewById(R.id.lv_contact);

        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 1.获取点中条目的索引指向集合中的对象
                if (myAdapter != null) {
                    HashMap<String, String> item = myAdapter.getItem(position);
                    // 获取当前条目指向集合的电话号码
                    String phone = item.get("phone");
                    System.out.println("contact界面回传给setup3界面的数据" + phone);
                    // 此电话号码给第三个导航界面
                    Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getApplicationContext(), R.layout.listview_contact_item, null);

            // 这个地方一定注意是view.findViewByID 不然报错， 就应该是view.findViewById 来查找控件
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            String name = getItem(position).get("name");
            String phone = getItem(position).get("phone");

            if (!TextUtils.isEmpty(name) && name.length() > 0) {
                tv_name.setText(name);
            }

            if (!TextUtils.isEmpty(phone) && phone.length() > 0) {
                tv_phone.setText(phone);
            }

            return view;
        }
    }
}
