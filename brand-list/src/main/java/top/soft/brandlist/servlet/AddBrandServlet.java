package top.soft.brandlist.servlet;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.brandlist.entity.Brand;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gmy
 * @description: TODO
 * @date 2024/11/9 16:13
 */
@WebServlet("/addBrand")
public class AddBrandServlet   extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder(); // 创建一个 StringBuilder 对象，用于构建字符串
        String line; // 声明一个字符串变量，用于存储每次读取的行
        BufferedReader br = req.getReader(); // 从某个请求对象（可能是 ServletRequest）获取一个 BufferedReader 实例

        while ((line = br.readLine()) != null) { // 使用 while 循环读取 BufferedReader 中的每一行，直到没有更多行可读
            stringBuilder.append(line); // 将读取的行追加到 StringBuilder 对象中
        }
        List<Brand> brands = new ArrayList<>((List<Brand>) req.getServletContext().getAttribute("brands")); // 从 ServletContext 中获取一个品牌列表，并将其转换为 ArrayList
        Brand brand = JSON.parseObject(stringBuilder.toString(), Brand.class); // 将 StringBuilder 中的字符串解析为 Brand 对象
        brands.add(brand);
        req.getServletContext().setAttribute("brands", brands);
    }
}
