package com.forum_system.dao;

import com.forum_system.model.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message row);

    int insertSelective(Message row);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKey(Message row);

//  站内信
    Integer selectUnReadCount(Long receiveUserId);

//  实现站内信的列表
    List<Message> selectByReceiveUserId(Long receiveUserId);

}