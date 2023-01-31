package com.zx.mall.trade.service;

import com.zx.framework.web.result.Assert;
import com.zx.mall.trade.exception.TradeExceptionCodeEnum;
import com.zx.user.api.UserDubboService;
import com.zx.user.api.vo.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author zhangxin
 * @date 2023/1/30 10:17
 */
@Slf4j
@Service("userService")
public class UserService {

    @DubboReference
    UserDubboService userDubboService;

    /**
     * 根据用户ID查询用户信息，获取不到则返回空
     *
     * @param userId：用户ID
     * @return com.zx.user.api.vo.UserVO
     * @author zhangxin
     * @date 2023/1/30
     */
    public UserDO getUserById(Long userId) {

        try {
            return userDubboService.getUserDetailInfoById(userId);
        } catch (Exception e) {
            log.error("调用服务失败", e);
            return null;
        }
    }

    /**
     * 根据用户ID查询用户信息，获取不到则抛出异常
     *
     * @param userId：用户ID
     * @return com.zx.user.api.vo.UserVO
     * @author zhangxin
     * @date 2023/1/30
     */
    public UserDO getUserInfoByIdOrThrow(Long userId) {

        UserDO userDO = this.getUserById(userId);
        Assert.notNull(userDO, TradeExceptionCodeEnum.USER_EXCEPTION);
        return userDO;
    }


}
