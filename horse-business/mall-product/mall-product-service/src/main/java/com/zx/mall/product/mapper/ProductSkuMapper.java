package com.zx.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.mall.product.model.entity.ProductSku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商品sku(ProductSku)表数据库访问层
 *
 * @author zhangxin
 * @date 2023/1/29 14:25
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {


    /**
     * 增加库存
     *
     * @param skuId   skuId
     * @param quality 数量（扣减库存用负数）
     * @return boolean
     */
    @Update("update product_sku set stock = stock + #{quality} where id = #{skuId}")
    boolean addStock(@Param("skuId") Long skuId, @Param("quality") Integer quality);

    /**
     * 扣减库存
     *
     * @param skuId   skuId
     * @param quality 数量
     * @return boolean
     */
    @Update("update product_sku set stock = stock - #{quality} where id = #{skuId} and stock >= #{quality}")
    boolean reduceStock(@Param("skuId") Long skuId, @Param("quality") Integer quality);
}
