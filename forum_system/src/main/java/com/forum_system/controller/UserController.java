package com.forum_system.controller;


import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.model.User;
import com.forum_system.service.Impl.UserServiceImpl;
import com.forum_system.utils.MD5Util;
import com.forum_system.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping("/register")
    public AppResult register(String username,String nickname,String password,String passwordRepeat) {
        if(!StringUtils.hasLength(username)){
            return AppResult.failed("用户名为空");
        }
        if(!StringUtils.hasLength(nickname)){
            return AppResult.failed("昵称为空");
        }
        if(!StringUtils.hasLength(password)){
            return AppResult.failed("密码为空");
        }
        if(!StringUtils.hasLength(passwordRepeat)){
            return AppResult.failed("请再次输入密码");
        }
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);

        if(!password.equals(passwordRepeat)){
//         记录日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
//         返回错误信息
            return AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE);
        }
//        加密密码
        String salt = UUIDUtils.UUID_32();
        String encryptPassword = MD5Util.md5Salt(password, salt);
//        设置密码
        user.setPassword(encryptPassword);
        user.setSalt(salt);
        userServiceImpl.createNormalUser(user);

//        成功
        return AppResult.success();


    }





}
