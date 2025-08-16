package com.forum_system.service;

import com.forum_system.model.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMessageService {
    /**
     * 发送站内信！
     * @param message
     */

    void create(Message message);


    Integer selectUnReadCount(Long id);


    List<Message> selectByReceiveUserId (Long receiveUserId);


    /**
     * 更新指定站内信的状态
     * @param id
     * @param state
     */
    void selectStateById(Long id, Byte state);


    /**
     * 回复站内信
     * @param repliedId
     * @param message
     */

    @Transactional
    void reply(Long repliedId, Message message);
}
