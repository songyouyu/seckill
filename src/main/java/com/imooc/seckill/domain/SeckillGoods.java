package com.imooc.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀商品表
 * @author youyusong
 * @date 2018/12/28
 */
@Data
public class SeckillGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
