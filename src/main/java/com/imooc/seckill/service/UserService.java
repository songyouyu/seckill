package com.imooc.seckill.service;

import com.imooc.seckill.dao.UserDao;
import com.imooc.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author youyusong
 * @date 2018/12/27
 */
@Service
public class UserService {

    @Resource
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insert() {
        User u1= new User();
        u1.setName("2222");
        userDao.insert(u1);

        User u2= new User();
        u2.setName("11111");
        userDao.insert(u2);

        return true;
    }

}
