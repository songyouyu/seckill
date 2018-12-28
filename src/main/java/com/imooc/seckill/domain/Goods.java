package com.imooc.seckill.domain;

import lombok.Data;

/**
 * 商品表
 * @author youyusong
 * @date 2018/12/28
 */
@Data
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
