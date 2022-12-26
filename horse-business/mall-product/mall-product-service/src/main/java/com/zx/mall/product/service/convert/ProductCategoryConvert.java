package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.ProductCategoryModifyDTO;
import com.zx.mall.product.model.entity.ProductCategory;
import org.mapstruct.Mapper;

/**
 * 商品分类(ProductCategory)转换接口
 *
 * @author zhangxin
 * @date 2022/12/20 14:34
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryConvert {

    ProductCategory toEntity(ProductCategoryModifyDTO productCategoryModifyDTO);

}
