package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2018/12/31
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
