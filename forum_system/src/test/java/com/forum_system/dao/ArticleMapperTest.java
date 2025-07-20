package com.forum_system.dao;

import com.forum_system.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;


    @Test
    void insert() {



    }

    @Test
    void insertSelective() {
        Article article = new Article();
        article.setBoardid(2L);
        article.setUserid(1L);
        article.setTitle("无忧无虑");
        article.setContent("人这一辈子开心就好！！");
        article.setVisitcount(520);
        articleMapper.insertSelective(article);

    }

    @Test
    void updateByPrimaryKeySelective() {
    }
}