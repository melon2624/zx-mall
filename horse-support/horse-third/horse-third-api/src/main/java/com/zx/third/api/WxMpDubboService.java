package com.zx.third.api;

import com.zx.third.model.dto.OrderCancelMessageDTO;
import com.zx.third.model.dto.OrderPayMessageDTO;

/**
 * 微信公众号Dubbo接口
 *
 * @author zhangxin
 * @date 2023/1/30 14:59
 */
public interface WxMpDubboService {
    Boolean sendOrderCancelMessage(OrderCancelMessageDTO orderCancelMessageDTO);

    Boolean sendOrderPayMessage(OrderPayMessageDTO orderPayMessageDTO);


}
