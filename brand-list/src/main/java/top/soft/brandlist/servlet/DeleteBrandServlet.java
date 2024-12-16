package top.soft.brandlist.servlet;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.brandlist.entity.Brand;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@WebServlet("/deleteBrand")
public class DeleteBrandServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取请求体中的数据（假设是要删除的品牌的ID）
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader br = req.getReader();
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }

        // 2、将 JSON 字符串转成 JAVA 对象，假设品牌的 ID 用于删除操作
        String brandIdToDelete = JSON.parseObject(stringBuilder.toString(), String.class); // 假设是品牌ID

        // 3、从 ServletContext 获取品牌列表
        List<Brand> brands = new ArrayList<>((List<Brand>) req.getServletContext().getAttribute("brands"));

        // 4、遍历品牌列表，找到并删除匹配的品牌
        Brand brandToRemove = null;
        for (Brand brand : brands) {
            if (brand.getId().equals(brandIdToDelete)) {
                brandToRemove = brand;
                break;
            }
        }

        // 5、如果找到了品牌，则从列表中删除
        if (brandToRemove != null) {
            brands.remove(brandToRemove);
            // 6、更新 ServletContext 中的品牌列表
            req.getServletContext().setAttribute("brands", brands);
        } else {
            // 如果没有找到该品牌，返回错误信息
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Brand not found");
        }
    }

}
