package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2018/12/27
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

}
