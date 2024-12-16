package com.example.classoa.mapper;

import com.example.classoa.entity.Notice;
import com.example.classoa.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoticeMapperTest {

    @Test
    void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            mapper.insert(Notice.builder().receiverId(7L).content("测试消息").createTime(new Date()).build());
            return null;
        });
    }

    @Test
    void selectByReceiverId() {
        MyBatisUtils.executeQuery(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            List<Notice> notices = mapper.selectByReceiverId(7L);
            System.out.println(notices);
            return notices;
        });
    }
}