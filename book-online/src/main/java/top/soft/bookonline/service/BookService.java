package top.soft.bookonline.service;

import top.soft.bookonline.entity.Book;
import top.soft.bookonline.entity.User;

import java.util.List;

public interface BookService {

    /**
     * 查询所有图书
     *
     * @return List<Book>
     */
    List<Book> getBooks();

    /**
     * 根据 id 查询图书
     *
     * @param id id
     * @return 图书信息
     */
    Book getBookById(int id);
}
