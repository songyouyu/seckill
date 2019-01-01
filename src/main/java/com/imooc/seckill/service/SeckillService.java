package com.imooc.seckill.service;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.SeckillOrder;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.redis.SeckillKey;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 秒杀 service
 * @author youyusong
 * @date 2018/12/28
 */
@Service
public class SeckillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Resource
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean flag = goodsService.reduceStock(goods);
        if (flag) {
            //order_info seckill_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        // 秒杀成功
        if(order != null) {
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }


}
