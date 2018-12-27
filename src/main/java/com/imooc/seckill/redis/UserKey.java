package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2018/12/27
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");
}
