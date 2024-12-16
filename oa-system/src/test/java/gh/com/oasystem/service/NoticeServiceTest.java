package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Notice;
import gh.com.oasystem.service.impl.NoticeServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class NoticeServiceTest {
    private NoticeService noticeService=new NoticeServiceImpl();
    @Test
    void queryAllNotices() {
        List<Notice>  notices=noticeService.queryAllNotices(2L);

        System.out.println(notices);
    }

    @Test
    void sendNotice() {
    }
}