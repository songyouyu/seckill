package com.imooc.seckill.dao;

import com.imooc.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Mapper
public interface UserDao {

    /**
     * 通过 id 获取用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id")int id	);

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Insert("insert into user(name)values(#{name})")
    int insert(User user);

}
