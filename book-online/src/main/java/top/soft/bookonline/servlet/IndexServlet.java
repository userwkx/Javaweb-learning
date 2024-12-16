package top.soft.bookonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.bookonline.entity.Book;
import top.soft.bookonline.service.BookService;
import top.soft.bookonline.service.impl.BookServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午2:32
 */
@WebServlet(urlPatterns = "/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 创建 BookService 实例
        BookService bookService = new BookServiceImpl();

        // 获取图书列表
        List<Book> bookList = bookService.getBooks();

        // 将图书列表放入请求作用域
        req.setAttribute("bookList", bookList);

        // 通过服务器端转发，将数据带到 JSP 页面
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
