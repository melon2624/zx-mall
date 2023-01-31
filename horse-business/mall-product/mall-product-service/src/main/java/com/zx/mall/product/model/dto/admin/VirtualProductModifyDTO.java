package com.zx.mall.product.model.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 虚拟商品表(VirtualProduct)表实体类
 *
 * @author zhangxin
 * @date 2023/1/3 16:21
 */
@Data
public class VirtualProductModifyDTO  implements Serializable {

    @NotNull
    @ApiModelProperty("发货类型（1-文件 2-消息）")
    private Integer shipType;

    @Size(max = 255)
    @NotNull
    @ApiModelProperty("发货内容")
    private String shipContent;

}
