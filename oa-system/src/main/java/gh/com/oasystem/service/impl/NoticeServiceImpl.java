package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.NoticeMapper;
import gh.com.oasystem.model.entity.Notice;
import gh.com.oasystem.service.NoticeService;
import gh.com.oasystem.utils.MybatisUtil;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeServiceImpl implements NoticeService {
    @Override
    public List<Notice> queryAllNotices(Long id) {
        return   (List<Notice>) MybatisUtil.executeQuery(sqlSession -> {
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            return noticeMapper.getAllNotice(id);

        });
    }



    @Override
    public Notice sendNotice(Long id, String content) {
        Notice notice = new Notice(null,id,content, LocalDateTime.now());

        MybatisUtil.executeUpdate(sqlSession -> {
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

            return noticeMapper.insertNotice(notice);
        });
        return notice;

    }
}
