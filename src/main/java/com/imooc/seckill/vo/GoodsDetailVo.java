package com.imooc.seckill.vo;

import com.imooc.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @author youyusong
 * @date 2018/12/31
 */
@Data
public class GoodsDetailVo {

    private int seckillStatus = 0;

    private int remainSeconds = 0;

    private GoodsVo goods ;

    private SeckillUser user;

}
