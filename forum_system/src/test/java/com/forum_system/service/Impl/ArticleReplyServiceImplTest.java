package com.forum_system.service.Impl;
import com.forum_system.model.ArticleReply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ArticleReplyServiceImplTest {

    @Autowired
    private ArticleReplyServiceImpl articleReplyServiceImpl;

    @Test
    void createArticleReply() {
        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleid(6L);
        articleReply.setPostuserid(1L);
        articleReply.setContent("单元测试");
        articleReplyServiceImpl.createArticleReply(articleReply);
        System.out.println("测试成功！！");

    }

    @Test
    void selectByArticleId() {
        articleReplyServiceImpl.selectByArticleId(3L);
    }
}