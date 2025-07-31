package com.forum_system.controller;


import com.forum_system.common.AppConfig;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.model.Article;
import com.forum_system.model.User;
import com.forum_system.service.IArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        List<Article> articles = articleService.selectAll();
        if(articles == null){
            articles = new ArrayList<>();
        }
        return AppResult.success(articles);
    }

}
