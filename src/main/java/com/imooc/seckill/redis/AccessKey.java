package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2019/1/1
 */
public class AccessKey extends BasePrefix {

    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}
