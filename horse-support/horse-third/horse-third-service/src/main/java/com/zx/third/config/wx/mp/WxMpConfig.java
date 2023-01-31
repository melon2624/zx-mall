package com.zx.third.config.wx.mp;

import com.zx.framework.common.exception.BizException;
import com.zx.framework.web.result.Assert;
import com.zx.third.enums.ThirdExceptionCodeEnum;
import com.zx.third.enums.WxMpAppEnum;
import com.zx.third.enums.WxMpAppMessageTemplateEnum;
import com.zx.third.model.entity.MpApp;
import com.zx.third.model.entity.MpMessageTemplate;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhangxin
 * @date 2023/1/30 15:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpConfig {

    /**
     * 公众号配置列表
     */
    private List<MpApp> configs;

    /**
     * 根据appId和模板枚举获取消息模板
     *
     * @param appId：公众号应用ID
     * @param wxMpAppMessageTemplateEnum：公众号消息模板枚举
     * @return com.zx.third.model.entity.MpMessageTemplate
     * @author zhangxin
     * @date 2023/1/30
     */
    public MpMessageTemplate getMpMessageTemplate(String appId, WxMpAppMessageTemplateEnum wxMpAppMessageTemplateEnum) {
        MpApp mpApp = this.getMpAppByAppId(appId);
        Map<String, MpMessageTemplate> templateMap = mpApp.getTemplateMap();
        MpMessageTemplate mpMessageTemplate = templateMap.get(wxMpAppMessageTemplateEnum.getCode());

        if (Objects.isNull(mpMessageTemplate)) {
            throw new BizException(ThirdExceptionCodeEnum.WX_MP_MESSAGE_TEMPLATE_EXCEPTION);
        }
        return mpMessageTemplate;
    }

    /**
     * 根据appId获取微信配置应用
     *
     * @param appId：公众号应用ID
     * @return com.zx.mall.third.model.entity.MpApp
     * @author zhangxin
     * @date 2023/1/30
     */
    private MpApp getMpAppByAppId(String appId) {
        Assert.isTrue(CollectionUtils.isNotEmpty(configs), ThirdExceptionCodeEnum.WX_MP_EXCEPTION);
        Optional<MpApp> first = configs.stream().filter(mpApp -> Objects.equals(mpApp.getAppId(), appId)).findFirst();
        return first.orElseThrow(() -> new BizException(ThirdExceptionCodeEnum.WX_API_EXCEPTION));
    }

    /**
     * 根据枚举类获取微信配置应用
     *
     * @param wxMpAppEnum：微信公众号枚举
     * @return com.zx.mall.third.model.entity.MpApp
     * @author zhangxin
     * @date 2023/1/30
     */
    public MpApp getMpAppByMpEnum(WxMpAppEnum wxMpAppEnum) {
        Assert.isTrue(CollectionUtils.isNotEmpty(configs), ThirdExceptionCodeEnum.WX_MP_EXCEPTION);
        Optional<MpApp> first = configs.stream().filter(mpApp -> Objects.equals(mpApp.getAppCode(), wxMpAppEnum.getAppCode())).findFirst();
        return first.orElseThrow(()->new BizException(ThirdExceptionCodeEnum.WX_APPID_EXCEPTION));
    }
}
