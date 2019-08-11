package com.silencezhou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

class Setup2Activity extends Activity {

    private SettingItemView siv_sim_bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }

    private void initUI() {
        siv_sim_bind = (SettingItemView) findViewById(R.id.siv_sim_bind);

        // 回显的过程
        final String sim_number = SpUtils.getString(this, ConstantValue.SIM_NUMER, "");
        if (TextUtils.isEmpty(sim_number)) {
            siv_sim_bind.setCheck(false);
        } else {
            siv_sim_bind.setCheck(true);
        }

        siv_sim_bind.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // 获取原有的状态
                // 因为是全局变量 不会加final
                // 内部类使用外部变量： 外部类的变量前面加final， 或者设置为全局变量
                boolean isCheck = siv_sim_bind.isCheck();
                // 将原有状态取反，存储 并设置给当前条目
                siv_sim_bind.setCheck(!isCheck);


                // 存储
                if (!isCheck) {

                    // 模拟器获取序列号不行, 这个地方进行模拟
                    SpUtils.putString(getApplication(), ConstantValue.SIM_NUMER, "123");
//                    // 存储 序列卡号
//                    // 1.获取sim卡序列号的TelephoneManager
//                    TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    Activity#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for Activity#requestPermissions for more details.
//
//
//                        return;
//                    }
//
//                    /// 真机OK
//                    @SuppressLint("MissingPermission") String simSerialNumber = manager.getSimSerialNumber();
//
//                    System.out.println("simSerialNumber = " + simSerialNumber);
//
//                    // 存储
//                    SpUtils.putString(getApplication(), ConstantValue.SIM_NUMER, simSerialNumber);

                } else {
                    System.out.println("22222");
                    // 将存储序列卡号的节点，从sp中删除
                    SpUtils.remove(getApplicationContext(), ConstantValue.SIM_NUMER);
                }
            }
        });
    }


    public void prePage(View view) {

        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);
        finish();

    }

    public void nextPage(View view) {

        String simSerialNumber = SpUtils.getString(this, ConstantValue.SIM_NUMER, "");

        if (!TextUtils.isEmpty(simSerialNumber)) {

            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);
            finish();

        } else {
            ToastUtils.show(this, "请绑定sim卡");
        }
    }
}
