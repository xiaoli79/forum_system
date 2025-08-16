package com.forum_system.service.Impl;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.UserMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.User;
import com.forum_system.service.IUserService;
import com.forum_system.utils.MD5Util;
import com.forum_system.utils.StringUtil;
import com.forum_system.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

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

    @Override
    public void subOneArticleCountById(Long id) {


        if (id == 0 || id < 0) {
            log.warn(ResultCode.FAILED_USER_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_ARTICLE_COUNT));
        }

        //调用数据库
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
//        更新板块中的帖子数量
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setArticlecount(user.getArticlecount() -1);

//        如果用户中的帖子数量为0的话，就不能再减了，如果要减的话，需要将其变成0！！
        if(updateUser.getArticlecount() < 0){
            updateUser.setArticlecount(0);
        }
        userMapper.updateByPrimaryKeySelective(updateUser);


    }

    @Override
    public void modifyUser(User user) {
//        参数校验
        if(user == null || user.getId() <= 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString().toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

//        从数据库中查询用户是否存在!!
        User existUser = userMapper.selectByPrimaryKey(user.getId());
        if(existUser == null){
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }

//      标志位,用来检测哪些参数发生了变化
        boolean flag = false;
//        专门用来更新的对象!!
        User updateUser = new User();
        updateUser.setId(user.getId());

//      检验session中的user对象,和数据库中的user对象是否一样,如果不一样条件满足!!
        if(StringUtils.hasLength(user.getUsername())
                && !user.getUsername().equals(existUser.getUsername())){
            User checkUser = userMapper.selectByUsername(user.getUsername());
//          如果用户已经存在!!
            if(checkUser != null){
                log.warn(ResultCode.FAILED_USER_EXISTS.toString());
                throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
            }
//          更新用户名!!
            updateUser.setUsername(user.getUsername());
//          把标签位设置成true!
            flag = true;

        }

//      判断nickName!!
        if(!StringUtils.isEmpty(user.getNickname())
           && !existUser.getNickname().equals(user.getNickname())){
            updateUser.setNickname(user.getNickname());
            flag = true;

        }

//      校验性别
        if(!StringUtils.isEmpty(user.getGender())
           && !user.getGender().equals(existUser.getGender())){
            updateUser.setGender(user.getGender());
            flag = true;
        }

//      校验邮箱!!
        if(!StringUtils.isEmpty(user.getEmail())
                && !user.getEmail().equals(existUser.getEmail())){
            updateUser.setEmail(user.getEmail());
            flag = true;
        }


        //校验电话号码!!
        if(!StringUtils.isEmpty(user.getPhonenum())
                && !user.getPhonenum().equals(updateUser.getPhonenum())){
            updateUser.setPhonenum(user.getPhonenum());
            flag = true;
        }

        // 10. 校验个人简介
        if (!StringUtil.isEmpty(user.getRemark())
                && !user.getRemark().equals(existUser.getRemark())) {
            // 设置电话号码
            updateUser.setRemark(user.getRemark());
            // 更新标志位
           flag = true;
        }


        if(flag == false){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString().toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }


        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if(row !=1){
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    @Override
    public void modifyPassword(Long id, String newPassword, String oldPassword) {

//      非空校验!!
        if(id == null || id <= 0 || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(oldPassword)){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString().toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

//       获取用户信息
        User user = userMapper.selectByPrimaryKey(id);
        if( user == null || user.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }


//      密码是否正确
        String oldEncryptPassword =MD5Util.md5Salt(oldPassword,user.getSalt());
        if(!oldEncryptPassword.equals(user.getPassword())){
            log.warn(ResultCode.FAILED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }

//      对新密码进行加密
        String salt = UUIDUtils.UUID_32();
        String encryptNewPassword = MD5Util.md5Salt(newPassword,salt);

//       设置要更新的值
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPassword(encryptNewPassword);
        updateUser.setSalt(salt);
        Date date = new Date();
        updateUser.setUpdatetime(date);
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
//        检验是否更新!!
        if(row != 1){
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }


}
