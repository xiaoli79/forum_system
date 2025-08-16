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

    @Test
    void modifyUser() {
        User user = new User();
        user.setId(7L);
        user.setGender((byte) 1);
        user.setNickname("xiaoli");
        user.setEmail("3099592185@qq.com");
        user.setPhonenum("17337191448");
        user.setRemark("我是你爸爸");
        userServiceImpl.modifyUser(user);


    }

    @Test
    void modifyPassword() {
        userServiceImpl.modifyPassword(7L,"123456","222");
    }
}