package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2019/1/1
 */
public class SeckillKey extends BasePrefix {

    private SeckillKey(String prefix) {
        super(prefix);
    }
    public static SeckillKey isGoodsOver = new SeckillKey("go");

}
