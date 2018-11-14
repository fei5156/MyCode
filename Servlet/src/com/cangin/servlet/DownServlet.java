package com.cangin.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 11:44
 * @Description:频库之断点下载
 */
public class DownServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String fileName;
        if (id == 1) {
            fileName = "E:\\19_DevelopmentDoc\\HTTP1.1.chm";
        } else if (id == 2) {
            fileName = "";

        } else if (id == 3) {
            fileName = "";
        } else {
            fileName = "";
        }

        RandomAccessFile raFile = new RandomAccessFile(fileName, "r");

        String range = req.getParameter("RANGE");

        int start = 0, end = 0;

        if (null != range && range.startsWith("bytes=")) {
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            end = Integer.parseInt(values[1]);
        }


        int requestSize = 0;
        if (end != 0 && end > start) {
            requestSize = end - start + 1;
            resp.addHeader("content-length", "" + (requestSize));
        } else {
            requestSize = Integer.MAX_VALUE;
        }

        byte[] buffer = new byte[4096];

        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=a.chm");
        ServletOutputStream os = resp.getOutputStream();

        int needSize = requestSize;


        raFile.seek(start);
        while (needSize > 0) {
            int len = raFile.read(buffer);
            if (needSize < buffer.length) {
                os.write(buffer, 0, needSize);
            } else {
                os.write(buffer, 0, len);
                if (len < buffer.length) {
                    break;
                }
            }
            needSize -= buffer.length;
        }

        raFile.close();
        os.close();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("----init--download-----");
    }
}
