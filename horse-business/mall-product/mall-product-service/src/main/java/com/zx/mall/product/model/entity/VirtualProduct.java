package com.zx.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zx.framework.mysql.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 虚拟商品表(VirtualProduct)表实体类
 *
 * @author zhangxin
 * @date 2023/1/6 20:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("virtual_product")
public class VirtualProduct extends BaseEntity implements Serializable {

    /**
     * 虚拟商品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发货类型（1-文件 2-消息）
     */
    private Integer shipType;

    /**
     * 发货内容
     */
    private String shipContent;

    /**
     * 商品id
     */
    private Long productId;

}
