package com.silencezhou.activity;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void show(Context ctx, String msg){

        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
}
