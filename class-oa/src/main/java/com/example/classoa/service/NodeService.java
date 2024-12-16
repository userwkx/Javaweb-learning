package com.example.classoa.service;

import com.example.classoa.entity.Node;
import com.example.classoa.mapper.NodeMapper;
import com.example.classoa.utils.MyBatisUtils;

import java.util.List;

public class NodeService {
    public List<Node> selectNodeByUserId(Long userId){
    return (List<Node>) MyBatisUtils.executeQuery(sqlSession ->{
        NodeMapper mapper =sqlSession.getMapper(NodeMapper.class);
        return mapper.selectNodeByUserId(userId);
    });
    }
}
