package com.imooc.seckill.access;

import com.imooc.seckill.domain.SeckillUser;

/**
 * @author youyusong
 * @date 2019/1/1
 */
public class UserContext {

    private static ThreadLocal<SeckillUser> userHolder = new ThreadLocal<SeckillUser>();

    public static void setUser(SeckillUser user) {
        userHolder.set(user);
    }

    public static SeckillUser getUser() {
        return userHolder.get();
    }

}
