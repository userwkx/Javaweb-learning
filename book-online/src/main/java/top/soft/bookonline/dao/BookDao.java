package top.soft.bookonline.dao;

import top.soft.bookonline.entity.Book;

import java.util.List;

public interface BookDao {

    /**
     * 查询图书列表
     * @return 返回图书列表
     */
    List<Book> selectAll();

    /**
     * 根据id查询书籍详情
     * @param id 书籍的ID
     * @return 返回对应ID的书籍对象
     */
    Book getBookById(int id);

}
