package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.Notice;
import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoticeMapperTest {

    @Test
    void insertNotice() {
        Notice notice =Notice.builder()

                .receiverId(1L)
                .content("notice测试")
                .createTime(LocalDateTime.now())
                .build();
        MybatisUtil.executeUpdate(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            int result=mapper.insertNotice(notice);
            System.out.println(result);
            return result;
        });
    }
}