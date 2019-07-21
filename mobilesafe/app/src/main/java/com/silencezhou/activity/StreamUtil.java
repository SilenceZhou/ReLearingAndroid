package com.silencezhou.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class StreamUtil {

    /**
     * 流转换成字符串
     *
     * @param is 输入流
     * @return 转换成字符串 返回null代表异常
     */
    public static String stream2String(InputStream is) {

        // 在读取流的过程中，将读取的内容存储缓存中，然后一次性的转换成字符串返回
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 读流操作，读到没有位置
        byte[] buffer = new byte[1024];

        int temp = -1;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            // 返回读取的数据
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
