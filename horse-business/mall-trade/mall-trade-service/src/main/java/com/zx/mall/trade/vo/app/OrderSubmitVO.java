package com.zx.mall.trade.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/31 10:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("提交订单VO")
public class OrderSubmitVO {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("服务器时间")
    private LocalDateTime serverTime;

    @ApiModelProperty("支付失效时间")
    private LocalDateTime expireTime;

    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("运费")
    private BigDecimal shippingAmount;

    @ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty("实付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("商品详情信息")
    private List<AdvanceOrderProductVO> products;


}
