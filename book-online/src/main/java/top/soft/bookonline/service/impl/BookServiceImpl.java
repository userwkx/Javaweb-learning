package top.soft.bookonline.service.impl;

import top.soft.bookonline.dao.BookDao;
import top.soft.bookonline.dao.impl.BookDaoImpl;
import top.soft.bookonline.entity.Book;
import top.soft.bookonline.service.BookService;

import java.util.List;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午2:37
 */
public class BookServiceImpl implements BookService {
    protected BookDao bookDao = new BookDaoImpl();
    /**
     * 获取图书列表
     *
     * @return 图书列表
     */
    @Override
    public List<Book> getBooks() {
        return bookDao.selectAll();
    }

    /**
     * 根据 ID 获取图书信息
     *
     * @param id 图书的唯一标识
     * @return 对应的图书信息
     */
    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

}
