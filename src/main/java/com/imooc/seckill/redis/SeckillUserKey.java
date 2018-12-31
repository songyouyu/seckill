package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2018/12/27
 */
public class SeckillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 * 2;

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE, "tk");

    /**
     * 对象缓存，希望它永久有效
     */
    public static SeckillUserKey getById = new SeckillUserKey(0, "id");
}
