package com.zx.user.manager;

import com.model.UserLoginInfo;
import com.zx.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static com.zx.user.model.constant.RedisKeysConstants.USER_TOKEN_MAP;

/**
 * @author zhangxin
 * @date 2022/12/9 19:23
 */
@Slf4j
@Component
public class TokenManager {

    @Resource
    private RedisClient redisClient;

    public Map<String, UserLoginInfo> getUserTokenMap(Long userId) {
        return  redisClient.getMap(USER_TOKEN_MAP.concat(String.valueOf(userId)));
    }
}
