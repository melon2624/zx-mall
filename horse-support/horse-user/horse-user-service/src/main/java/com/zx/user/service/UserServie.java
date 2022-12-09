package com.zx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.user.mapper.UserMapper;
import com.zx.user.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author zhangxin
 * @date 2022/12/7 17:21
 */
@Service("userService")
public class UserServie extends ServiceImpl<UserMapper, User> {


    public Optional<User> getUserOptional(String phone) {
        return super.lambdaQuery().eq(User::getPhone, phone).oneOpt();
    }

    public User register(String phone, Integer clientId, Integer systemId) {
        User user=User.builder().account("").nickname("随机昵称").avatar("").gender(3).phone(phone).password("").idCard("")
                .systemId(systemId).clientId(clientId).isEnable(true).lastLoginTime(LocalDateTime.now()).build();
        this.save(user);
        return user;
    }
}
