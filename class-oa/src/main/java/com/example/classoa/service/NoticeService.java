package com.example.classoa.service;

import com.example.classoa.entity.Notice;
import com.example.classoa.mapper.NoticeMapper;
import com.example.classoa.utils.MyBatisUtils;

import java.util.List;

public class NoticeService {
    public List<Notice> getNoticeList(Long receiverId){
        return(List) MyBatisUtils.executeQuery(sqlSession -> {
                    NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
                    return mapper.selectByReceiverId(receiverId);
                }
        );
    }
}