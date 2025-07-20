package com.forum_system.dao;

import com.forum_system.model.Message;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message row);

    int insertSelective(Message row);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKey(Message row);
}