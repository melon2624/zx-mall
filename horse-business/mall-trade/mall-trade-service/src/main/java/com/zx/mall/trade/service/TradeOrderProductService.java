package com.zx.mall.trade.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.mall.trade.mapper.TradeOrderProductMapper;
import com.zx.mall.trade.model.entity.TradeOrderProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 订单详情(TradeOrderProduct)表服务实现类
 *
 * @author zhangxin
 * @date 2023/1/30 10:06
 */
@Slf4j
@Service("tradeOrderProductService")
public class TradeOrderProductService extends ServiceImpl<TradeOrderProductMapper, TradeOrderProduct> {


    /**
     * 根据一个或多个订单ID，查询商品详情列表
     *
     * @param orderIds：订单ID列表
     * @return java.util.List<com.zx.mall.trade.model.entity.TradeOrderProduct>
     * @author zhangxin
     * @date 2023/1/30
     */
    public List<TradeOrderProduct> listByOrderIds(Long... orderIds) {

        return this.listByOrderIds(Arrays.asList(orderIds));
    }

    /**
     * 根据订单ID集合，查询商品详情列表
     *
     * @param orderIds：订单ID列表
     * @return java.util.List<com.zx.mall.trade.model.entity.TradeOrderProduct>
     * @author zhangxin
     * @date 2023/1/30
     */
    private List<TradeOrderProduct> listByOrderIds(Collection<Long> orderIds) {
        return lambdaQuery().in(TradeOrderProduct::getOrderId,orderIds).list();
    }
}
