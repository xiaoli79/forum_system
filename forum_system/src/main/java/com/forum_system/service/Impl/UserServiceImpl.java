package com.forum_system.service.Impl;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.UserMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.User;
import com.forum_system.service.IUserService;
import com.forum_system.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //注册的逻辑
    @Override
    public void createNormalUser(User user) {
        if(user == null){
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        val existUser = userMapper.selectByUsername(user.getUsername());
        if(existUser != null){
            log.info(ResultCode.FAILED_USER_EXISTS+"username = "+user.getUsername());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
        }
        if(user.getGender() == null) {
            user.setGender((byte) 2);
            user.setIsadmin((byte) 0);
            user.setAvatarurl(null);
            user.setState((byte) 0);
            user.setDeletestate((byte) 0);
            Date date = new Date();
            user.setCreatetime(date);
            user.setUpdatetime(date);
            int row = userMapper.insertSelective(user);
            if (row != 1) {
                log.info(ResultCode.FAILED_CREATE + "username = " + user.getUsername());
                throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
            }
            log.info("新增用户成功：username = " + user.getUsername());
        }
    }
    @Override
    public User login(String username, String password) {
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

        User user = userMapper.selectByUsername(username);
        if(user == null){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

//        应该是加密算法出问题了！！
//        对密码进行校验！
        if(!user.getPassword().equals(MD5Util.md5Salt(password,user.getSalt()))){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }
        return user;
    }

//    调用接口
    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void addOneArticleCountById(Long id) {


        if(id == 0 || id <0){
            log.warn(ResultCode.FAILED_USER_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_ARTICLE_COUNT));
        }

//调用数据库
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
//        更新板块中的帖子数量
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setArticlecount(user.getArticlecount()+1);
        userMapper.updateByPrimaryKeySelective(updateUser);



    }
}
