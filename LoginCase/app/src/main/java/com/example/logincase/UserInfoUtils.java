package com.example.logincase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserInfoUtils {


    public static  boolean saveInfo(String username, String pwd) throws IOException {

        try {

            String result = username + "#" + pwd;

            File file = new File("/data/data/com.example.logincase/info.txt");

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(result.getBytes());

            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
