package com.silencezhou.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

    private CheckBox cb_box;
    private TextView tv_desc;
    private static final String TAG = "SettingItemView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.silencezhou.mobilesafe";
    private String mDesctitle;
    private String mDescoff;
    private String mDescon;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // xml -> view 一行抵两行
        //View view = View.inflate(context, R.layout.setting_item, null);
        //this.addView(view);
        View.inflate(context, R.layout.setting_item, this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        cb_box = (CheckBox) findViewById(R.id.cb_box);

        // 获取自定义已经原生属性的操作 attrs
        initAttrs(attrs);

        tv_title.setText(mDescon);
    }

    /**
     * 返回属性集合中自定义属性值
     * @param attrs 构造方法中维护好的属性集合
     */
    private void initAttrs(AttributeSet attrs) {

        Log.i(TAG, "initAttrs count: " + attrs.getAttributeCount());

        // 遍历属性
//        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//            Log.i(TAG, "name: " + attrs.getAttributeName(i));
//            Log.i(TAG, "value: " + attrs.getAttributeValue(i));
//            Log.i(TAG, "===========================");
//        }

        mDesctitle = attrs.getAttributeValue(NAMESPACE, "desctitle");
        mDescoff = attrs.getAttributeValue(NAMESPACE, "descoff");
        mDescon = attrs.getAttributeValue(NAMESPACE, "descon");




    }

    /**
     * @return 返回当前SettingItemView的选中状态
     */
    public boolean isCheck() {
        return cb_box.isChecked();
    }

    /**
     * @param isCheck  是否作为开启的变量
     */
    public void setCheck(boolean isCheck) {
        cb_box.setChecked(isCheck);
        if (isCheck) {
            tv_desc.setText(mDescon);
        } else {
            tv_desc.setText(mDescoff);
        }
    }


}
