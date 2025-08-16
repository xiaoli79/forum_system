package com.forum_system.service.Impl;

import com.forum_system.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

class MessageServiceImplTest {


    @Autowired
    private MessageServiceImpl messageService;

    @Test
    void create() {
        Message message = new Message();
        message.setPostuserid(1L);
        message.setContent("我最帅");
        message.setReceiveuserid(7L);
        messageService.create(message);
        System.out.println("成功");
    }

    @Test
    void reply() {
        Message message = new Message();
        message.setPostuserid(7L);
        message.setReceiveuserid(9L);
        message.setContent("肥波最帅了");
        messageService.reply(1L, message);
        System.out.println("回复成功");
    }
}