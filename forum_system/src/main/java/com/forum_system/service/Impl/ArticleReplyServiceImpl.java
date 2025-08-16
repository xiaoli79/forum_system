package com.forum_system.service.Impl;

import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.ArticleReplyMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.ArticleReply;
import com.forum_system.service.IArticleReplyService;
import com.forum_system.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class ArticleReplyServiceImpl implements IArticleReplyService {

    @Autowired
    private ArticleReplyMapper articleReplyMapper;

    @Autowired
    private IArticleService articleService;


    @Override
    public void createArticleReply(ArticleReply articleReply) {
        if(articleReply == null || articleReply.getPostuserid() == null ||
        articleReply.getContent() == null){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));

        }
        articleReply.setReplyid(null);
        articleReply.setReplyuserid(null);
        articleReply.setLikecount(0);
        articleReply.setState((byte) 0);
        articleReply.setDeletestate((byte) 0);
        Date now = new Date();
        articleReply.setCreatetime(now);
        articleReply.setUpdatetime(now);

        int row = articleReplyMapper.insertSelective(articleReply);
        if(row != 1){
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
//       给回复帖子数+1
        articleService.addOneReplyCountById(articleReply.getArticleid());
        log.info("回复成功！！");
    }

    @Override
    public List<ArticleReply> selectByArticleId(Long articleId) {
        if(articleId == null || articleId < 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        List<ArticleReply> articleReplies = articleReplyMapper.selectByArticleId(articleId);
        if(articleReplies == null || articleReplies.size() < 1){
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        return articleReplies;

    }

}
