package com.zx.user.controller;

import com.zx.user.manager.LoginManager;
import com.zx.user.model.dto.VerificationCodeLoginDTO;
import com.zx.user.model.vo.LoginSuccessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangxin
 * @date 2022/11/30 14:48
 */

@Api(tags = "登录相关接口")
@RestController
public class UserController {

    @Resource
    LoginManager loginManager;

    @ApiModelProperty("验证码登录，注册")
    public LoginSuccessVo  login(@Validated @RequestBody VerificationCodeLoginDTO loginDTO){
        return loginManager.checkVerificationCodeLogin(loginDTO);

    }

}
