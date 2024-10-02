package com.example.class03response.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wkx32
 * @description: TODO
 * @date 2024/9/28 下午2:54
 */
@WebServlet("/res")
public class ResponseTypeDemo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        switch (type) {
            case "img":
                getImages(request, response);
                break;
            case "ppd":
                getPdf(request, response);
                break;
            default:
                break;
        }
    }

    private void getImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpg");
        String realPath = request.getServletContext().getRealPath("/images/image.jpg");
        File file = new File(realPath);
        InputStream in = new FileInputStream(file);
        int read = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.flush();
        out.close();
    }

    private void getPdf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("ppdf/jpg");
        String realPath = request.getServletContext().getRealPath("/ppdf/ppdf.pdf");
        File file = new File(realPath);
        InputStream in = new FileInputStream(file);
        int read = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.flush();
        out.close();
    }
}
