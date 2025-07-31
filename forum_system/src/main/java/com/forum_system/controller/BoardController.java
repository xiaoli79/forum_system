package com.forum_system.controller;

import com.forum_system.common.AppResult;
import com.forum_system.model.Board;
import com.forum_system.service.Impl.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/board")
@RestController
public class BoardController {

    @Value("${bit-forum.index.board-num:9}")
    private Integer indexBoardNum;


    @Autowired
    private BoardServiceImpl boardService;


    @RequestMapping("/toList")
    public AppResult<List<Board>> toList() {
        List<Board> boards = boardService.selectByNum(indexBoardNum);
        return AppResult.success(boards);

    }


    @RequestMapping("/allNormal")
    public AppResult<List<Board>> allNormal() {
        List<Board> boards = boardService.selectAll();
        return AppResult.success(boards);
    }

}
