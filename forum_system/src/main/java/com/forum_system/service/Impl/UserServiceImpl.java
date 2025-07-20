package com.forum_system.service.Impl;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.UserMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.User;
import com.forum_system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

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
        if(user.getGender() == null){
            user.setGender((byte)2);
            user.setIsadmin((byte)0);
            user.setAvatarurl(null);
            user.setState((byte)0);
            user.setDeletestate((byte)0);
            Date date = new Date();
            user.setCreatetime(date);
            user.setUpdatetime(date);
            int row = userMapper.insertSelective(user);
            if(row!=1){
                log.info(ResultCode.FAILED_CREATE+"username = "+user.getUsername());
                throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
            }
            log.info("新增用户成功：username = "+user.getUsername());
        }

    }
}
