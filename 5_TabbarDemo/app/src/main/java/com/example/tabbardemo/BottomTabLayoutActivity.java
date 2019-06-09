package com.example.tabbardemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BottomTabLayoutActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Fragment []mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tabbar);
//        mFragments = DataGenerator
    }
}
