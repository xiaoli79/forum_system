package com.forum_system.service.Impl;

import com.forum_system.dao.UserMapper;
import com.forum_system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {


    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void selectById() {
        User user = userServiceImpl.selectById(1L);
        System.out.println(user);
    }

    @Test
//    回滚数据库的操作
    @Transactional
    void addOneArticleCountById() {
        userServiceImpl.addOneArticleCountById(1L);
    }
}