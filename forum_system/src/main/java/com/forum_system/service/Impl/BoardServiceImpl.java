package com.forum_system.service.Impl;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.BoardMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.Board;
import com.forum_system.service.IBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
public class BoardServiceImpl implements IBoardService {


    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Board> selectByNum(Integer num) {
        if(num < 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        List<Board> boards = boardMapper.selectByNum(num);
        return boards;
    }

    @Override
    public List<Board> selectAll() {
        return boardMapper.selectAll();
    }

    @Override
    public Board selectById(Long id) {
        return boardMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addOneArticleCountById(Long id) {
//        校验ID是否输入正确
        if(id == 0 || id <0){
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

//调用数据库
        Board board = boardMapper.selectByPrimaryKey(id);
        if(board == null){
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
//        更新板块中的帖子数量
        Board updateBoard = new Board();
        updateBoard.setId(id);
        updateBoard.setArticlecount(board.getArticlecount()+1);
        boardMapper.updateByPrimaryKeySelective(updateBoard);

    }


    @Override
    public void subOneArticleCountById(Long id) {

        //校验ID是否输入正确
        if(id == 0 || id <0){
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        //调用数据库
        Board board = boardMapper.selectByPrimaryKey(id);
        if(board == null){
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
//      更新板块中的帖子数量
        Board updateBoard = new Board();
        updateBoard.setId(id);
        updateBoard.setArticlecount(board.getArticlecount() - 1);
//      如果<0,需要设置成0 !!
        if(updateBoard.getArticlecount() < 0){
            updateBoard.setArticlecount(0);
        }
        boardMapper.updateByPrimaryKeySelective(updateBoard);

    }
}
