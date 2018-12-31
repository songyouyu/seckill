package com.imooc.seckill.controller;

import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.redis.GoodsKey;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.vo.GoodsDetailVo;
import com.imooc.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private RedisService redisService;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 商品列表(商品列表缓存)
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list2", produces = "text/html")
    @ResponseBody
    public String list2(HttpServletRequest request, HttpServletResponse response, Model model, SeckillUser user) {
        model.addAttribute("user", user);

        // 做页面缓存，页面缓存适合变化不大的
        // 取缓存， 若缓存中有则直接返回
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (! StringUtils.isEmpty(html)) {
            return html;
        }

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        SpringWebContext context = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (! StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }

        return html;
    }

    @RequestMapping(value="/to_list")
    public String list(Model model,SeckillUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /**
     * 根据商品 id 查看商品详情(商品详情缓存)
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model,SeckillUser user,
                         @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }

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

        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }

        return html;
    }


    /**
     * 商品详情静态化
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model,
                                        SeckillUser user, @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;

        if(now < startAt ) {
            seckillStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSeckillStatus(seckillStatus);

        return Result.success(vo);
    }
}
