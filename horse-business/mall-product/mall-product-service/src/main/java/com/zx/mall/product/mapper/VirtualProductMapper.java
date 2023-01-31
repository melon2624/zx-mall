package com.zx.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.mall.product.model.entity.VirtualProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 虚拟商品表(VirtualProduct)表数据库访问层
 *
 * @author zhangxin
 * @date 2023/1/6 20:24
 */
public interface VirtualProductMapper  extends BaseMapper<VirtualProduct> {

    /**
     * 根据商品id删除虚拟商品
     * @param productId 商品id
     * @return
     */
    @Delete("delecte from  virtual_product where product_id = #{product_id}")
    Boolean deleteByProductId(@Param("product_id") Long productId);

}
