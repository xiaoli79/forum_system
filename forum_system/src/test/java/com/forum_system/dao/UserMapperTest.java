package com.forum_system.dao;

import com.forum_system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class UserMapperTest {


    @Autowired
    private UserMapper userMapper;

    @Test
    void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }
}