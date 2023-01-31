package com.zx.mall.product.model.vo.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 虚拟商品表(VirtualProduct)表实体类
 *
 * @author zhangxin
 * @date 2023/1/9 17:27
 */
@Data
public class VirtualProductVO implements Serializable {
    @ApiModelProperty("发货类型（1-文件 2-消息）")
    private Integer shipType;

    @ApiModelProperty("发货内容")
    private String shipContent;

    @ApiModelProperty("商品id")
    private Long productId;
}
