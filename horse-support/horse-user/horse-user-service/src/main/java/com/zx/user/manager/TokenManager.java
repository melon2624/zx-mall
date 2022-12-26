package com.zx.user.manager;

import com.alibaba.nacos.common.utils.Md5Utils;
import com.zx.framework.common.model.UserLoginInfo;
import com.zx.framework.redis.RedisClient;
import com.zx.user.api.mq.body.UserTokenLogoutMessageBody;
import com.zx.user.auth.JwtProperties;
import com.zx.user.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private JwtProperties jwtProperties;

 /*   @Resource
    private UserTokenLogoutMessage userTokenLogoutMessage;*/


    public Map<String, UserLoginInfo> getUserTokenMap(Long userId) {
        return redisClient.getMap(USER_TOKEN_MAP.concat(String.valueOf(userId)));
    }

    public void removeToken(UserLoginInfo userLoginInfo) {
        if (Objects.nonNull(userLoginInfo.getToken())) {
            redisClient.deleteHash(USER_TOKEN_MAP.concat(String.valueOf(userLoginInfo.getId())), userLoginInfo.getToken());
        }
        UserTokenLogoutMessageBody userTokenLogoutMessageBody = new UserTokenLogoutMessageBody()
                .setUserId(userLoginInfo.getId())
                .setToken(userLoginInfo.getToken())
                .setSessionId(userLoginInfo.getSessionId())
                .setSystemId(userLoginInfo.getSystemId())
                .setClientId(userLoginInfo.getClientId());
        //TODO
        //userTokenLogoutMessage.product(userTokenLogoutMessageBody);

    }

    public UserLoginInfo generateUserTokenAndSessionWriteRedis(UserLoginInfo userLoginInfo) {

        Long userId = userLoginInfo.getId();
        HashMap<String, Object> stringObjectsHashMap = new HashMap<>(1);

        stringObjectsHashMap.put("id", userId);
        stringObjectsHashMap.put("time", System.currentTimeMillis());

        String jwtToken = JwtUtils.generateToken(stringObjectsHashMap, jwtProperties.getPrivateKey());

        userLoginInfo.setToken(jwtToken);
        userLoginInfo.setSessionId(Md5Utils.getMD5(jwtToken.getBytes()));
        userLoginInfo.setTokenExpireTime(LocalDateTime.now().plusMinutes(jwtProperties.getExpiration()));

        String key = USER_TOKEN_MAP.concat(userId.toString());

        redisClient.putMap(key, jwtToken, userLoginInfo);

        //清除过期的会话
        cleanExpireUserSession(userId);

        getExpireLongestTime(userId).ifPresent(localDateTime -> {
            redisClient.expire(key, Duration.between(LocalDateTime.now(), localDateTime).toMinutes() + jwtProperties.getRefreshExpiration(), TimeUnit.MINUTES);
        });
        return userLoginInfo;
    }

    private Optional<LocalDateTime> getExpireLongestTime(Long userId) {
        Map<String, UserLoginInfo> userTokenMap = getUserTokenMap(userId);
        return userTokenMap.values().stream().map(UserLoginInfo::getTokenExpireTime).max(LocalDateTime::compareTo);
    }

    private void cleanExpireUserSession(Long userId) {
        Map<String, UserLoginInfo> userTokenMap = getUserTokenMap(userId);
        userTokenMap.forEach((token, userTokenInfo) -> {
         /*   if (LocalDateTime.now().isAfter(userTokenInfo.getTokenExpireTime().plusDays(7))){
                removeToken(userTokenInfo);
            }*/
            if (LocalDateTime.now().isAfter(userTokenInfo.getTokenExpireTime())) {
                removeToken(userTokenInfo);
            }
        });
    }


}
