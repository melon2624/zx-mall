package com.zx.framework.web.safety.repeat;


import com.alibaba.fastjson.JSON;
import com.zx.framework.common.context.UserContext;
import com.zx.framework.common.exception.BaseResultCodeEnum;
import com.zx.framework.common.exception.BizException;
import com.zx.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author liao
 */
@Aspect
@Component
@Slf4j
public class RepeatRequestAspect {

    private static final String REPEAT_REQUEST_REDIS_KEY = "repeat_request_redis_key:";

    @Resource
    private RedisClient redisClient;

    @Around("@annotation(com.zx.framework.web.safety.repeat.RepeatRequestIntercept)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        Long userId = UserContext.getUserId();
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String requestInfo = joinPoint.getTarget() + method.getName() + userId + userId + JSON.toJSONString(args);

        String md5 = DigestUtils.md5DigestAsHex(requestInfo.getBytes());
        String lockKey = REPEAT_REQUEST_REDIS_KEY.concat(md5);
        Boolean lockFlag = false;
        try {
            lockFlag = redisClient.setNx(lockKey, md5);
            if (Boolean.TRUE.equals(lockFlag)) {
                return joinPoint.proceed();
            } else {
                throw new BizException(BaseResultCodeEnum.REPETITIVE_OPERATION);
            }
        } finally {
            if (Boolean.TRUE.equals(lockFlag)) {
                redisClient.delete(lockKey);
            }
        }
    }
}
