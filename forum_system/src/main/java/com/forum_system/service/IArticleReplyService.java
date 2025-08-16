package com.forum_system.service;


import com.forum_system.model.ArticleReply;

import java.util.List;

public interface IArticleReplyService {

    void createArticleReply(ArticleReply articleReply);


    List<ArticleReply> selectByArticleId(Long articleId);


}
