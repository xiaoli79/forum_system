package com.forum_system.service.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;




//单元测试
@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardServiceImpl boardServiceImpl;

    @Test
    void selectByNum() {
        System.out.println(boardServiceImpl.selectByNum(5));

    }
}