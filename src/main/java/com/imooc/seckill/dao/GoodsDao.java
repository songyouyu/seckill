package com.imooc.seckill.dao;

import com.imooc.seckill.domain.SeckillGoods;
import com.imooc.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author youyusong
 * @date 2018/12/28
 */
@Mapper
public interface GoodsDao {

    /**
     * 获得商品列表
     * @return
     */
    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    /**
     * 通过商品 id 获取商品
     * @param goodsId
     * @return
     */
    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);

    /**
     * 减少库存
     * @param g
     * @return
     */
    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SeckillGoods g);

}
