package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2019/1/1
 */
public class SeckillKey extends BasePrefix {

    private SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SeckillKey isGoodsOver = new SeckillKey(0, "go");

    public static SeckillKey getSeckillPath = new SeckillKey(60, "mp");

    public static SeckillKey getSeckillVerifyCode = new SeckillKey(300, "vc");

}
