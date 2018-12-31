package com.imooc.seckill.controller;

import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户 controller
 * @author youyusong
 * @date 2018/12/29
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<SeckillUser> getInfo(Model model, SeckillUser user) {
        return Result.success(user);
    }

}
