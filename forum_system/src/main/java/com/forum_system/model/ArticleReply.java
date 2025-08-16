package com.forum_system.model;

import lombok.Data;
import org.apache.commons.lang3.text.translate.UnicodeUnpairedSurrogateRemover;

import java.util.Date;


@Data
public class ArticleReply {

//  编号
    private Long id;

//  帖子Id,关联Article
    private Long articleid;

//  回复的用户编号！！
    private Long postuserid;

    private Long replyid;

    private Long replyuserid;

    private String content;

    private Integer likecount;

    private Byte state;

    private Byte deletestate;

    private Date createtime;

    private Date updatetime;


    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleid() {
        return articleid;
    }

    public void setArticleid(Long articleid) {
        this.articleid = articleid;
    }

    public Long getPostuserid() {
        return postuserid;
    }

    public void setPostuserid(Long postuserid) {
        this.postuserid = postuserid;
    }

    public Long getReplyid() {
        return replyid;
    }

    public void setReplyid(Long replyid) {
        this.replyid = replyid;
    }

    public Long getReplyuserid() {
        return replyuserid;
    }

    public void setReplyuserid(Long replyuserid) {
        this.replyuserid = replyuserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getDeletestate() {
        return deletestate;
    }

    public void setDeletestate(Byte deletestate) {
        this.deletestate = deletestate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}