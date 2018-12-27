package com.imooc.seckill.controller;

import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.SeckillUserService;
import com.imooc.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    SeckillUserService seckillUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        // 登录
        seckillUserService.login(response, loginVo);
        return Result.success(true);
    }

}
