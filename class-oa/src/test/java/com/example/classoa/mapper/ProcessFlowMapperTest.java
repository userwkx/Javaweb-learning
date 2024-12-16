package com.example.classoa.mapper;

import com.example.classoa.entity.ProcessFlow;
import com.example.classoa.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessFlowMapperTest {

    @Test
    void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper mapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow processFlow = new ProcessFlow();
            processFlow.setFormId(3L);
            processFlow.setOperatorId(3L);
            processFlow.setAction("apply");
            processFlow.setCreateTime(new Date());
            processFlow.setAuditTime(new Date());
            processFlow.setOrderNo(1);
            processFlow.setState("processing");
            processFlow.setIsLast(0);
            mapper.insert(processFlow);

            return null;
        });
    }

    @Test
    void selectByFormId() {
        MyBatisUtils.executeQuery(sqlSession -> {
            ProcessFlowMapper mapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> processFlowList = mapper.selectByFormId(3L);
            System.out.println(processFlowList);
            return processFlowList;
        });
    }

    @Test
    void update() {
        List<ProcessFlow> processFlows = (List<ProcessFlow>) MyBatisUtils.executeQuery(sqlsession -> {
            ProcessFlowMapper mapper = sqlsession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> processFlowList = mapper.selectByFormId(3L);
            System.out.println(processFlowList);
            return processFlowList;
        });

        ProcessFlow processFlow = processFlows.get(0);
        processFlow.setResult("approved");
        processFlow.setReason("同意");
        processFlow.setAuditTime(new Date());
        processFlow.setState("complete");
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper mapper = sqlSession.getMapper(ProcessFlowMapper.class);
            mapper.update(processFlow);
            return null;
        });
    }
}