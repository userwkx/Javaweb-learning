package top.soft.bookonline.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.bookonline.entity.Book;
import top.soft.bookonline.service.BookService;
import top.soft.bookonline.service.impl.BookServiceImpl;

import java.io.IOException;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午2:42
 */
@WebServlet(urlPatterns = "/detail/*")
public class BookDetailServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        bookService = new BookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的 URI 并去除空格
        String requestPath = req.getRequestURI().trim();

        // 获取最后一个 "/" 的位置
        int position = requestPath.lastIndexOf("/");

        // 从该位置到最后取子串，也就是 /detail/3 中的 3，有了这个 id 就可以找到对应的图书对象
        String id = requestPath.substring(position + 1);

        // 根据 id 查找图书
        Book book = bookService.getBookById(Integer.parseInt(id));

        // 将图书对象放入请求作用域
        req.setAttribute("book", book);

        // 通过服务器端转发，将数据带到 JSP 页面
        req.getRequestDispatcher("/book_detail.jsp").forward(req, resp);
    }
}
