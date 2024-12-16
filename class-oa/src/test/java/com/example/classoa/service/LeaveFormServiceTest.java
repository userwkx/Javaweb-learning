package com.example.classoa.service;

import com.example.classoa.entity.LeaveForm;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LeaveFormServiceTest {
    LeaveFormService leaveFormService = new LeaveFormService();

    //市场部员工请假单（72小时以上）
    @Test
    void createLeaveForm1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8L);
        form.setStartTime(sdf.parse("2024120409"));
        form.setEndTime(sdf.parse("2024121409"));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时以上)");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm);
    }

    //市场部员工请假单（72小时以内）
    @Test
    void createLeaveForm2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8L);
        form.setStartTime(sdf.parse("2024120409"));
        form.setEndTime(sdf.parse("2024120509"));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时以内)");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm);
    }

    //研发部门经理请假单
    @Test
    void createLeaveForm3() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(2L);
        form.setStartTime(sdf.parse("2024120409"));
        form.setEndTime(sdf.parse("2024120509"));
        form.setFormType(1);
        form.setReason("研发部部门经理请假单");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm);
    }

    //总经理请假单
    @Test
    void createLeaveForm4() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(1L);
        form.setStartTime(sdf.parse("2024120409"));
        form.setEndTime(sdf.parse("2024120509"));
        form.setFormType(1);
        form.setReason("总经理请假单");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm);
    }
}
