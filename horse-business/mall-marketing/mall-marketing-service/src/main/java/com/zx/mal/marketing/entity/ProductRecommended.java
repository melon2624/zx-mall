package com.zx.mal.marketing.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zx.framework.mysql.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品推荐表(ProductRecommended)表实体类
 *
 * @author zhangxin
 * @date 2023/1/3 19:51
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_recommended")
public class ProductRecommended  extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品主图
     */
    private String productMainPicture;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 是否开启 1开启，0未开启
     */
    private Boolean isEnable;


    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;

}
