package com.zx.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhangxin
 * @date 2022/11/30 19:31
 */
@Data
public class LoginDTO {

    @NotNull
    @ApiModelProperty("登录系统")
    private Integer systemId;


    @NotNull
    @ApiModelProperty("登录来源")
    private Integer clientId;

}
