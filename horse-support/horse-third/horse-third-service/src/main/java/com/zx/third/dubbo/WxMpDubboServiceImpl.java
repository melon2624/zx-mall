package com.zx.third.dubbo;

import com.zx.third.api.WxMpDubboService;
import com.zx.third.config.wx.mp.WxMpConfig;
import com.zx.third.entity.ThirdInfo;
import com.zx.third.enums.WxMpAppMessageTemplateEnum;
import com.zx.third.model.dto.OrderCancelMessageDTO;
import com.zx.third.model.dto.OrderPayMessageDTO;
import com.zx.third.model.dto.TemplateMessageDTO;
import com.zx.third.model.entity.MpApp;
import com.zx.third.model.entity.MpMessageTemplate;
import com.zx.third.service.ThirdInfoService;
import com.zx.third.service.ThirdWxMpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhangxin
 * @date 2023/1/30 15:04
 */
@Slf4j
@DubboService
public class WxMpDubboServiceImpl implements WxMpDubboService {

    @Resource
    private WxMpConfig wxMpConfig;

    @Resource
    ThirdInfoService thirdInfoService;

    @Resource
    ThirdWxMpService thirdWxMpService;

    @Override
    public Boolean sendOrderCancelMessage(OrderCancelMessageDTO orderCancelMessageDTO) {
        log.info("发送订单取消通知，{}", orderCancelMessageDTO);
        String[] params = {orderCancelMessageDTO.getOrderNo(),
                orderCancelMessageDTO.getPayAmount().toString(),
                orderCancelMessageDTO.getProductNames(),
                orderCancelMessageDTO.getCancelReason()};
        return this.sendMessage(orderCancelMessageDTO, WxMpAppMessageTemplateEnum.ORDER_CANCEL, params);
    }

    @Override
    public Boolean sendOrderPayMessage(OrderPayMessageDTO orderPayMessageDTO) {
        log.info("发送订单支付成功模板消息，{}", orderPayMessageDTO);
        String[] params = {
                orderPayMessageDTO.getUserNickName(),
                orderPayMessageDTO.getOrderNo(),
                orderPayMessageDTO.getPayAmount().toString(),
                orderPayMessageDTO.getProductNames()
        };
        return this.sendMessage(orderPayMessageDTO, WxMpAppMessageTemplateEnum.ORDER_PAY, params);
    }


    private Boolean sendMessage(TemplateMessageDTO templateMessageDTO, WxMpAppMessageTemplateEnum wxMpAppMessageTemplateEnum, String[] params) {
        // 获取公众号应用
        MpApp mpApp = wxMpConfig.getMpAppByMpEnum(templateMessageDTO.getWxMpAppEnum());

        // 获取消息模板
        MpMessageTemplate messageTemplate = wxMpConfig.getMpMessageTemplate(mpApp.getAppId(), wxMpAppMessageTemplateEnum);
        // 获取授权关系
        String appId = mpApp.getAppId();

        Long userId = templateMessageDTO.getUserId();

        ThirdInfo thirdInfo = thirdInfoService.getByAppIdAndUserId(appId, userId);

        if (Objects.isNull(thirdInfo)) {
            log.info("用户userId【{}】没有绑定appId【{}】授权关系", userId, appId);
        }
        if (!thirdInfo.getIsSubscribe()) {
            log.info("用户userId【{}】没有关注appId【{}】公众号", userId, appId);
            return false;
        }

        String url = null;

        if (StringUtils.isNotBlank(messageTemplate.getUrl()) && Objects.nonNull(templateMessageDTO.getPrimaryId())) {
            url = messageTemplate.getUrl() + templateMessageDTO.getPrimaryId();
        }

        return thirdWxMpService.sendTemplateMessage(mpApp.getAppId(), messageTemplate.getTemplateId(), thirdInfo.getAppUserId(), url,
                messageTemplate.getFirst(), messageTemplate.getRemark(), params, null);
    }


}
