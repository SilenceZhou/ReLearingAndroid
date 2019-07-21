package com.example.logincase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_pssword;
    private CheckBox cb_ischeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText)findViewById(R.id.et_username);
        et_pssword = (EditText)findViewById(R.id.et_pssword);
        cb_ischeck = (CheckBox)findViewById(R.id.ck_rember);


    }

    public void login(View v){


        String name = et_username.getText().toString().trim();
        String psw = et_pssword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {

            Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            System.out.println("连接服务器进行登陆");

            if (cb_ischeck.isChecked()) {


                try {
                    boolean result = UserInfoUtils.saveInfo(name, psw);
                    if (result) {
                        Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.out.println("保存失败");
                }


            } else {
                Toast.makeText(MainActivity.this, "请勾选记住密码", Toast.LENGTH_LONG).show();
            }
        }


    }
}
