package com.zx.web.controller;

import com.zx.framework.web.result.IgnoredResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxin
 * @date 2022/12/16 9:50
 */
@Api(tags = "用户模块")
@RestController
public class UserController {

    @IgnoredResultWrapper
    @ApiOperation(value = "Hello方法", notes = "hello,zhangxin")
    @GetMapping("/hello")
    public String hello(@ApiParam(name = "userName", value = "用户名", required = true) String userName) {
        System.out.println(userName);
        return "hello";
    }

    @ApiOperation(value = "query方法", notes = "query,zhangxin")
    @GetMapping("/query")
    public String query(@ApiParam(name = "username", value = "用户名", required = true) String username) {
        System.out.println(username);
        return "query";
    }


}
