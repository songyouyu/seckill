package com.imooc.seckill.service;

import com.imooc.seckill.dao.SeckillUserDao;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.exception.GlobalException;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.redis.SeckillUserKey;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.util.MD5Util;
import com.imooc.seckill.util.UUIDUtil;
import com.imooc.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Slf4j
@Service
public class SeckillUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Resource
    SeckillUserDao seckillUserDao;

    @Resource
    RedisService redisService;

    public SeckillUser getById(long id) {
        return seckillUserDao.getById(id);
    }


    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        log.info("登录成功");
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
