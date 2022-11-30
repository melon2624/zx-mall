package com.zx.user.controller;

import com.zx.user.model.dto.VerificationCodeLoginDTO;
import com.zx.user.model.vo.LoginSuccessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxin
 * @date 2022/11/30 14:48
 */

@Api(tags = "登录相关接口")
@RestController
public class UserController {



    @ApiModelProperty("验证码登录，注册")
    public LoginSuccessVo  login(@Validated @RequestBody VerificationCodeLoginDTO loginDTO){
        return null;

    }

}
