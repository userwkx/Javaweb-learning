package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> queryAllNotices(Long id);
    Notice sendNotice(Long id,String content);
}
