package com.zx.third.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author zhangxin
 * @date 2023/1/30 14:51
 */
@Getter
public enum WxMpAppEnum implements Serializable {

    /**
     * 马士兵严选公众号
     */
    YANXUAN("yanxuan", "马士兵严选");

    private static final long serialVersionUID = 1L;

    private final String appCode;
    private final String appName;

    WxMpAppEnum(String appCode, String appName) {
        this.appCode = appCode;
        this.appName = appName;
    }

}
