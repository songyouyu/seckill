package com.imooc.seckill.controller;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.SeckillOrder;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.rabbitmq.MQSender;
import com.imooc.seckill.rabbitmq.SeckillMessage;
import com.imooc.seckill.redis.GoodsKey;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.service.OrderService;
import com.imooc.seckill.service.SeckillService;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 秒杀 controller
 * @author youyusong
 * @date 2018/12/28
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private RedisService redisService;
    @Resource
    private SeckillService seckillService;
    @Resource
    private MQSender sender;

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 系统初始化时加载数据到 redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if(goodsList == null) {
            return;
        }
        for(GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSeckillGoodsStock, ""+goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> seckill(Model model, SeckillUser user,
                       @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return  Result.error(CodeMsg.SESSION_ERROR);
        }

        // 内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, ""+goodsId);
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 入队
        SeckillMessage mm = new SeckillMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendSeckillMessage(mm);

        // 排队中
        return Result.success(0);
    }

    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(Model model,SeckillUser user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result  =seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }
}
