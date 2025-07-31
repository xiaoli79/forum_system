package com.forum_system.service;

import com.forum_system.model.Article;
import com.forum_system.model.Board;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {


//    新增帖子！！
    @Transactional
    void create(Article article);



    List<Article> selectAll();


}
