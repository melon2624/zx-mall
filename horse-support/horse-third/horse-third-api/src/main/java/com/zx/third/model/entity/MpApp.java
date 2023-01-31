package com.zx.third.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * @author zhangxin
 * @date 2023/1/30 15:17
 */
@Data
@ApiModel("公众号应用")
public class MpApp {

    /**
     * appCode
     */
    private String appCode;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 开发者密码
     */
    private String secret;

    /**
     * 令牌token
     */
    private String token;

    /**
     * 解密密钥
     */
    private String aesKey;

    /**
     * 消息模板Map
     */
    private Map<String, MpMessageTemplate> templateMap;

}
