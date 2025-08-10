package com.forum_system.controller;

import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.Board;
import com.forum_system.service.Impl.BoardServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/board")
@RestController
@Slf4j
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

    @RequestMapping("/getById")
    public AppResult<Board> getById(@RequestParam("id") @NonNull Long id) {
        Board board = boardService.selectById(id);
        if(board == null || board.getDeletestate() == 1) {
            log.warn(ResultCode.FAILED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
        return AppResult.success(board);


    }

}
