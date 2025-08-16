package com.forum_system.controller;


import com.forum_system.common.AppConfig;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.model.Article;
import com.forum_system.model.User;
import com.forum_system.service.IArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private IArticleService articleService;


    @RequestMapping("/create")
//  @RequestParam就是从url中获取相应的值！！
    public AppResult create(HttpServletRequest request,
                            @RequestParam(value = "boardId") Long boardId,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "content") String content) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);

        if (user.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        Article article = new Article();
        article.setBoardid(boardId);
        article.setTitle(title);
        article.setContent(content);
        article.setUserid(user.getId());

        articleService.create(article);
        return AppResult.success();

    }
    @RequestMapping("/getAllByBoardId")
    public AppResult<List<Article>> getAllByBoardId(Long boardId) {
        List<Article> articles;
        if(boardId == null){
            articles = articleService.selectAll();
        }else{
            articles = articleService.selectByBoardId(boardId);
        }
        if(articles == null){
            articles = new ArrayList<>();
        }
        return AppResult.success(articles);
    }



    @RequestMapping("/details")
    public AppResult<Article> details(HttpServletRequest request , @NonNull @RequestParam("id") long id) {

//      从session中拿取user对象
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);

        Article article = articleService.selectDetailById(id);
        if(article == null){
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        if(user.getId() == article.getUserid()){
            article.setOwn(true);
        }
        return AppResult.success(article);
    }





    @RequestMapping("/modify")
    public AppResult modify(HttpServletRequest request,
                            @NonNull @RequestParam("id") long id ,
                            @NonNull @RequestParam("title")  String title ,
                            @NonNull @RequestParam("content") String content ){


        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if(user.getState() == 1){
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        Article article = articleService.selectById(id);
        if(article == null){
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        if(user.getId() != article.getUserid()){
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        if(article.getState() == 1 || article.getDeletestate() == 1){
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }
        articleService.modify(id,title,content);
        return AppResult.success();
    }



    @RequestMapping("/thumbsup")
    public AppResult thumbsup (HttpServletRequest request, long id){
//      校验用户状态
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
//       判断用户是否禁言
        if(user.getState() == 1){
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
//        调用service
        articleService.thumpsUpById(id);
//        返回结果！
        return AppResult.success();

    }


    @RequestMapping("/delete")
    public AppResult deleteById(HttpServletRequest request,@NonNull long id){

//        校验用户状态
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
//        判断用户是否被禁言
        if(user.getState() == 1){
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
//      再次检验其状态
        Article article = articleService.selectById(id);
        if(article == null || article.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

//      调用业务代码
        articleService.deleteById(id);
        return AppResult.success();

    }



    @RequestMapping("/getAllByUserId")
//  userId 可为空!!
    public AppResult<List<Article>> getALLByUserId(HttpServletRequest request,
                                    @RequestParam(value = "userId",required = false) Long userId){
        if(userId == null){
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(AppConfig.USER_SESSION);
            userId = user.getId();
        }
        List<Article> articles = articleService.selectByUserId(userId);
        return AppResult.success(articles);

    }





}
