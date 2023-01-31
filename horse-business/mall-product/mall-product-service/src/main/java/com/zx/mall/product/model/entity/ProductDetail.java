package com.zx.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhangxin
 * @date 2023/1/3 18:38
 */
@Data
@Accessors(chain = true)
@TableName("product_detail")
public class ProductDetail  implements Serializable {

    /**
     * 商品id
     */
    @TableId
    private Long productId;

    /**
     * 商品详情描述
     */
    private String detail;

}
