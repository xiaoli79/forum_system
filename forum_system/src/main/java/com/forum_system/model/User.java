package com.forum_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;


@Data
public class User {
        private Long id;

        private String username;
//      加入Json后，不再参与Json的序列化
        @JsonIgnore
        private String password;

        private String nickname;

        private String phonenum;

        private String email;

        private Byte gender;
        @JsonIgnore
        private String salt;
        @JsonInclude(JsonInclude.Include.ALWAYS)
        private String avatarurl;

        private Integer articlecount;

        private Byte isadmin;

        private String remark;

        private Byte state;
        @JsonIgnore
        private Byte deletestate;

        private Date createtime;

        private Date updatetime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username == null ? null : username.trim();
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname == null ? null : nickname.trim();
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum == null ? null : phonenum.trim();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email == null ? null : email.trim();
        }

        public Byte getGender() {
            return gender;
        }

        public void setGender(Byte gender) {
            this.gender = gender;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt == null ? null : salt.trim();
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl == null ? null : avatarurl.trim();
        }

        public Integer getArticlecount() {
            return articlecount;
        }

        public void setArticlecount(Integer articlecount) {
            this.articlecount = articlecount;
        }

        public Byte getIsadmin() {
            return isadmin;
        }

        public void setIsadmin(Byte isadmin) {
            this.isadmin = isadmin;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark == null ? null : remark.trim();
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