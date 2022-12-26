package com.zx.user.model.vo;

import com.zx.framework.common.model.UserLoginInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangxin
 * @date 2022/11/30 19:14
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginSuccessVo {

    @ApiModelProperty("用户基本信息")
    private UserLoginInfo user;

    @ApiModelProperty("token")
    private String  token;


}
