package com.imooc.seckill.controller;

import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品列表 controller
 * @author youyusong
 * @date 2018/12/28
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 商品列表
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String list(Model model, SeckillUser user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /**
     * 根据商品 id 查看商品详情
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,SeckillUser user,
                         @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;
        // 秒杀还没开始，倒计时
        if (now < startAt) {
            seckillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } // 秒杀已经结束
        else if (now > endAt) {
            seckillStatus = 2;
            remainSeconds = -1;
        } // 秒杀进行中
        else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
