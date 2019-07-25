package com.silencezhou.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 能够获取焦点的自定义TextView
 */
public class MarqueeTextView extends TextView {

    // 使用在通过java代码创建
    public MarqueeTextView(Context context) {
        super(context);
    }

    // 有系统调用 xml 转换成 对应的java对象的时候
    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 系统调用
    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 系统调用
    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     * 修改为默认可以获取焦点
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
