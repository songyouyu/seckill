package com.imooc.seckill.service;

import com.imooc.seckill.dao.OrderDao;
import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.SeckillOrder;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.redis.OrderKey;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 订单 service
 * @author youyusong
 * @date 2018/12/28
 */
@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private RedisService redisService;

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 根据商品和用户 id 获取秒杀订单
     * @param userId
     * @param goodsId
     * @return
     */
    public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getSeckillOrderByUidGid, ""+userId+"_"+goodsId, SeckillOrder.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        // 此数据表中添加了唯一联合索引（user_id, goods_id）,防止一个用户同时下两个订单
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);

        redisService.set(OrderKey.getSeckillOrderByUidGid, ""+user.getId()+"_"+goods.getId(), seckillOrder);

        return orderInfo;
    }

}
