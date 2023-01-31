package com.zx.im.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @date 2023/1/30 13:19
 */
@Data
@ApiModel("自定义消息基础DTO")
public class CustomTypeDTO {
    @ApiModelProperty("自定义消息类型")
    private String customType;

    public CustomTypeDTO(String customType) {
        this.customType = customType;
    }
}
