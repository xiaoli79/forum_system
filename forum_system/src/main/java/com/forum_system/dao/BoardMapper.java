package com.forum_system.dao;

import com.forum_system.model.Board;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board row);

    int insertSelective(Board row);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board row);

    int updateByPrimaryKey(Board row);
}