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


}
