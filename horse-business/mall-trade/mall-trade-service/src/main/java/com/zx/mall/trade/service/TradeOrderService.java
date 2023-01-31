package com.zx.mall.trade.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.mysql.shard.ShardTableContext;
import com.zx.framework.web.result.Assert;
import com.zx.mall.trade.exception.TradeExceptionCodeEnum;
import com.zx.mall.trade.mapper.TradeOrderMapper;
import com.zx.mall.trade.model.entity.TradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zhangxin
 * @date 2023/1/29 16:14
 */
@Slf4j
@Service("tradeOrderService")
public class TradeOrderService extends ServiceImpl<TradeOrderMapper, TradeOrder> {


    /**
     * 根据订单ID获取订单
     *
     * @param orderId：订单ID
     * @return com.msb.mall.trade.model.entity.TradeOrder
     * @author peng.xy
     * @date 2023/1/30
     */
    public TradeOrder getOrderById(Long orderId) {
        return this.getOrderByIdAndUserId(orderId, null);
    }

    /**
     * 根据订单ID，用户ID获取订单
     *
     * @param orderId：订单ID
     * @param userId：可选
     * @return com.zx.mall.trade.model.entity.TradeOrder
     * @author zhangxin
     * @date 2023/1/30
     */
    private TradeOrder getOrderByIdAndUserId(Long orderId, Long userId) {
        ShardTableContext.set(TradeOrder.class, orderId);

        TradeOrder tradeOrder = lambdaQuery().eq(TradeOrder::getId, orderId)
                .eq(Objects.nonNull(userId), TradeOrder::getUserId, userId)
                .one();
        log.info("查询订单信息，订单ID：{}, 用户ID：{}，订单数据：{}", orderId, userId, tradeOrder);
        return tradeOrder;
    }

    /**
     * 根据订单ID获取订单，数据有误则抛出异常
     *
     * @param orderId：订单ID
     * @return com.zx.mall.trade.model.entity.TradeOrder
     * @author zhangxin
     * @date 2023/1/30
     */
    public TradeOrder getOrderByIdOrThrow(Long orderId) {
        TradeOrder tradeOrder = this.getOrderById(orderId);
        Assert.notNull(tradeOrder, TradeExceptionCodeEnum.ORDER_EXCEPTION);
        Assert.notTrue(tradeOrder.getIsDisabled(), TradeExceptionCodeEnum.ORDER_EXCEPTION);
        Assert.notTrue(tradeOrder.getIsDeleted(), TradeExceptionCodeEnum.ORDER_EXCEPTION);
        return tradeOrder;
    }
}
