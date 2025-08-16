package com.forum_system.controller;

import com.forum_system.common.AppConfig;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.model.Article;
import com.forum_system.model.ArticleReply;
import com.forum_system.model.User;
import com.forum_system.service.IArticleReplyService;
import com.forum_system.service.IArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reply")
public class ArticleReplyController {


    @Autowired
    private IArticleReplyService articleReplyService;
    @Autowired
    private IArticleService articleService;

    @RequestMapping("/create")
    public AppResult create(HttpServletRequest request,
                            @NonNull Long articleId,
                            @NonNull String content) {


//      从session中获取用户信息！！
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(AppConfig.USER_SESSION);
//      判断用户是否被禁言！！
        if(user.getState() == 1){
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Article article = articleService.selectById(articleId);
//       判断帖子已经删除或者不存在！！
        if(article == null || article.getDeletestate() == 1) {
            log.warn(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS).toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
//       构建回复帖子的信息
        ArticleReply articleReply = new ArticleReply();
        articleReply.setContent(content);
        articleReply.setArticleid(articleId);
        articleReply.setPostuserid(user.getId());

//        调用Service实现后续逻辑，进而写入数据库！！
        articleReplyService.createArticleReply(articleReply);

        return AppResult.success();

    }


    @RequestMapping("/getReplies")
    public AppResult<List<ArticleReply>>  getRepliesByArticleId (@RequestParam("articleId") @NonNull Long articleId) {
        Article article = articleService.selectById(articleId);
        if(article == null || article.getDeletestate() == 1) {
            log.warn(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS).toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        List<ArticleReply> articleReplies = articleReplyService.selectByArticleId(articleId);
        return AppResult.success(articleReplies);
    }

}
