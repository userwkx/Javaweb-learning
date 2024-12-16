package com.example.classoa.service;

import com.example.classoa.entity.Employee;
import com.example.classoa.entity.LeaveForm;
import com.example.classoa.entity.Notice;
import com.example.classoa.entity.ProcessFlow;
import com.example.classoa.mapper.EmployeeMapper;
import com.example.classoa.mapper.LeaveFormMapper;
import com.example.classoa.mapper.NoticeMapper;
import com.example.classoa.mapper.ProcessFlowMapper;
import com.example.classoa.service.exception.LeaveFormException;
import com.example.classoa.utils.DateUtils;
import com.example.classoa.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

public class LeaveFormService {
    private final EmployeeService employeeService = new EmployeeService();

    public LeaveForm createLeaveForm(LeaveForm leaveForm) {
        return (LeaveForm) MyBatisUtils.executeUpdate(sqlSession -> {
            // 一、新增请假单数据
            // 1. 根据请假单中的员工号，查询员工信息
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(leaveForm.getEmployeeId());
            // 2. 8级以下员工请假单状态为 processing，8级（总经理）状态为 approved
            if (employee.getLevel() == 8) {
                leaveForm.setState("approved");
            } else {
                leaveForm.setState("processing");
            }
            // 3. 持久化请假单数据
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            leaveFormMapper.insert(leaveForm);

            // 二、新增处理流程数据
            // 1. 新增第一条流程数据：因为请假单已提交，所以此流程数据状态为 complete
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow flow1 = ProcessFlow.builder()
                    .formId(leaveForm.getFormId())
                    .operatorId(leaveForm.getEmployeeId())
                    .action("apply")
                    .createTime(new Date())
                    .orderNo(1)
                    .state("complete")
                    .isLast(0)
                    .build();
            processFlowMapper.insert(flow1);
// 分情况创建其余流程数据
            int level = employee.getLevel();
            switch (level) {
                case 1, 2, 3, 4, 5, 6 -> {
// 找到员工的leader（部门经理）
                    Employee leader = employeeService.selectLeader(employee.getEmployeeId());

// 先填充和请假时长无关的数据（是否最后一步和时长有关）
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(leader.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setCreateTime(new Date());
                    flow2.setOrderNo(2);
                    flow2.setState("process");

// 调用工具类计算请假时长（小时）
                    long hours = DateUtils.getDiffHours(leaveForm.getCreateTime(), leaveForm.getEndTime());
// 超过 72 小时
                    if (hours >= 72) {
                        // 设置为不是最后一步，插入这条流程数据
                        flow2.setIsLast(0);
                        processFlowMapper.insert(flow2);

// 找到部门经理的leader（总经理）
                        Employee boss = employeeService.selectLeader(employee.getEmployeeId());

// 继续生成第三条流程数据，并插入数据库
                        ProcessFlow flow3 = new ProcessFlow();
                        flow3.setFormId(leaveForm.getFormId());
                        flow3.setOperatorId(boss.getEmployeeId());
                        flow3.setAction("audit");
                        flow3.setCreateTime(new Date());
                        flow3.setState("ready");
                        flow3.setOrderNo(3);
                        flow3.setIsLast(1);
                        processFlowMapper.insert(flow3);
                    } else {
                        // 小于 72 小时，是最后一步，插入这条数据
                        flow2.setIsLast(1);
                        processFlowMapper.insert(flow2);
                    }
                }

                case 7 -> {
// 7级员工，进生成总经理审批任务
                    Employee boss = employeeService.selectLeader(employee.getEmployeeId());
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(boss.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setCreateTime(new Date());
                    flow2.setState("process");
                    flow2.setOrderNo(2);
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);
                }

                case 8 -> {
// 8级员工，生成总经理审批任务，系统自动通过
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(employee.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setResult("approved");
                    flow2.setReason("自动通过");
                    flow2.setCreateTime(new Date());
                    flow2.setAuditTime(new Date());
                    flow2.setState("completed");
                    flow2.setOrderNo(2);
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);
                }

                default -> System.out.println("无此等级员工");
            }
            return leaveForm;
        });
    }

    public List<Map<String, Object>> getLeaveFormList(String state, Long operatorId) {
        return (List<Map<String, Object>>) MyBatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            List<Map<String, Object>> map = leaveFormMapper.selectByParams(state, operatorId);
            return map;
        });
    }

    /**
     * 审核请假单
     *
     * @param formId     表单编号
     * @param operatorId 经办人(当前登录员工)
     * @param result     审批结果
     * @param reason     审批意见
     */
    public void audit(Long formId, Long operatorId, String result, String reason) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            //无论同意/驳回，当前任务状态变更为complete
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
            if (flowList.size() == 0) {
                throw new LeaveFormException("无效的审批流程");
            }
            //  获取当前任务 ProcessFlow 对象
            List<ProcessFlow> processList = flowList.stream().filter(p -> Objects.equals(p.getOperatorId(), operatorId) && "process".equals(p.getState())).toList();
            ProcessFlow process;
            if (processList.size() == 0) {
                throw new LeaveFormException("未找到待处理任务节点");
            } else {
                //取得处理任务节点的第一条记录，无论同意/驳回，当前任务状态变更为complete
                process = processList.get(0);
                process.setState("complete");
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowMapper.update(process);
            }
            //查询到当前的请假单数据
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = leaveFormMapper.selectById(formId);

            //请假人
            Employee employee = employeeService.selectById(form.getEmployeeId());
            //当前审批人
            Employee operator =employeeService.selectById(operatorId);
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");

            //如果当前任务是最后一个节点，代表流程结束，更新请假单状态对应的approved /refuse
            if (process.getIsLast() == 1) {
                form.setState(result);
                LeaveFormMapper.update(form);
                String strResult=null;
                if("approved".equals(result)) {
                    strResult="批准";
                }else if("refused".equals(result)) {
                    strResult="驳回";
                }
                //发给申请人的通知
                String notice1 = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结",
                        sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()),
                        operator.getTitle(),
                        operator.getName(),
                        strResult,
                        reason);
                noticeMapper. insert(Notice.builder().receiverId( form.getEmployeeId()).content(notice1).createTime(new Date()).build());

                //发给审批人的通知
                String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束",
                        employee.getTitle(),
                        employee.getName(),
                        sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()),
                        strResult, reason);
                noticeMapper. insert(Notice.builder().receiverId(operator.getEmployeeId( )).content(notice2).createTime(new Date()).build());
            } else {
                //readyList 包含所有后续任务节点
                List<ProcessFlow> readyList = flowList.stream().filter(p -> "ready".equals(p.getState())).toList();
                //如果当前任务不是最后一个节点且审批通过，那么下一个节点的状态从 ready 变process
                if ("approved".equals(result)) {
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowMapper.update(readyProcess);

                    //消息1:通知表单提交人，部门经理已经审批通过,交由上级继续审批
                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已通过，审批意见:%s,请继续等待上级审批",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            reason);

                    noticeMapper.insert(
                            Notice.builder()
                                    .receiverId(form.getEmployeeId())
                                    .content(notice1)
                                    .createTime(new Date())
                                    .build());

                    //消息2:通知部门经理(当前经办人)，员工的申请你已批准，交由上级继续审
                    String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已批准，审批意见:s,申请转至上级领导继续审批",
                            employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()),sdf.format(form.getEndTime( )),
                                    reason);
                            noticeMapper.insert(Notice.builder().receiverId(operator.getEmployeeId( ))
                                    .content(notice2).createTime(new Date()).build());

                    //消息3: 通知总经理有新的审批任务
                    String notice3 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                            employee.getTitle(),employee.getName( ), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                    noticeMapper. insert(Notice.builder().receiverId(readyProcess.getOperatorId()).content(notice3).createTime(
                            new Date()).build());

                } else if ("refused".equals(result)) {
                    //如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变为 cancel，请假单状态变为r
                    for (ProcessFlow p : readyList) {
                        p.setState("cancel");
                        processFlowMapper.update(p);
                    }
                    form.setState("refused");
                    LeaveFormMapper.update(form);

                    //消息1:通知申请人表单已被驳回
                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已被驳回,审批意见:%s,审批流程已结束",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),reason);
                    noticeMapper.insert( Notice.builder().receiverId(operator.getEmployeeId()).content(notice1).createTime( new
                            Date()).build());
                            //消息2:通知经办人表单"您已驳回"
                    String notice2 = String.format("%s-%s 提起请假申请[%s-%s]您已驳回,审批意见:%s,审批流程已结束",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()), reason);
                    noticeMapper.insert(Notice.builder().receiverId(operator.getEmployeeId())
                            .content(notice2).createTime(new Date()).build());
                }
            }
            return null;
        });
    }
}