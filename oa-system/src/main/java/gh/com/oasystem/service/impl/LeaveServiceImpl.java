package gh.com.oasystem.service.impl;

import gh.com.oasystem.common.Constant;
import gh.com.oasystem.common.LevelEnum;
import gh.com.oasystem.common.LeaveFormStateEnum;
import gh.com.oasystem.mapper.DepartmentMapper;
import gh.com.oasystem.mapper.EmployeeMapper;
import gh.com.oasystem.mapper.ProcessFlowMapper;
import gh.com.oasystem.mapper.LeaveFormMapper;
import gh.com.oasystem.model.entity.Department;
import gh.com.oasystem.model.entity.Employee;
import gh.com.oasystem.model.entity.LeaveForm;
import gh.com.oasystem.model.entity.ProcessFlow;
import gh.com.oasystem.model.vo.AuditLeaveVO;
import gh.com.oasystem.service.DepartmentService;
import gh.com.oasystem.service.EmployeeService;
import gh.com.oasystem.service.LeaveService;
import gh.com.oasystem.service.NoticeService;
import gh.com.oasystem.utils.MybatisUtil;
import gh.com.oasystem.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class LeaveServiceImpl implements LeaveService {
    private static EmployeeService employeeService=new  EmployeeServiceImpl();
    private static DepartmentService departmentService=new DepartmentServiceImpl();
    private static NoticeService noticeService=new NoticeServiceImpl();
    @Override
    public void createLeave(LeaveForm form) {

        MybatisUtil.executeUpdate(sqlSession -> {
            EmployeeMapper employeeMapper=sqlSession.getMapper(EmployeeMapper.class);
            LeaveFormMapper leaveFormMapper=sqlSession.getMapper(LeaveFormMapper.class);
            ProcessFlowMapper processFlowMapper =sqlSession.getMapper(ProcessFlowMapper.class);
            DepartmentMapper departmentMapper=sqlSession.getMapper(DepartmentMapper.class);
            Employee employee=employeeService.queryEmployeeById(form.getEmployeeId());
            Department employeeDepartment=departmentService.queryDepartmentById(employee.getDepartmentId());
            if(LevelEnum.BOSS.getLevel()==employee.getLevel()){
                form.setState(LeaveFormStateEnum.APPROVED.getValue());
            }else{
                form.setState(LeaveFormStateEnum.PROCESSING.getValue());
            }


            Employee leader=employeeService.getLeader(employee.getEmployeeId());

            form.setCreateTime(LocalDateTime.now());
            leaveFormMapper.insertLeaveForm(form);
            System.out.println("mapper返回的自增的主键："+form.getFormId());
            if(LevelEnum.BOSS.getLevel()!=employee.getLevel())
            {
                Department leaderDepartment =departmentService.queryDepartmentById(leader.getDepartmentId());


                String notice1 = String.format("%s-%s提起请假申请[%s-%s],请您审批",
                        employeeDepartment.getDepartmentName(),
                        employee.getName(),
                        DateUtils.dateFormat(form.getStartTime()),
                        DateUtils.dateFormat(form.getEndTime()));
                noticeService.sendNotice(leader.getEmployeeId(), notice1);
                String notice2 = String.format("您的请假申请[%s-%s],已移交给%s-%s审批",

                        DateUtils.dateFormat(form.getStartTime()),
                        DateUtils.dateFormat(form.getEndTime()),
                        leaderDepartment.getDepartmentName(),
                        leader.getName()
                );
                noticeService.sendNotice(employee.getEmployeeId(), notice2);
            }
            ProcessFlow applyFlow=ProcessFlow.builder()
                    .formId(form.getFormId())
                    .operatorId(form.getEmployeeId())
                    .action(Constant.FLOW_ACTION_APPLY)
                    .createTime(LocalDateTime.now())
                    .orderNo(1)
                    .state(Constant.FLOW_STATE_COMPLETE)
                    .isLast(0)
                    .build();
            processFlowMapper.insertFlow(applyFlow);
            switch (employee.getLevel()){
                case 1,2,3,4,5,6->{
                    Boolean flag=DateUtils.checkHours(form.getStartTime(),form.getEndTime());
                    if(flag){
                        ProcessFlow managerFlow=ProcessFlow.builder()
                                .formId(form.getFormId())
                                .operatorId(leader.getEmployeeId())
                                .action(Constant.FLOW_ACTION_AUDIT)
                                .createTime(LocalDateTime.now())
                                .orderNo(2)
                                .state(Constant.FLOW_STATE_PROCESS)
                                .isLast(0)
                                .build();
                        processFlowMapper.insertFlow(managerFlow);

                        ProcessFlow bossFlow=ProcessFlow.builder()
                                .formId(form.getFormId())
                                .operatorId(employeeService.getLeader(leader.getEmployeeId()).getEmployeeId())
                                .action(Constant.FLOW_ACTION_AUDIT)
                                .createTime(LocalDateTime.now())
                                .orderNo(3)
                                .state(Constant.FLOW_STATE_READY)
                                .isLast(1)
                                .build();
                        processFlowMapper.insertFlow(bossFlow);
                    }else{
                        ProcessFlow auditFlow=ProcessFlow.builder()
                                .formId(form.getFormId())
                                .operatorId(leader.getEmployeeId())
                                .action(Constant.FLOW_ACTION_AUDIT)
                                .createTime(LocalDateTime.now())
                                .orderNo(2)
                                .state(Constant.FLOW_STATE_PROCESS)
                                .isLast(1)
                                .build();
                        processFlowMapper.insertFlow(auditFlow);
                    }
                }
                case 7->{
                    ProcessFlow auditFlow=ProcessFlow.builder()
                            .formId(form.getFormId())
                            .operatorId(leader.getEmployeeId())
                            .action(Constant.FLOW_ACTION_AUDIT)
                            .createTime(LocalDateTime.now())
                            .orderNo(2)
                            .state(Constant.FLOW_STATE_PROCESS)
                            .isLast(1)
                            .build();
                    processFlowMapper.insertFlow(auditFlow);
                }
                case 8->{
                    ProcessFlow auditFlow=ProcessFlow.builder()
                            .formId(form.getFormId())
                            .operatorId(form.getEmployeeId())
                            .action(Constant.FLOW_ACTION_AUDIT)
                            .result(Constant.FLOW_RESULT_APPROVED)
                            .reason("自动通过")
                            .createTime(LocalDateTime.now())
                            .auditTime(LocalDateTime.now())
                            .orderNo(2)
                            .state(Constant.FLOW_STATE_COMPLETE)
                            .isLast(1)
                            .build();
                    processFlowMapper.insertFlow(auditFlow);
                }
                default -> throw new RuntimeException("没有该级别员工");
            }
            return null;
        });
    }
    @Override
    public List<AuditLeaveVO>queryLeaveList(Long eid) {

        return (List<AuditLeaveVO>) MybatisUtil.executeQuery(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            return leaveFormMapper.selectAuditLeaveList(eid, Constant.FLOW_STATE_PROCESS);
        });

    }

    @Override
    public void auditLeave(Long operatorId, Long formId, String result, String reason) {
        MybatisUtil.executeUpdate(sqlSession -> {
            Employee employee=employeeService.queryEmployeeById(operatorId);
            Department employeeDepartment=departmentService.queryDepartmentById(employee.getDepartmentId());
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> allFlows = processFlowMapper.selectFlow(formId);
            List<ProcessFlow> processList = allFlows.stream()
                    .filter(flow ->
                            flow.getOperatorId().equals(operatorId) && flow.getState().equals(Constant.FLOW_STATE_PROCESS))
                    .toList();
            if(processList.isEmpty()){
                throw new RuntimeException("该请假没有流程");
            }
            ProcessFlow processFlow = processList.get(0);
            processFlow.setState(Constant.FLOW_STATE_COMPLETE);
            processFlow.setReason(reason);
            processFlow.setResult(result);
            processFlow.setAuditTime(LocalDateTime.now());
            processFlowMapper.updateFlow(processFlow);

            LeaveForm leaveForm = leaveFormMapper.selectLeaveFormById(formId);
            //判断是不是最后一个流程

            if(processFlow.getIsLast().equals(1)){
                leaveForm.setState(result);
                leaveFormMapper.updateLeaveForm(leaveForm);
                Employee follower=employeeService.queryEmployeeById(leaveForm.getEmployeeId());

                Department followerDepartment =departmentService.queryDepartmentById(follower.getDepartmentId());
                //TODO 发送Notice，两条通知
                String notice1 = String.format("%s-%s提起请假申请[%s-%s]您以处理,审批意见:%s,审批流程已结束",
                        followerDepartment.getDepartmentName(),
                        follower.getName(),
                        DateUtils.dateFormat(leaveForm.getStartTime()),
                        DateUtils.dateFormat(leaveForm.getEndTime()),
                        leaveForm.getState()
                )
                        ;
                noticeService.sendNotice(employee.getEmployeeId(), notice1);
                String notice2 = String.format("您提起请假申请[%s-%s]审批完毕,审批意见:%s",

                        DateUtils.dateFormat(leaveForm.getStartTime()),
                        DateUtils.dateFormat(leaveForm.getEndTime()),
                        leaveForm.getState()
                );
                noticeService.sendNotice(follower.getEmployeeId(), notice2);
            }else{
                List<ProcessFlow> readyFlowList = allFlows.stream()
                        .filter(flow ->
                                flow.getState().equals(Constant.FLOW_STATE_READY))
                        .toList();
                if(readyFlowList.isEmpty()){
                    throw new RuntimeException("没有待处理的流程");
                }
                ProcessFlow readyFlow = readyFlowList.get(0);
                if(result.equals(Constant.FLOW_RESULT_APPROVED)){
//前一条流程通过
                    readyFlow.setState(Constant.FLOW_STATE_PROCESS);
                    processFlowMapper.updateFlow(readyFlow);
                    //TODO 发通知，三条通知
                    Employee leader=employeeService.getLeader(employee.getEmployeeId());

                    Department leaderDepartment =departmentService.queryDepartmentById(leader.getDepartmentId());
                    Employee follower=employeeService.queryEmployeeById(leaveForm.getEmployeeId());

                    Department followerDepartment =departmentService.queryDepartmentById(follower.getDepartmentId());
                    String notice1 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续审批",
                            employeeDepartment.getDepartmentName(),
                            employee.getName(),
                            DateUtils.dateFormat(leaveForm.getStartTime()),
                            DateUtils.dateFormat(leaveForm.getEndTime()),
                            leaveForm.getState()
                    );
                    noticeService.sendNotice(employee.getEmployeeId(), notice1);
                    String notice2 = String.format("您的请假申请[%s-%s],已移交给%s-%s审批",

                            DateUtils.dateFormat(leaveForm.getStartTime()),
                            DateUtils.dateFormat(leaveForm.getEndTime()),
                            leaderDepartment.getDepartmentName(),
                            leader.getName()
                    );
                    noticeService.sendNotice(follower.getEmployeeId(), notice2);
                    String notice3 = String.format("%s-%s提起请假申请[%s-%s],请您审批",
                            employeeDepartment.getDepartmentName(),
                            employee.getName(),
                            DateUtils.dateFormat(leaveForm.getStartTime()),
                            DateUtils.dateFormat(leaveForm.getEndTime()));
                    noticeService.sendNotice(leader.getEmployeeId(), notice3);
                }else{
                    readyFlowList.forEach(flow ->{
                        flow.setState(Constant.FLOW_STATE_CANCEL);
                        processFlowMapper.updateFlow(readyFlow);
                    });
                    //修改请假单状态
                    leaveForm.setState(result);
                    leaveFormMapper.updateLeaveForm(leaveForm);
                    //TODO 发通知
                    Employee follower=employeeService.queryEmployeeById(leaveForm.getEmployeeId());

                    Department followerDepartment =departmentService.queryDepartmentById(follower.getDepartmentId());
                    String notice1 = String.format("%s-%s提起请假申请[%s-%s]您以处理,审批意见:%s,审批流程已结束",
                            followerDepartment.getDepartmentName(),
                            follower.getName(),
                            DateUtils.dateFormat(leaveForm.getStartTime()),
                            DateUtils.dateFormat(leaveForm.getEndTime()),
                            leaveForm.getState()
                    )
                            ;
                    noticeService.sendNotice(employee.getEmployeeId(), notice1);
                    String notice2 = String.format("您提起请假申请[%s-%s]审批完毕,审批意见:%s",

                            DateUtils.dateFormat(leaveForm.getStartTime()),
                            DateUtils.dateFormat(leaveForm.getEndTime()),
                            leaveForm.getState()
                    );
                    noticeService.sendNotice(follower.getEmployeeId(), notice2);
                }

            }


            return null;
        });
    }

}


