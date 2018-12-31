package com.imooc.seckill.vo;

import com.imooc.seckill.domain.OrderInfo;
import lombok.Data;

/**
 * @author youyusong
 * @date 2018/12/31
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;

}
