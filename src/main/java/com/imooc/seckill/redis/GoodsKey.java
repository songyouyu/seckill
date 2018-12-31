package com.imooc.seckill.redis;

/**
 * @author youyusong
 * @date 2018/12/30
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");

    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
}