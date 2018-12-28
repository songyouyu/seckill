package com.imooc.seckill.vo;

import com.imooc.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author youyusong
 * @date 2018/12/28
 */
@Data
public class GoodsVo extends Goods {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
