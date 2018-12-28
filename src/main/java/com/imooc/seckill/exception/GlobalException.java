package com.imooc.seckill.exception;

import com.imooc.seckill.result.CodeMsg;

/**
 * 异常处理
 * @author youyusong
 * @date 2018/12/27
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
