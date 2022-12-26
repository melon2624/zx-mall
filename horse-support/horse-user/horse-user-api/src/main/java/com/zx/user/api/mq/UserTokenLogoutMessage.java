package com.zx.user.api.mq;

import com.zx.framework.common.mq.MqTopic;
import com.zx.framework.common.mq.annotation.Topic;
import com.zx.user.api.mq.body.UserTokenLogoutMessageBody;

/**
 * 用户下线消息
 *
 * @author zhangxin
 * @date 2022/12/13 14:48
 */
@Topic("user_token_logout")
public interface UserTokenLogoutMessage extends MqTopic<UserTokenLogoutMessageBody> {
}
