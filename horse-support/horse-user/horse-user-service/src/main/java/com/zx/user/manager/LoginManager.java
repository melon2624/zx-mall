package com.zx.user.manager;

import com.zx.framework.redis.RedisClient;
import com.zx.framework.web.result.BizAssert;
import com.zx.user.exception.UserExceptionCodeEnum;
import com.zx.user.model.dto.VerificationCodeLoginDTO;
import com.zx.user.model.vo.LoginSuccessVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.zx.user.model.constant.RedisKeysConstants.LOGIN_VERIFICATION_CODE_INTERVAL;


/**
 * @author zhangxin
 * @date 2022-12-04 19:10
 */
@Component
public class LoginManager {


    @Resource
    RedisClient redisClient;

    private void setVerificationCodeRedis(String phone, String verificationCode) {


    }

    @Transactional
    public LoginSuccessVo checkVerificationCodeLogin(VerificationCodeLoginDTO loginDTO) {

        return null;
    }

    public void loginSendSms(String phone) {
        BizAssert.isNull(redisClient.get(LOGIN_VERIFICATION_CODE_INTERVAL.concat(phone)), UserExceptionCodeEnum.VERIFICATION_CODE_FREQUENTLY.getMessage());
        String verificationCode =String.valueOf(this.generateVerificationCode());
        //TODO  发送短信
        setVerificationCodeRedis(phone,verificationCode);


    }



    /**
     * 生成验证码
     * @return
     */
    public int generateVerificationCode() {
        return (int) (Math.random() * (9999 - 1000 + 1) + 1000);
    }

}
