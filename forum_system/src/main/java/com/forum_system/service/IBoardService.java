package com.forum_system.service;

import com.forum_system.model.Board;

import java.util.List;

public interface IBoardService {


    List<Board> selectByNum(Integer num);


    List<Board> selectAll();


    Board selectById(Long id);


    void addOneArticleCountById(Long id);
}
