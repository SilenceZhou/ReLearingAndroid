package com.silencezhou.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;

public class Md5Utils {
    public static void main(String[] args) {

        // "abc" 则是'盐'
//        String psd = "1,.Aa1" + "abc";
        String psd = "123" + "abc"; // 轻松解密出来

        encoder(psd);
    }

    /** 给指定字符串按照MD5算法进行加密 (将字符串转换成 32位的字符串 => 不可逆)
     * @param psd 需要加密的密码
     * @return
     */
    private static void encoder(String psd) {

        // 单例模式
        try {

            // 指定加密算法类型 =>单例模式 参数为算法
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // 将需要加密的字符串转换成byte类型数组，然后进行随机哈希过程
            byte[] bs = digest.digest(psd.getBytes());
            System.out.println(bs.length);

            // 循环遍历bs，然后让其生成32位字符串，固定写法
            StringBuffer stringBuffer = new StringBuffer();
            for(byte b : bs) {
                int i = b & 0xff;
                // int类型的i转换成16进制字符
                String hexString = Integer.toHexString(i);

                System.out.println(hexString);
                // 不是两位则补零
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }

            /// 这样是不是加盐？？？ 加盐是在原始密码上面进行加
            /// stringBuffer.append("2d3");

            // 打印最终的md5字符串
            System.out.println(stringBuffer.toString());


            /// 网上能够界面： 因为不可逆，会有一个数据库 然后进行保存，提供进行查询
            //、

        } catch (NoSuchAlgorithmException e) {
            // 没有该算法异常
            e.printStackTrace();
        }

    }


}
