package com.imooc.seckill.rabbitmq;

import com.imooc.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @author youyusong
 * @date 2019/1/1
 */
@Data
public class SeckillMessage {

    private SeckillUser user;

    private long goodsId;

}
