package com.cangin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 16:44
 * @Description:把3张图片合在一张图片里，用的是java自己的类实现的：
 */
public class MixImage {
    public static void main(String[] args) {

        try {
//            MixImage.main("E:\\1.jpg","e:\\60648.png","e:\\60648.png","e:\\60648.png","E:\\444.jpg") ;
            //第一张图是白色背景，中间三张是要合成的，最后一个是最后合成的照片名称
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String from1, String from2, String from3, String from4, String toPaht) {
        try {



            /*  BufferedImage的setRGB参数(startX,startY,w,h,rgbArray,offset,scansize)          startX, startY 是要提取的区域左上角图像的坐标
             w, h 是要提取的区域的宽度和高度
             rgbArray 是接收像素值的整数数组
             offset 是数组中接收第一个像素值的位置的索引。
             scansize 是图像中相邻两行中具有相同行索引的像素的索引偏移值。
             */

            // 读取第一张图片

  /*下面写的是直接读取硬盘上的文件，如果要读取http://img03.taobaocdn.com/a.jpg这种的话，那么要用如下方法

      URL url = new URL(imagePath);
   BufferedImage ImageOne = ImageIO.read(url);
  */

            File fileOne = new File(from1);
            BufferedImage ImageOne = ImageIO.read(fileOne);

            int width1 = ImageOne.getWidth();// 图片宽度
            int height1 = ImageOne.getHeight();// 图片高度

            // 从图片中读取RGB
            int[] ImageArrayOne = new int[width1 * height1];
            ImageArrayOne = ImageOne.getRGB(0, 0, width1, height1, ImageArrayOne,
                    0, width1);

            // 对第二张图片做相同的处理
            File fileTwo = new File(from2);
            BufferedImage ImageTwo = ImageIO.read(fileTwo);
            int width2 = ImageTwo.getWidth();// 图片宽度
            int height2 = ImageTwo.getHeight();// 图片高度

            int[] ImageArrayTwo = new int[width2 * height2];
            ImageArrayTwo = ImageTwo.getRGB(0, 0, width2, height2, ImageArrayTwo,
                    0, width2);


            // 对第三张图片做相同的处理
            File fileThree = new File(from3);
            BufferedImage ImageThree = ImageIO.read(fileThree);

            int width3 = ImageThree.getWidth();// 图片宽度
            int height3 = ImageThree.getHeight();// 图片高度

            int[] ImageArrayThree = new int[width3 * height3];
            ImageArrayThree = ImageThree.getRGB(0, 0, width3, height3, ImageArrayThree,
                    0, width3);

            // 对第四张图片做相同的处理
            File fileFour = new File(from4);
            BufferedImage ImageFour = ImageIO.read(fileFour);
            /**
             * 因为第四张图片很大，要压缩一下，所以要重新在缓冲中生成一张压缩后的图，然后赋值给原图，这里是压缩到200*200的
             */
            BufferedImage newImage = new BufferedImage(200, 200, ImageFour.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(ImageFour, 0, 0, 200, 200, null);
            g.dispose();
            ImageFour = newImage;

            int width4 = ImageFour.getWidth();// 图片宽度
            int height4 = ImageFour.getHeight();// 图片高度
            int[] ImageArrayFour = new int[width4 * height4];
            ImageArrayFour = ImageFour.getRGB(0, 0, width4, height4, ImageArrayFour,
                    0, width4);

            // 生成新图片

            BufferedImage ImageNew = new BufferedImage(560, 560,
                    BufferedImage.TYPE_INT_RGB);
            ImageNew.setRGB(0, 0, width1, height1, ImageArrayOne, 0, width1);// 设置第一张图的RGB，这里是一张白色的背景图
            ImageNew.setRGB(50, 70, width2, height2, ImageArrayTwo, 0, width2);// 设置第二张图的RGB
            ImageNew.setRGB(50, 280, width3, height3, ImageArrayThree, 0, width3);// 设置第三张图的RGB
            ImageNew.setRGB(300, 300, width4, height4, ImageArrayFour, 0, width4);// 设置第四张图的RGB

            File outFile = new File(toPaht);
            ImageIO.write(ImageNew, "jpg", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
