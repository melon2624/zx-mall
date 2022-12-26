package com.zx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.web.util.ServletUtil;
import com.zx.user.mapper.UserLoginRecordMapper;
import com.zx.user.model.entity.UserLoginRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * (UserLoginRecord)表服务实现类
 *
 * @author zhangxin
 * @date 2022/12/14 10:43
 */
@Service("userLoginRecordService")
public class UserLoginRecordService extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> {


    public Boolean save(Long userId, String token, Integer clientId, Integer systemId) {

        String clientIp = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes1 -> ServletUtil.getClientIP(((ServletRequestAttributes) requestAttributes1).getRequest()))
                .orElse("");

        UserLoginRecord userLoginRecord = UserLoginRecord.builder()
                .userId(userId)
                .token(token)
                .systemId(systemId)
                .clientId(clientId)
                .ip(clientIp).build();
        return this.save(userLoginRecord);
    }
}
