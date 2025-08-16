package com.forum_system.model;

import lombok.Data;

import java.util.Date;



@Data
public class Message {
    private Long id;

    private Long postuserid;

    private Long receiveuserid;

    private String content;

    private Byte state;

    private Byte deletestate;

    private Date createtime;

    private Date updatetime;


//  表关联的对象！！
    private User postUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostuserid() {
        return postuserid;
    }

    public void setPostuserid(Long postuserid) {
        this.postuserid = postuserid;
    }

    public Long getReceiveuserid() {
        return receiveuserid;
    }

    public void setReceiveuserid(Long receiveuserid) {
        this.receiveuserid = receiveuserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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