package com.imooc.seckill.dao;

import com.imooc.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 根据用户 id 更新用户密码
     * @param toBeUpdate
     */
    @Update("update seckill_user set password = #{password} where id = #{id}")
    void update(SeckillUser toBeUpdate);

}
