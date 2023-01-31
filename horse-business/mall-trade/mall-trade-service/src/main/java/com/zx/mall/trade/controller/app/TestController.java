package com.zx.mall.trade.controller.app;

import com.zx.framework.redis.RedisClient;
import com.zx.mall.trade.model.entity.TradeOrder;
import com.zx.mall.trade.model.entity.TradeOrderProduct;
import com.zx.mall.trade.service.NotifyService;
import com.zx.mall.trade.service.TradeOrderProductService;
import com.zx.mall.trade.service.TradeOrderService;
import com.zx.user.auth.NoAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/29 15:48
 */
@Slf4j
@NoAuth
@Api(tags = "压测测试接口")
@RestController("test")
public class TestController {

    @Resource
    private RedisClient redisClient;

    @Resource
    TradeOrderService tradeOrderService;

    @Resource
    TradeOrderProductService tradeOrderProductService;

    @Resource
    NotifyService notifyService;

    @ApiOperation("根据id 查询db，走索引")
    @GetMapping("dbById")
    public TradeOrder dbById(Long id) {
        return tradeOrderService.getOrderById(id);
    }

    @ApiOperation("根据 总金额查，不走索引")
    @GetMapping("dbByNoIndex")
    public List<TradeOrder> dbByNoIndex(BigDecimal value) {
        return tradeOrderService.lambdaQuery().eq(TradeOrder::getTotalAmount, value)
                .last("limit 100").list();
    }

    @ApiOperation("写redis")
    @PostMapping("writeRedis")
    public Boolean wirteRedis(String value) {
        redisClient.lPush("test-key", value);
        return Boolean.TRUE;
    }

    @ApiOperation("读redis")
    @GetMapping("readRedis")
    public  Object readRedis(int loop){
        redisClient.lPop("test-key");
        return Boolean.TRUE;
    }

    @ApiOperation("空接口")
    @GetMapping("empty")
    public Boolean empty() {
        return Boolean.TRUE;
    }


    @ApiOperation("IM测试接口")
    @GetMapping("im/{orderId}")
    public Boolean im(@PathVariable("orderId") Long orderId){
        log.info("IM测试接口");
        TradeOrder tradeOrder=tradeOrderService.getOrderByIdOrThrow(orderId);
        List<TradeOrderProduct> orderProductList=tradeOrderProductService.listByOrderIds(orderId);
        notifyService.orderPayNotify(tradeOrder,orderProductList);
        return Boolean.TRUE;

    }



}
