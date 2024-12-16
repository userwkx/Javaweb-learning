package com.example.classoa.mapper;

import com.example.classoa.entity.Node;

import java.util.List;

public interface NodeMapper {
    List<Node> selectNodeByUserId(Long userId);
}
