package com.zx.user.model.constant;

/**
 * @author zhangxin
 * @date 2022/12/6 16:45
 */
public class RedisKeysConstants {

    private static final String APP_KEY = "horse-user:";

    public static final String LOGIN_VERIFICATION_CODE = APP_KEY + "login-verification-code:";

    public static final String LOGIN_VERIFICATION_CODE_INTERVAL = APP_KEY + "login-verification-code-interval:";

}
