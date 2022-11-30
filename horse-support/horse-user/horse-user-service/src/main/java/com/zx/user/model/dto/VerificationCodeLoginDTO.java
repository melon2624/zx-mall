package com.zx.user.model.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author zhangxin
 * @date 2022/11/30 19:31
 */
public class VerificationCodeLoginDTO extends LoginDTO {

    @NotNull
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull
    @ApiModelProperty("验证码")
    private String verificationCode;


}
