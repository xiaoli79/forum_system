package com.forum_system.service;

import com.forum_system.model.Article;
import com.forum_system.model.Board;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {


//    新增帖子！！
    @Transactional
    void create(Article article);



    List<Article> selectAll();




    List<Article> selectByBoardId(long boardId);


    Article selectDetailById( long id);

    /**
     * 这里面是帖子id
     * @param id
     * @return
     */

    Article selectById(long id);

    void modify(long id ,String title,String content);

    void thumpsUpById(long id);


    void deleteById(long id);

    void addOneReplyCountById(long id);


    List<Article> selectByUserId(Long userId);





}
