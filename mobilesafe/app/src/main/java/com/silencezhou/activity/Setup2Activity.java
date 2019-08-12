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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.silencezhou.mobilesafe.utils.ConstantValue;
import com.silencezhou.mobilesafe.utils.SpUtils;

import org.w3c.dom.Text;

import java.util.UUID;

class Setup2Activity extends Activity {

    private SettingItemView siv_sim_bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /// checkSelfPermission 该方法只能在API23上用
    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getMyUUID() {
        final TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return "";
        }
        tmDevice = "" + manager.getDeviceId();
        tmSerial = "" + manager.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
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
                    // 1.获取sim卡序列号的TelephoneManager
                    TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Setup2Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                        return;
                    }
                    String simSerialNumber = manager.getSimSerialNumber();
                    System.out.println("11111simSerialNumber = " + simSerialNumber);

                    // 安卓后续都没法获取 sim 序列号了
                    if (TextUtils.isEmpty(simSerialNumber)) {

                        simSerialNumber = getMyUUID();
                    }
                    System.out.println("22222simSerialNumber = " + simSerialNumber);

                    // 存储
                    SpUtils.putString(getApplication(), ConstantValue.SIM_NUMER, simSerialNumber);

                } else {

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
