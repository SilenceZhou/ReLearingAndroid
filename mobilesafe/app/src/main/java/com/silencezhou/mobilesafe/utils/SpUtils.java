package com.silencezhou.mobilesafe.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private static SharedPreferences sp;

    static public void putBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * @param ctx 上下文环境
     * @param key  存储key值
     * @param defValue 默认值
     * @return
     */
    static public boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }


    /**
     * 写入字符串到 偏好设置文件了里面
     * @param ctx
     * @param key
     * @param value
     */
    static public void putString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * @param ctx 上下文环境
     * @param key  存储key值
     * @param defValue 默认值
     * @return
     */
    static public String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * @param ctx 上下文环境
     * @param key key值
     */
    public static void remove(Context ctx, String key) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();

    }
}
