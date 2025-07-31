package com.forum_system.dao;

import com.forum_system.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board row);

    int insertSelective(Board row);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board row);

    int updateByPrimaryKey(Board row);


    @Select("select * from t_board where state = 0 and deleteState = 0 order by sort asc LIMIT ${num}")
    List<Board> selectByNum(Integer num );

    @Select("select * from t_board where state = 0 and deleteState = 0 order by sort asc")
    List<Board> selectAll();


}