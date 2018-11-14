package com.cangin;

import java.io.*;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 15:33
 * @Description: 合并文件
 */
public class RandomAccessFileDemo03 {
    public static void main(String[] args) throws Exception {

        File file = new File("e:\\file.txt");
        File file1 = new File("e:\\file1.txt");

        RandomAccessFile file2 = new RandomAccessFile("e:\\file2.txt", "rw");


        BufferedReader bf = new BufferedReader(new FileReader(file));
        BufferedReader bf1 = new BufferedReader(new FileReader(file1));

        String line = "";
        while ((line = bf.readLine()) != null) {
            file2.write(line.getBytes());
        }

        file2.seek(file2.getFilePointer());

        while ((line = bf1.readLine()) != null) {
            file2.write(line.getBytes());
        }

        /**
         * 输出file2的内容
         */
        RandomAccessFile file3 = new RandomAccessFile("e:\\file2.txt", "r");
        byte[] b = new byte[(int) file3.length()];


        for (int i = 0; i < b.length; i++) {
            b[i] = file3.readByte();
        }

        String aa = new String(b, "utf8");
        System.out.println(aa);

        file2.close();


    }
}
