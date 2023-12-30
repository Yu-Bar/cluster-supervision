package com.ustc.web.service.impl;
/**
 * Author:Yu-Bar
 * Date:2023/12/31-0:26
 */

import cn.hutool.core.bean.BeanUtil;
import com.ustc.constant.MessageConstant;
import com.ustc.domain.dto.UserLoginDTO;
import com.ustc.domain.dto.UserSignDTO;
import com.ustc.domain.po.User;
import com.ustc.exception.AccountNotFoundException;
import com.ustc.exception.LoginFailedException;
import com.ustc.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *@ClassName UserServiceImpl
 *@Description 用户有关的业务方法
 *@Author Yu-Bar
 *@Date 2023/12/31 0:26
 *@Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RedisTemplate<String,User> redisTemplate;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User user = redisTemplate.opsForValue().get("username::" + userLoginDTO.getUsername());
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!user.getPassword().equals(userLoginDTO.getPassword())){
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public User sign(UserSignDTO userSignDTO) {
        User user = new User();
        BeanUtil.copyProperties(userSignDTO,user);
        synchronized (this){
            Long userId = (long) (redisTemplate.keys("user::*").size() + 1);
            user.setId(userId);
            redisTemplate.opsForValue().set("user::" + userId, user);
        }
        redisTemplate.opsForValue().set("username::" + user.getUsername(), user);
        return user;
    }
}
