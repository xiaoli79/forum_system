package com.forum_system.controller;


import com.forum_system.common.AppConfig;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.model.User;
import com.forum_system.service.Impl.UserServiceImpl;
import com.forum_system.utils.MD5Util;
import com.forum_system.utils.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/login")
    public AppResult login(String username, String password, HttpServletRequest request) {
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AppResult.failed("用户名或者密码错误");
        }
        User user = userServiceImpl.login(username, password);
        if(user == null) {
            return AppResult.failed("<UNK>");
        }
//        设置为true，表示没有session的时候创建一个session
        HttpSession session = request.getSession(true);

        session.setAttribute(AppConfig.USER_SESSION, user);
        return AppResult.success();
    }


//   实现用户退出的功能！！
    @RequestMapping("/logout")
    public AppResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return AppResult.success();
    }

//   呈现用户的信息

    /**
     * id为空,返回当前登录用户 （通过session来获取）
     * id不为空，返回指定id的用户~~
     * @param request
     * @param id
     * @return
     */

    @RequestMapping("/info")
    public AppResult userInfo(HttpServletRequest request,Long id) {
        User user = null;
        if(id == null){
//            从session中获取用户信息·~
           HttpSession session = request.getSession(false);
           user = (User)session.getAttribute(AppConfig.USER_SESSION);
        }else{
            user = userServiceImpl.selectById(id);
        }

        return AppResult.success(user);

    }



//    接收参数,对参数作非空校验!!
    @RequestMapping("/modifyInfo")
    public AppResult modifyInfo(HttpServletRequest request,
                                @RequestParam( value = "username" ,required = false) String username,
                                @RequestParam( value = "nickname" ,required = false) String nickname,
                                @RequestParam( value = "gender" ,required = false) Byte gender,
                                @RequestParam( value = "email" ,required = false) String email,
                                @RequestParam( value = "phoneNum" ,required = false) String phoneNum,
                                @RequestParam( value = "remark" ,required = false) String remark) {

        if(StringUtils.isEmpty(username) && StringUtils.isEmpty(nickname) && gender == null && email == null && phoneNum == null && remark == null
        ){
            return AppResult.failed("请输入要更改的内容");
        }

//      从session中获取用户信息!!
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(AppConfig.USER_SESSION);
        User updateUser = new User();

        updateUser.setId(user.getId());
        updateUser.setUsername(username);
        updateUser.setNickname(nickname);
        updateUser.setGender(gender);
        updateUser.setEmail(email);
        updateUser.setPhonenum(phoneNum);
        updateUser.setRemark(remark);


//      调用service
        userServiceImpl.modifyUser(updateUser);
//        查询最新的用户信息
        user = userServiceImpl.selectById(user.getId());
//        把最新的用户信息设置到session中!!
        session.setAttribute(AppConfig.USER_SESSION, user);
        return AppResult.success(user);
    }


        @RequestMapping("/modifyPwd")
//      修改密码
        public AppResult modifyPassword(HttpServletRequest request,
                                        @RequestParam("oldPassword") @NonNull String oldPassword,
                                        @RequestParam("newPassword") @NonNull String newPassword,
                                        @RequestParam("passwordRepeat") @NonNull String passwordRepeat) {
//         新密码和再次输入的密码是否相同！！
            if(!newPassword.equals(passwordRepeat)){
                return AppResult.failed(ResultCode.FAILED_TWO_PWD_NOT_SAME);
            }
//         从session中获取用户信息
            HttpSession session = request.getSession(false);
            User user = (User)session.getAttribute(AppConfig.USER_SESSION);
//         进行修改密码！
            userServiceImpl.modifyPassword(user.getId(),newPassword,oldPassword);

//          销毁session
            if(session != null){
                session.invalidate();
            }

            return AppResult.success(user);

        }




















}
