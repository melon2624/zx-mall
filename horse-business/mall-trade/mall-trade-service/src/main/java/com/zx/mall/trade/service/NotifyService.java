package com.zx.mall.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.msb.im.api.dto.SendMessageDTO;
import com.msb.im.api.enums.MessageTypeEnum;
import com.msb.im.api.enums.SessionTypeEnum;
import com.zx.framework.common.context.UserContext;
import com.zx.framework.common.utils.EqualsUtil;
import com.zx.im.api.MallImDubboService;
import com.zx.im.api.enums.MallImSessionTypeEnum;
import com.zx.mall.trade.enums.OrderPayTypeEnum;
import com.zx.mall.trade.model.dto.notify.ImTradeNotifyDTO;
import com.zx.mall.trade.model.entity.TradeOrder;
import com.zx.mall.trade.model.entity.TradeOrderProduct;
import com.zx.third.api.WxMpDubboService;
import com.zx.third.enums.WxMpAppEnum;
import com.zx.third.enums.WxMpAppMessageTemplateEnum;
import com.zx.third.model.dto.OrderCancelMessageDTO;
import com.zx.third.model.dto.OrderPayMessageDTO;
import com.zx.user.api.UserDubboService;
import com.zx.user.api.vo.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @date 2023/1/30 9:57
 */
@Slf4j
@Async
@Service("notifyService")
public class NotifyService {

    @Resource
    UserService userService;

    @DubboReference
    private UserDubboService userDubboService;

    @DubboReference
    private MallImDubboService mallImDubboService;

    @DubboReference
    private WxMpDubboService wxMpDubboService;

    public void orderCancelNotify(TradeOrder tradeOrder, List<TradeOrderProduct> orderProductList) {

        String productNames = orderProductList.stream().map(TradeOrderProduct::getProductName).collect(Collectors.joining("-"));
        List<String> productImageUrls = orderProductList.stream().map(TradeOrderProduct::getProductImageUrl).collect(Collectors.toList());
        String title = "订单已关闭";
        String content = "您购买的[" + productNames + "]订单已关闭，点击查看详情";

        ImTradeNotifyDTO imTradeNotifyDTO = new ImTradeNotifyDTO(WxMpAppMessageTemplateEnum.ORDER_CANCEL.getCode()).setTitle(title).setContent(content).setPrimaryId(tradeOrder.getId()).setProductNames(productNames).setProductImageUrls(productImageUrls);
        SendMessageDTO sendMessageDTO = createSendMessageDTO(tradeOrder, imTradeNotifyDTO);
        Boolean im = mallImDubboService.sendMessage(sendMessageDTO);
        log.info("订单关闭IM通知：{}，{}", sendMessageDTO, im);

        OrderCancelMessageDTO orderCancelMessageDTO = new OrderCancelMessageDTO(WxMpAppEnum.YANXUAN, tradeOrder.getId(), tradeOrder.getUserId());
        orderCancelMessageDTO.setOrderNo(tradeOrder.getOrderNo()).setPayAmount(tradeOrder.getPayAmount()).setCancelReason(tradeOrder.getCancelReason())
                .setProductNames(productNames);
        Boolean mp = wxMpDubboService.sendOrderCancelMessage(orderCancelMessageDTO);
        log.info("订单关闭公众号通知：{}，{}", orderCancelMessageDTO, mp);
    }

    private SendMessageDTO createSendMessageDTO(TradeOrder tradeOrder, ImTradeNotifyDTO imTradeNotifyDTO) {
        return createSendMessageDTO(tradeOrder.getUserId(), imTradeNotifyDTO);
    }

    private SendMessageDTO createSendMessageDTO(Long tradeUserId, ImTradeNotifyDTO imTradeNotifyDTO) {
        long sysUserId = UserContext.getSysUserId();
        UserDO sysUser = userDubboService.getUserDetailInfoById(sysUserId);
        UserDO traceUser = userDubboService.getUserDetailInfoById(tradeUserId);
        SendMessageDTO sendMessageDTO = new SendMessageDTO();
        sendMessageDTO.setType(MessageTypeEnum.CUSTOM);
        sendMessageDTO.setFromId(sysUserId + "");
        sendMessageDTO.setFromAvatar(sysUser.getAvatar());
        sendMessageDTO.setFromNickname(sysUser.getNickname());
        sendMessageDTO.setToId(tradeUserId.toString());
        sendMessageDTO.setToAvatar(traceUser.getAvatar());
        sendMessageDTO.setToNickname(traceUser.getNickname());
        sendMessageDTO.setSysId(1);
        sendMessageDTO.setPayload(JSONObject.toJSONString(imTradeNotifyDTO));
        sendMessageDTO.setSessionTypeEnum(SessionTypeEnum.CUSTOM);
        sendMessageDTO.setSessionPayload(MallImSessionTypeEnum.SYSTEM_SESSION.getPayload());
        return sendMessageDTO;
    }

    public void orderPayNotify(TradeOrder tradeOrder, List<TradeOrderProduct> orderProductList) {
        UserDO userDO = userService.getUserInfoByIdOrThrow(tradeOrder.getUserId());
        String productNames = orderProductList.stream().map(TradeOrderProduct::getProductImageUrl).collect(Collectors.joining("-"));
        List<String> productImageUrls = orderProductList.stream().map(TradeOrderProduct::getProductImageUrl).collect(Collectors.toList());

        String title = "订单支付成功";
        String content = "您购买的[" + productNames + "]订单已支付成功，感谢您的使用";

        ImTradeNotifyDTO imTradeNotifyDTO = new ImTradeNotifyDTO(WxMpAppMessageTemplateEnum.ORDER_PAY.getCode()).setTitle(title).setContent(content).setPrimaryId(tradeOrder.getId())
                .setProductNames(productNames).setProductImageUrls(productImageUrls);

        SendMessageDTO sendMessageDTO = createSendMessageDTO(tradeOrder, imTradeNotifyDTO);

        Boolean im = mallImDubboService.sendMessage(sendMessageDTO);

        log.info("订单支付成功IM通知：{}，{}", sendMessageDTO, im);

        // 如果支付方式为微信支付，则发送公众号通知

        if (EqualsUtil.anyEqualsIDict(tradeOrder.getPayType(), OrderPayTypeEnum.WXPAY)) {
            OrderPayMessageDTO orderPayMessageDTO = new OrderPayMessageDTO(WxMpAppEnum.YANXUAN, tradeOrder.getId(), tradeOrder.getUserId());
            orderPayMessageDTO.setOrderNo(tradeOrder.getOrderNo()).setPayAmount(tradeOrder.getPayAmount())
                    .setUserNickName(userDO.getNickname()).setProductNames(productNames);
            Boolean mp=wxMpDubboService.sendOrderPayMessage(orderPayMessageDTO);
            log.info("订单支付成功公众号通知：{}，{}", orderPayMessageDTO, mp);
        }


    }
}
