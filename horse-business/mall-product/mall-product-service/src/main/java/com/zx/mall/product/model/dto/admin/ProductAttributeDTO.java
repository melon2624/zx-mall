package com.zx.mall.product.model.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author zhangxin
 * @date 2023/1/10 14:07
 */
@Data
public class ProductAttributeDTO implements Serializable {

    @NotNull
    @Size(max = 20)
    @ApiModelProperty("属性名")
    private String name;

    @ApiModelProperty("商品属性组ID，新增必传")
    private Long productAttributeGroupId;

    @ApiModelProperty("商品ID，新增必传")
    private Long productId;


}
