package com.forum_system.dao;

import com.forum_system.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

//    根据ID来查询用户的信息
    @Select("select * from t_user where id = #{id}")
    User selectById(@Param("id") long id);

//    根据用户名来查询用户的信息~
    @Select("select * from t_user where username = #{username}")
    User selectByUsername(@Param("username") String username);




}