package com.example.class03response.Download;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wkx32
 * @description: TODO
 * @date 2024/10/2 下午5:25
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数，文件名称
        String filename = request.getParameter("filename");

        // 找到文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/images/" + filename);

        // 使用字节流关联
        try (FileInputStream fis = new FileInputStream(realPath);
             ServletOutputStream sos = response.getOutputStream()) {

            // 获取文件的 mime 类型
            String mimeType = servletContext.getMimeType(filename);
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // 默认类型
            }

            // 设置响应头类型：content-type
            response.setHeader("Content-Type", mimeType);
            // 设置响应头打开方式: content-disposition
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);

            // 将输入流的数据写出到输出流中
            byte[] buff = new byte[1024 * 8];
            int len;
            while ((len = fis.read(buff)) != -1) {
                sos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 处理异常
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 文件未找到
        }
    }
}
