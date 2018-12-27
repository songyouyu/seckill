package com.imooc.seckill.dao;

import com.imooc.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Mapper
public interface SeckillUserDao {

    /**
     * 通过 id 查找用户
     * @param id
     * @return
     */
    @Select("select * from seckill_user where id = #{id}")
    SeckillUser getById(@Param("id")long id);

}
