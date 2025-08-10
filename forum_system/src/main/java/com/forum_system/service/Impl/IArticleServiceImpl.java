package com.forum_system.service.Impl;

import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.ArticleMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.Article;
import com.forum_system.model.Board;
import com.forum_system.model.User;
import com.forum_system.service.IArticleService;
import com.forum_system.service.IBoardService;
import com.forum_system.service.IUserService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class IArticleServiceImpl implements IArticleService {
    @Autowired
    private IUserService userService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IBoardService boardService;


    @Override
    public void create(Article article) {
//        非空校验
        if(article == null || article.getUserid() == null ||  article.getBoardid() == null
                || StringUtils.isEmpty(article.getTitle()) || StringUtils.isEmpty(article.getContent())) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }


//        设置默认值
        Date now = new Date();
        article.setVisitcount(0);
        article.setLikecount(0);
        article.setState((byte) 0);
        article.setDeletestate((byte) 0);
        article.setReplycount(0);
        article.setUpdatetime(now);
        article.setCreatetime(now);

        int articleRow = articleMapper.insertSelective(article);
        if(articleRow <= 0) {
            log.warn(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }


//      查询用户的信息
        User user = userService.selectById(article.getUserid());
        if(user == null) {
            log.warn(ResultCode.FAILED_CREATE.toString()+",发帖失败！！");
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }

//       增加用户的发帖数
        userService.addOneArticleCountById(article.getUserid());

//       获取板块信息
        Board board = boardService.selectById(article.getBoardid());
        if(board == null) {
            log.warn(ResultCode.FAILED_CREATE.toString()+",发帖失败！！");
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        boardService.addOneArticleCountById(article.getBoardid());
        log.info(ResultCode.SUCCESS.toString());


    }

    @Override
    public List<Article> selectAll() {
       return articleMapper.selectAll();
    }

    @Override
    public List<Article> selectByBoardId(long boardId) {
        if(boardId == 0 || boardId < 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
//        参看板块是存在，如果存在，则继续下一步
        Board board = boardService.selectById(boardId);
        if(board == null) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString()+"id不存在");
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return articleMapper.selectAllByBoardId(boardId);
    }



//   编写业务代码的逻辑
//    查询帖子的详情信息
    @Override
    public Article selectDetailById(long id) {
//      参数校验
        if(id == 0 || id < 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }


//      查询帖子详情
        Article article = articleMapper.selectDetailById(id);
        if(article == null) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }

//        访问次数+1
        Article updataedArticle =  new Article();
        updataedArticle.setId(id);
        updataedArticle.setVisitcount(article.getVisitcount()+1);
        int row = articleMapper.updateByPrimaryKeySelective(updataedArticle);
        if(row != 1 ) {
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

//      更新返回对象的帖子数量
        article.setVisitcount(article.getVisitcount()+1);
        return article;
    }
}
