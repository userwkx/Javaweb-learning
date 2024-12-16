package gh.com.oasystem.service.impl;

import gh.com.oasystem.service.LeaveService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaveServiceImplTest {
    private LeaveService leaveService = new LeaveServiceImpl();

    @Test
    void queryLeaveList() {
        System.out.println(leaveService.queryLeaveList(1L));
    }
}