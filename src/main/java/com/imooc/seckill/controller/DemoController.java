package com.imooc.seckill.controller;

import com.imooc.seckill.domain.User;
import com.imooc.seckill.rabbitmq.MQSender;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.redis.UserKey;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;
    @Resource
    private MQSender sender;

//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//		sender.send("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic() {
//		sender.sendTopic("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout() {
//		sender.sendFanout("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/header")
//    @ResponseBody
//    public Result<String> header() {
//		sender.sendHeader("hello,imooc");
//        return Result.success("Hello，world");
//    }


    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> home() {
        return Result.success("Hello，world");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SESSION_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String  thymeleaf(Model model) {
        model.addAttribute("name", "seckill");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.insert();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User  user  = redisService.get(UserKey.getById, ""+1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user  = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, ""+1, user);
        return Result.success(true);
    }

}
