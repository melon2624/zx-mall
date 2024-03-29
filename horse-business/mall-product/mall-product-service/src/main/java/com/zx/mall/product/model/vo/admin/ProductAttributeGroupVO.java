package com.zx.mall.product.model.vo.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性组(ProductAttributeGroup)表实体类
 *
 * @author zhangxin
 * @date 2023/1/12 20:01
 */
@Data
public class ProductAttributeGroupVO implements Serializable {

    @ApiModelProperty("商品属性组ID")
    private Long id;

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("属性组名")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

}
