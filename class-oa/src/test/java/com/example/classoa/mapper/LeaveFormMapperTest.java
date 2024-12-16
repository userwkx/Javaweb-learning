package com.example.classoa.mapper;

import com.example.classoa.entity.LeaveForm;
import com.example.classoa.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class LeaveFormMapperTest {
    @Test
    void insert() {
        MyBatisUtils.executeUpdate(sqlsession -> {
            LeaveFormMapper leaveFormMapper = sqlsession.getMapper(LeaveFormMapper.class);
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(3L);
            form.setFormType(1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            Date endTime = null;
            try {
                startTime = sdf.parse("2024-12-03 10:00:00");
                endTime = sdf.parse("2024-12-12 10:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            form.setStartTime(startTime);
            form.setEndTime(endTime);
            form.setReason("个人私事");
            form.setCreateTime(new Date());
            form.setState("processing");
            leaveFormMapper.insert(form);
            return null;
        });
    }
}