package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.model.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    int insertNotice(Notice notice);
    List<Node> getAllNotice(Long id);
}
