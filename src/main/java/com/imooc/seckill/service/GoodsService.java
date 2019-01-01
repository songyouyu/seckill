package com.imooc.seckill.service;

import com.imooc.seckill.dao.GoodsDao;
import com.imooc.seckill.domain.SeckillGoods;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品 service
 * @author youyusong
 * @date 2018/12/28
 */
@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    /**
     * 获得商品列表
     * @return
     */
    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    /**
     * 根据商品 id 获得商品详情
     * @param goodsId
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 减少库存
     * @param goods
     */
    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods g = new SeckillGoods();
        g.setGoodsId(goods.getId());
        int ret =  goodsDao.reduceStock(g);

        return ret > 0;
    }

}
