package com.silencezhou.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

    private CheckBox cb_box;
    private TextView tv_desc;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
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
            tv_desc.setText("自动更新已结束");
        } else {
            tv_desc.setText("自动更新已关闭");
        }
    }


}
