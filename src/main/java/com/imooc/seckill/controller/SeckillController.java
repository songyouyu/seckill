package com.imooc.seckill.controller;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.SeckillOrder;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.service.OrderService;
import com.imooc.seckill.service.SeckillService;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 秒杀 controller
 * @author youyusong
 * @date 2018/12/28
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private SeckillService seckillService;

    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> seckill(Model model, SeckillUser user,
                       @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return  Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(user, goods);
        return Result.success(orderInfo);
    }

}
