package com.zx.user.exception;

import com.zx.framework.common.exception.IResultCode;
import com.zx.framework.common.model.IDict;

/**
 * @author zhangxin
 * @date 2022/12/6 16:52
 */
public enum UserExceptionCodeEnum implements IResultCode, IDict<String> {

    /**
     * 验证码频繁
     */
    VERIFICATION_CODE_FREQUENTLY("VERIFICATION_CODE_FREQUENTLY","验证码频繁"),

    /**
     * 验证码错误
     */
    VERIFICATION_CODE_FAIL("VERIFICATION_CODE_FAIL","验证码错误"),

    /**
     * 验证码发送失败
     */
    VERIFICATION_CODE_SEND_FAIL("VERIFICATION_CODE_SEND_FAIL","验证码发送失败"),
    /**
     * 用户暂不可用
     *
     */
    USER_DISABLE("USER_DISABLE", "用户暂不可用");


    /**
     * 枚举编号
     */
    private final String code;

    /**
     * 枚举详情
     */
    private final String message;


    /**
     * 构造方法
     *
     * @param code    枚举编号
     * @param message 枚举详情
     */
    UserExceptionCodeEnum(String code, String message) {
        init(code, message);
        this.code = code;
        this.message = message;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
