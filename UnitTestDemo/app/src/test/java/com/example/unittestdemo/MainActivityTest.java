package com.example.unittestdemo;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void show() {

        /// Android使用的是Android虚拟机
        /// 单元测试 使用的是java虚拟机
        MainActivity activity = new MainActivity();

        activity.show();
    }
}