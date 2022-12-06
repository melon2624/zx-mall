package com.zx.user.controller;

import com.zx.framework.redis.RedisClient;
import com.zx.user.manager.LoginManager;
import com.zx.user.model.dto.VerificationCodeLoginDTO;
import com.zx.user.model.vo.LoginSuccessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author zhangxin
 * @date 2022/11/30 14:48
 */

@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    LoginManager loginManager;

    @PostMapping("/login")
    @ApiModelProperty("验证码登录，注册")
    public LoginSuccessVo login(@Validated @RequestBody VerificationCodeLoginDTO loginDTO) {
        return loginManager.checkVerificationCodeLogin(loginDTO);

    }

    @ApiOperation("登录发送验证码")
    @GetMapping("login/loginSendVerificationCode")
    public Boolean loginSendVerificationCode(@Validated @NotBlank String phone) throws Exception {
        loginManager.loginSendSms(phone);
        return Boolean.TRUE;
    }


}
