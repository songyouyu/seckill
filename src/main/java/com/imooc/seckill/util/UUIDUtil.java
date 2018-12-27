package com.imooc.seckill.util;

import java.util.UUID;

/**
 * @author youyusong
 * @date 2018/12/27
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
