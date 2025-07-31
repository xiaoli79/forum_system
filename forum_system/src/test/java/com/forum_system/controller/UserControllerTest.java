package com.forum_system.controller;

import com.forum_system.model.User;
import com.forum_system.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserServiceImpl userService;


//  加上这个注解回发生事务的回滚，不会污染数据库中的数据！！
    @Transactional
    @Test
    void login() {
        System.out.println(11111);
        User user = userService.login("nazal", "222");
        System.out.println(user);

    }
}