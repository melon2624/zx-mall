package com.zx.user.service.convert;

import com.zx.framework.common.model.UserLoginInfo;
import com.zx.user.model.entity.User;
import org.mapstruct.Mapper;

/**
 * (User)表服务接口
 * @author zhangxin
 * @date 2022/12/13 15:29
 */
@Mapper(componentModel = "spring")
public interface UserConvert {


    UserLoginInfo toUserLoginInfo(User user);

}
