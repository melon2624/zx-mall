package com.zx.mal.marketing.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 商品推荐表(ProductRecommended)表实体类
 *
 * @author zhangxin
 * @date 2023/1/3 19:46
 */
@Data
@Accessors(chain = true)
public class ProductRecommendedDTO  implements Serializable {

    @NotEmpty
    @ApiModelProperty("商品id")
    private List<Long> productIdList;

}
