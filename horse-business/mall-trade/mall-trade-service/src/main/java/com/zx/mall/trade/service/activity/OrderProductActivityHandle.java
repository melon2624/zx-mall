package com.zx.mall.trade.service.activity;

import com.zx.mall.product.api.model.ProductSkuDO;
import com.zx.mall.trade.enums.OrderTypeEnum;
import com.zx.mall.trade.model.dto.app.OrderSubmitProductDTO;
import com.zx.mall.trade.model.entity.TradeOrderProduct;

import javax.annotation.Nonnull;

/**
 * @author zhangxin
 * @date 2023/1/31 15:55
 */
public interface OrderProductActivityHandle {


    /**
     * 提交订单商品参数，获取商品订单对象
     *
     * @param productDTO：订单商品提交参数
     * @param productSku：商品sku
     * @param isReduceStock：是否扣减库存
     * @return com.zx.mall.trade.model.entity.TradeOrderProduct
     * @author zhangxin
     * @date 2023/01/31
     */
    TradeOrderProduct createOrderProduct(@Nonnull OrderSubmitProductDTO productDTO, @Nonnull ProductSkuDO productSku, boolean isReduceStock);

    /**
     * 获取订单对应的订单类型
     *
     * @return com.zx.mall.trade.enums.OrderTypeEnum
     * @author zhangxin
     * @date 2023/01/31
     */
    OrderTypeEnum getOrderType();

}
