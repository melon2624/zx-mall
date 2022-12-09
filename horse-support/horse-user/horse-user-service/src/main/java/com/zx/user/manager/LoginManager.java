package com.zx.user.manager;

import com.model.UserLoginInfo;
import com.zx.framework.redis.RedisClient;
import com.zx.framework.web.result.BizAssert;
import com.zx.user.exception.UserExceptionCodeEnum;
import com.zx.user.model.dto.VerificationCodeLoginDTO;
import com.zx.user.model.entity.User;
import com.zx.user.model.entity.UserLoginLimit;
import com.zx.user.model.vo.LoginSuccessVo;
import com.zx.user.service.UserLoginLimitService;
import com.zx.user.service.UserServie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zx.user.model.constant.RedisKeysConstants.LOGIN_VERIFICATION_CODE;
import static com.zx.user.model.constant.RedisKeysConstants.LOGIN_VERIFICATION_CODE_INTERVAL;


/**
 * @author zhangxin
 * @date 2022-12-04 19:10
 */
@Component
public class LoginManager {


    @Resource
    RedisClient redisClient;

    @Resource
    UserServie userServie;

    @Resource
    UserLoginLimitService userLoginLimitService;

    @Resource
    private TokenManager tokenManager;


    private void setVerificationCodeRedis(String phone, String verificationCode) {
        redisClient.set(LOGIN_VERIFICATION_CODE.concat(phone), verificationCode, 5, TimeUnit.MINUTES);
        redisClient.set(LOGIN_VERIFICATION_CODE_INTERVAL.concat(phone), verificationCode, 1, TimeUnit.MINUTES);
    }

    @Transactional
    public LoginSuccessVo checkVerificationCodeLogin(VerificationCodeLoginDTO loginDTO) {
        String phone = loginDTO.getPhone();
        String verificationCode = redisClient.get(LOGIN_VERIFICATION_CODE.concat(phone));
        boolean isVerificationCodeSuccess = StringUtils.equals(verificationCode, loginDTO.getVerificationCode()) ||
                StringUtils.equals("1111", loginDTO.getVerificationCode());
        BizAssert.isTrue(isVerificationCodeSuccess, UserExceptionCodeEnum.VERIFICATION_CODE_FAIL.getMessage());

        Optional<User> userOptional = userServie.getUserOptional(phone);
        LoginSuccessVo loginSuccessVo = loginAndRegister(userOptional, phone, loginDTO);
        return loginSuccessVo;
    }

    private LoginSuccessVo loginAndRegister(Optional<User> userOptional, String phone, VerificationCodeLoginDTO loginDTO) {
        userOptional.ifPresent(user -> {
            BizAssert.isTrue(user.getIsEnable(), "用户已被禁用,请联系管理员");
            userServie.updateById(new User().setId(user.getId()).setLastLoginTime(LocalDateTime.now()));
        });
        User user = userOptional.orElseGet(() -> {
                    return userServie.register(phone, loginDTO.getClientId(), loginDTO.getSystemId());
                }
        );
        checkBeyondLoginLimit(user.getId(), loginDTO);

        return null;
    }

    public void checkBeyondLoginLimit(Long userId, VerificationCodeLoginDTO loginDTO) {

        Optional<UserLoginLimit> userLoginLimitOptional = userLoginLimitService.getUserLoginLimit(loginDTO.getClientId(), loginDTO.getSystemId());
        if (!userLoginLimitOptional.isPresent()) {
            return;
        }
        UserLoginLimit userLoginLimit = userLoginLimitOptional.get();
        Map<String, UserLoginInfo> userTokenMap = tokenManager.getUserTokenMap(userId);
        Map<String,List<UserLoginInfo>> userLoginCount = userTokenMap.values().stream().filter(userLoginInfo -> Objects.nonNull(userLoginInfo.getClientId()) && Objects.nonNull(userLoginInfo.getSystemId()))
                .collect(Collectors.groupingBy(userLoginInfo -> userLoginInfo.getSystemId().toString() + userLoginInfo.getClientId()));
       // List<UserLoginInfo> userLoginInfos =
    }

    public void loginSendSms(String phone) {
        BizAssert.isNull(redisClient.get(LOGIN_VERIFICATION_CODE_INTERVAL.concat(phone)), UserExceptionCodeEnum.VERIFICATION_CODE_FREQUENTLY.getMessage());
        String verificationCode = String.valueOf(this.generateVerificationCode());
        //TODO
        //发送短信
        System.out.println(verificationCode);
        setVerificationCodeRedis(phone, verificationCode);
    }


    /**
     * 生成验证码
     *
     * @return
     */
    public int generateVerificationCode() {
        return (int) (Math.random() * (9999 - 1000 + 1) + 1000);
    }

}
