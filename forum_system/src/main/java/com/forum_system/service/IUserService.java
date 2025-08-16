package com.forum_system.service;

import com.forum_system.model.User;

public interface IUserService {





    void createNormalUser(User user);



    User login(String username,String password);


    User selectById(Long id);


    void addOneArticleCountById(Long id);


    void subOneArticleCountById(Long id);


    /**
     * 修改个人信息!!
     * @param user
     */
    void modifyUser(User user);



    void modifyPassword(Long id , String newpassword,String oldpassword);




}
