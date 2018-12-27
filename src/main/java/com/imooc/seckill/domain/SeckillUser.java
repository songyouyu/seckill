package com.imooc.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Data
public class SeckillUser {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

}
