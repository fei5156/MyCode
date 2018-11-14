package com.cangin;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 15:18
 * @Description: 断点续传的核心就是分块上传，然后再进行合并，我们先来了解一下合并所需的类RandomAccessFile。
 * <p>
 * RandomAccessFile类是随机读取类，它是一个完全独立的类。
 * 它适用于由大小已知的记录组成的文件，所以我们可以使用seek()将记录从一处转移到另一处，然后读取或者修改记录。
 * <p>
 * 文件中记录的大小不一定都相同，只要能够确定哪些记录有多大以及它们在文件中的位置即可。
 * <p>
 * RandomAccessFile类可以实现对文件内容的读写操作，但是比较复杂。所以一般操作文件内容往往会使用字节流或字符流方式。
 * （1）写入数据
 * 当用 rw 方式声明RandomAccessFile对象时，如果要写入的文件不存在，系统将自行创建。
 * r 为只读；w 为只写；rw 为读写。
 */
public class RandomAccessFileDemo {

    // 所有的异常直接抛出，程序中不再进行处理
    public static void main(String args[]) throws Exception {

        File f = new File("e:" + File.separator + "test.txt"); // 指定要操作的文件
        RandomAccessFile rdf = null; // 声明RandomAccessFile类的对象
        rdf = new RandomAccessFile(f, "rw");// 读写模式，如果文件不存在，会自动创建

        String name = null;
        int age = 0;

        name = "zhangsan"; // 字符串长度为8
        age = 30; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中

        name = "lisi    "; // 字符串长度为8
        age = 31; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中

        name = "wangwu  "; // 字符串长度为8
        age = 32; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中
        rdf.close(); // 关闭


    }


}
