package com.imooc.seckill.config;

import com.imooc.seckill.access.UserContext;
import com.imooc.seckill.domain.SeckillUser;
import com.imooc.seckill.service.SeckillUserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;


/**
 * @author youyusong
 * @date 2018/12/28
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    SeckillUserService seckillUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == SeckillUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        return UserContext.getUser();
    }
}
