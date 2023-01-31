package com.zx.third.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.third.entity.ThirdInfo;
import com.zx.third.mapper.ThirdInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhangxin
 * @date 2023/1/30 17:13
 */
@Slf4j
@Service("thirdInfoService")
public class ThirdInfoService extends ServiceImpl<ThirdInfoMapper, ThirdInfo> {


    public ThirdInfo getByAppIdAndUserId(String appId, Long userId) {
        return super.lambdaQuery().eq(ThirdInfo::getAppId,appId).eq(ThirdInfo::getUserId,userId).one();
    }
}
