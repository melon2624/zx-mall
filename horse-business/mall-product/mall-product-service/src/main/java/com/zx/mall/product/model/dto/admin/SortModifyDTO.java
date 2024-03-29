package com.zx.mall.product.model.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 排序修改DTO，适用于拖拽调整排序的对象
 *
 * @author zhangxin
 * @date 2023/1/3 15:39
 */
@Data
public class SortModifyDTO implements Serializable {

    @NotNull
    @ApiModelProperty("商品分类id")
    private Long id;

    @NotNull
    @ApiModelProperty("原来的sort值")
    private Integer oldSort;

    @NotNull
    @ApiModelProperty("现在的sort值（注意是取本来在新位置上的元素的sort值）")
    private Integer currentSort;

}
