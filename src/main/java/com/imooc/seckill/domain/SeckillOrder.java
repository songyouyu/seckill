package com.imooc.seckill.domain;

import lombok.Data;

/**
 * 秒杀订单表
 * @author youyusong
 * @date 2018/12/28
 */
@Data
public class SeckillOrder {

    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;

}
