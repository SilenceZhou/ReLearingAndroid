package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// 代码实现按钮调用方法 -- 代码的优先级高于布局文件设置
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new MyclickListener());

    }


    /// 实现点击事件
    private class MyclickListener implements View.OnClickListener{

        /// 使用自定义布局Toast  ---- 不知道怎么引入图片资源文件
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView textView = (TextView) layout.findViewById(R.id.text123);
            textView.setText("This is a custome toast");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

        /// 使用系统的toast
       /* @Override
        public void onClick(View v) {
            System.out.println("按钮被点击");

            Context context = getApplicationContext();
            CharSequence text = "Hello toast";
            int duration = Toast.LENGTH_SHORT;

            // MainActivity.this
            Toast toast = Toast.makeText(context,text,duration);
            // 默认为考底部中间
            // 中间靠左
            // toast.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
            // 中间
            toast.setGravity(Gravity.CENTER,0,0);

            toast.show();


            // 不保留对象 使用链式访问
            //Toast.makeText(context,text,duration).show();
        }*/

    }




}
