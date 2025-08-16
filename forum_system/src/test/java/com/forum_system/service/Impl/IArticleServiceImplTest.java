package com.forum_system.service.Impl;

import com.forum_system.model.Article;
import com.forum_system.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class IArticleServiceImplTest {
    @Autowired
    private IArticleService iArticleService;

    @Test
    void create() {
        Article article = new Article();
        article.setUserid(1L);
        article.setBoardid(1L);
        article.setTitle("测试");
        article.setContent("<测试用例");
        iArticleService.create(article);
        System.out.println("发帖成功");

    }

    @Test
    void selectAll() {
        System.out.println();
    }

    @Test
    void selectByBoardId() {
        System.out.println(iArticleService.selectByBoardId(2L));
    }

    @Test
    void selectDetailById() {
        System.out.println(iArticleService.selectDetailById(100L));
    }

    @Test
    void thumpsUpById() {
       iArticleService.thumpsUpById(3L);
    }

    @Test
    void deleteById() {
        iArticleService.deleteById(7L);
    }

    @Test
    void addOneReplyCountById() {
        iArticleService.addOneReplyCountById(1L);
    }

    @Test
    void selectByUserId() {
        System.out.println(iArticleService.selectByUserId(1L));
    }
}