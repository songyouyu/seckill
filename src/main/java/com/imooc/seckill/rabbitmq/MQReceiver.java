package com.imooc.seckill.rabbitmq;

import com.imooc.seckill.domain.SeckillOrder;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.service.OrderService;
import com.imooc.seckill.service.SeckillService;
import com.imooc.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author youyusong
 * @date 2018/12/31
 */
@Slf4j
@Service
public class MQReceiver {

    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private SeckillService seckillService;

    @RabbitListener(queues=MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        SeckillMessage mm  = RedisService.stringToBean(message, SeckillMessage.class);
        SeckillUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goods);
    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        log.info("QUEUE : {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        log.info(" topic queue1 message : " + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        log.info(" topic queue2 message : " + message);
//    }
//
//    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message) {
//        log.info(" header queue message : " + new String(message));
//    }

}
