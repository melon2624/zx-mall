package com.zx.third.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhangxin
 * @date 2023/1/30 15:18
 */
@Data
@ApiModel("公众号消息模板")
public class MpMessageTemplate {

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 页面地址
     */
    private String url;

    /**
     * 消息标题
     */
    private String first;

    /**
     * 消息备注
     */
    private String remark;

}
