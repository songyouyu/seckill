package com.imooc.seckill.service;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        //order_info seckill_order
        return orderService.createOrder(user, goods);
    }
}
