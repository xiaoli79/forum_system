package com.forum_system.dao;

import com.forum_system.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ArticleReplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleReply row);

    int insertSelective(ArticleReply row);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply row);

    int updateByPrimaryKey(ArticleReply row);

    List<ArticleReply> selectByArticleId(Long articleId);
}