package com.zx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.user.mapper.UserLoginLimitMapper;
import com.zx.user.model.entity.UserLoginLimit;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhangxin
 * @date 2022/12/9 19:03
 */
@Service("userLoginLimitService")
public class UserLoginLimitService extends ServiceImpl<UserLoginLimitMapper, UserLoginLimit> {


    public Optional<UserLoginLimit> getUserLoginLimit(Integer clientId, Integer systemId) {
        Optional<UserLoginLimit> userLoginLimitOptional = super.lambdaQuery().eq(UserLoginLimit::getSystemClientId, clientId).eq(UserLoginLimit::getSystemId, systemId).oneOpt();
        return userLoginLimitOptional;
    }
}
