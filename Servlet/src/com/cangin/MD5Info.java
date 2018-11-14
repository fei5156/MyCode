package com.cangin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 15:10
 * @Description: 用户点击上传按钮，FLASH先发送请求给后端，
 * 后端从数据库中用MD5判断验证是否已经上传过（且上传完整）该视频，
 * 如果上传过，则实现秒传（即为直接返回服务器存储地址，不用再上传），
 * 如果上传过，但是没有上传全部，则给FLASH返回一个已上传文件大小，FLASH重新分份后继续发送剩下的文件流给服务端。
 * 知识点：MD5生成散列码
 * java.security.MessageDigest类用于为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
 * 简单点说就是用于生成散列码。信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值。
 */
public class MD5Info {
    public static void main(String[] args) {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = "abcd";
            MessageDigest com = MessageDigest.getInstance("MD5"); //生成MessageDigest对象
            com.update(str.getBytes("UTF-8"));  //传入需要计算的字符串
            byte[] b = com.digest(); //计算消息摘要
            System.out.println(convertToHexString(b)); //e2fc714c4727ee9395f324cd2e7f331f

            //对文件生成md5
            byte[] fileA = create("E:\\1.jpg");
            System.out.println(convertToHexString(fileA));

        } catch (Exception e) {
//             TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static String convertToHexString(byte data[]) {
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            strBuffer.append(Integer.toHexString(0xff & data[i]));
        }
        return strBuffer.toString();

    }


    /**
     * 对文件生成md5
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static byte[] create(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);
        byte[] buf = new byte[1024];
        MessageDigest com = MessageDigest.getInstance("MD5");
        int num;
        do {
            num = fis.read(buf);
            if (num > 0) {
                com.update(buf, 0, num);
            }
        } while (num != -1);

        fis.close();
        return com.digest();
    }
}
