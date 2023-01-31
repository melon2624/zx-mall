package com.zx.mall.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @date 2023/1/30 14:36
 */
@Component
@ConfigurationProperties(prefix = "im")
@Data
public class MallImConfig {

    private String secret;
    private String client;
    private Long systemId = 1L;

}
