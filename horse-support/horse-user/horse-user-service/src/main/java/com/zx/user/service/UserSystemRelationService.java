package com.zx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.user.mapper.UserSystemRelationMapper;
import com.zx.user.model.entity.UserSystemRelation;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhangxin
 * @date 2022/12/14 11:21
 */
@Service("userSystemRelationService")
public class UserSystemRelationService extends ServiceImpl<UserSystemRelationMapper, UserSystemRelation> {


    public void save(Long userId, Integer systemId) {
        Optional<UserSystemRelation> userSystemRelationOptional = this.lambdaQuery()
                .eq(UserSystemRelation::getUserId, userId).eq(UserSystemRelation::getSystemId, systemId).oneOpt();

        if (!userSystemRelationOptional.isPresent()) {
            UserSystemRelation userSystemRelation = new UserSystemRelation().setUserId(userId)
                    .setSystemId(systemId);
            this.save(userSystemRelation);
        }
    }
}
