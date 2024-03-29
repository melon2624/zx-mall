package com.zx.mall.trade.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangxin
 * @date 2023/1/31 11:24
 */
@Data
@ApiModel("预订单商品信息VO")
public class AdvanceOrderProductVO {

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("商品SKU-ID")
    private Long productSkuId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productImageUrl;

    @ApiModelProperty("SKU规格描述")
    private String skuDescribe;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    @ApiModelProperty("实际价格")
    private BigDecimal realPrice;

    @ApiModelProperty("实际金额")
    private BigDecimal realAmount;

}
