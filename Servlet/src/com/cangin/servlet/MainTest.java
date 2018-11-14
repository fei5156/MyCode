package com.cangin.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 11:52
 * @Description:
 */
public class MainTest {
    public static void main(String[] args) throws MalformedURLException, FileNotFoundException {

        test1(0, 1000);
        test1(1001, 0);
    }

    public static void test1(int start, int end) throws MalformedURLException, FileNotFoundException {

        String endpoint = "http://localhost:8082/down.do?id=1";

        RandomAccessFile raFile = new RandomAccessFile("E:/temp/test.txt", "rw");

        URL url = new URL(endpoint);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.addRequestProperty("location", "/tomcat.gif");
            conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
            conn.setRequestProperty("RANGE", "bytes=" + start + "-" + end);

            conn.connect();
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getContentLength());
            System.out.println(conn.getContentType());

            InputStream ins = (InputStream) conn.getContent();

            raFile.seek(start);

            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = ins.read(buffer)) != -1) {
                raFile.write(buffer, 0, len);
            }
            raFile.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
